package com.company.globaleventsdemo

import com.codeborne.selenide.WebDriverRunner
import com.company.globaleventsdemo.composite.LoginWindow
import com.company.globaleventsdemo.jmx.CoreTester
import com.haulmont.masquerade.Connectors
import groovy.sql.Sql
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import static SystemTestSupport.openScreen
import static SystemTestSupport.sql
import static com.codeborne.selenide.Selenide.open
import static com.haulmont.masquerade.Components.wire
import static com.haulmont.masquerade.Conditions.value
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class LocalSystemTest {

    @Before
    void setUp() throws Exception {
        System.setProperty('selenide.browser', 'chrome')

        getSql().execute('delete from GLEVTDEMO_EVENT_REGISTRATION')
    }

    @Test
    void testUiEvent() {
        WebDriver wd0 = new ChromeDriver()
        WebDriver wd1 = new ChromeDriver()
        try {
            // open first browser, login, open screen and check count is 0
            WebDriverRunner.setWebDriver(wd0)
            open("http://localhost:8080/app")
            wire(LoginWindow).login()
            def screen1_wd0 = openScreen()
            screen1_wd0.with {
                receivedLab.shouldHave(value('0'))
            }

            // open second browser, login, open screen, check count is 0 and click "Send"
            WebDriverRunner.setWebDriver(wd1)
            open('http://localhost:8080/app')
            wire(LoginWindow).login()
            def screen1_wd1 = openScreen()
            screen1_wd1.with {
                receivedLab.shouldHave(value('0'))
                sendBtn.click()
                receivedLab.shouldHave(value('1'))
            }

            // Check count is 1 in the first browser
            WebDriverRunner.setWebDriver(wd0)
            screen1_wd0.with {
                receivedLab.shouldHave(value('1'))
            }

            // Send event from core
            CoreTester coreTester = Connectors.jmx(CoreTester,
                    new Connectors.JmxHost(null, null, "localhost:7770"))
            coreTester.sendUiNotificationEvent('test')

            // Check count is 2 in the first browser
            screen1_wd0.with {
                receivedLab.shouldHave(value('2'))
            }

            // Check count is 2 in the second browser
            WebDriverRunner.setWebDriver(wd1)
            screen1_wd1.with {
                receivedLab.shouldHave(value('2'))
            }
        } finally {
            wd1.quit()
            wd0.quit()
        }
    }

    @Test
    void testBeanEvent() {
        // Wait for server is up
        WebDriver wd = new ChromeDriver()
        try {
            WebDriverRunner.setWebDriver(wd)
            open("http://localhost:8080/app")
        } finally {
            wd.quit()
        }

        // Send event from core
        CoreTester coreTester = Connectors.jmx(CoreTester,
                new Connectors.JmxHost(null, null, "localhost:7770"))
        coreTester.sendBeanNotificationEvent()

        Thread.sleep(500)

        Sql sql = getSql()
        List rows = sql.rows('select * from GLEVTDEMO_EVENT_REGISTRATION')
        rows.each {
            println(it)
        }
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.core.FooCoreBean' }.size())
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.web.FooWebBean' }.size())
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.portal.FooPortalBean' }.size())
        assertTrue(rows.every { it.event_class == 'com.company.globaleventsdemo.BeanNotificationEvent' })
        assertEquals(3, rows.size())
    }
}
