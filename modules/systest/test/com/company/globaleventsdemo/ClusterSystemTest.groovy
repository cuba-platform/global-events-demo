package com.company.globaleventsdemo

import com.codeborne.selenide.WebDriverRunner
import com.company.globaleventsdemo.composite.LoginWindow
import com.company.globaleventsdemo.composite.Screen1
import com.company.globaleventsdemo.jmx.CoreTester
import com.haulmont.masquerade.Connectors
import groovy.sql.Sql
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import static com.codeborne.selenide.Selenide.open
import static SystemTestSupport.getSql
import static SystemTestSupport.openScreen
import static com.haulmont.masquerade.Components.wire
import static com.haulmont.masquerade.Conditions.value
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class ClusterSystemTest {

    @Before
    void setUp() throws Exception {
        System.setProperty('selenide.browser', 'chrome')

        getSql().execute('delete from GLEVTDEMO_EVENT_REGISTRATION')
    }

    @Test
    void testUiEvent() {
        WebDriver wd3 = new ChromeDriver()
        WebDriver wd4 = new ChromeDriver()
        WebDriver wd5 = new ChromeDriver()
        try {
            // browser3
            // login, open screen and check count is 0
            WebDriverRunner.setWebDriver(wd3)
            open("http://localhost:8083/app")
            wire(LoginWindow).login()
            Screen1 screen_wd3 = openScreen()
            screen_wd3.with {
                receivedLab.shouldHave(value('0'))
            }

            // browser4
            // login, open screen and check count is 0
            WebDriverRunner.setWebDriver(wd4)
            open("http://localhost:8084/app")
            wire(LoginWindow).login()
            Screen1 screen_wd4 = openScreen()
            screen_wd4.with {
                receivedLab.shouldHave(value('0'))
            }

            // browser5
            // login, open screen, check count is 0 and click "Send"
            WebDriverRunner.setWebDriver(wd5)
            open('http://localhost:8085/app')
            wire(LoginWindow).login()
            Screen1 screen_wd5 = openScreen()
            screen_wd5.with {
                receivedLab.shouldHave(value('0'))
                sendBtn.click()
                receivedLab.shouldHave(value('1'))
            }

            // browser3
            // Check count is 1
            WebDriverRunner.setWebDriver(wd3)
            screen_wd3.with {
                receivedLab.shouldHave(value('1'))
            }

            // browser4
            // Check count is 1
            WebDriverRunner.setWebDriver(wd4)
            screen_wd4.with {
                receivedLab.shouldHave(value('1'))
            }

            // Send event from core
            CoreTester coreTester = Connectors.jmx(CoreTester,
                    new Connectors.JmxHost(null, null, "localhost:7771"))
            coreTester.sendUiNotificationEvent('test')

            // browser3
            // Check count is 2
            WebDriverRunner.setWebDriver(wd3)
            screen_wd3.with {
                receivedLab.shouldHave(value('2'))
            }

            // browser4
            // Check count is 2
            WebDriverRunner.setWebDriver(wd4)
            screen_wd4.with {
                receivedLab.shouldHave(value('2'))
            }

            // browser5
            // Check count is 2
            WebDriverRunner.setWebDriver(wd5)
            screen_wd5.with {
                receivedLab.shouldHave(value('2'))
            }
        } finally {
            wd3.quit()
            wd4.quit()
            wd5.quit()
        }
    }

    @Test
    void testBeanEvent() {
        // Connect to web blocks from browsers, because WebSocket connection is established on first Vaadin session
        WebDriver wd3 = new ChromeDriver()
        WebDriver wd4 = new ChromeDriver()
        WebDriver wd5 = new ChromeDriver()
        try {
            // browser3
            WebDriverRunner.setWebDriver(wd3)
            open("http://localhost:8083/app")
            // browser4
            WebDriverRunner.setWebDriver(wd4)
            open("http://localhost:8084/app")
            // browser5
            WebDriverRunner.setWebDriver(wd5)
            open("http://localhost:8085/app")
        } finally {
            wd3.quit()
            wd4.quit()
            wd5.quit()
        }

        // Send event from core
        CoreTester coreTester = Connectors.jmx(CoreTester,
                new Connectors.JmxHost(null, null, "localhost:7771"))
        coreTester.sendBeanNotificationEvent()

        Thread.sleep(500)

        Sql sql = getSql()
        List rows = sql.rows('select * from GLEVTDEMO_EVENT_REGISTRATION')
        rows.each {
            println(it)
        }
        assertEquals(2, rows.findAll { it.receiver == 'com.company.globaleventsdemo.core.FooCoreBean' }.size())
        assertEquals(3, rows.findAll { it.receiver == 'com.company.globaleventsdemo.web.FooWebBean' }.size())
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.portal.FooPortalBean' }.size())
        assertTrue(rows.every { it.event_class == 'com.company.globaleventsdemo.BeanNotificationEvent' })
        assertEquals(6, rows.size())
    }
}
