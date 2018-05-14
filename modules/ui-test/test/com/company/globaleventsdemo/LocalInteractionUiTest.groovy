package com.company.globaleventsdemo

import com.codeborne.selenide.WebDriverRunner
import com.company.globaleventsdemo.composite.LoginWindow
import com.company.globaleventsdemo.composite.Screen1
import com.company.globaleventsdemo.jmx.CoreTester
import com.haulmont.masquerade.Connectors
import com.haulmont.masquerade.components.AppMenu
import groovy.sql.Sql
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import java.sql.DriverManager

import static com.codeborne.selenide.Selenide.open
import static com.haulmont.masquerade.Components._$
import static com.haulmont.masquerade.Components.wire
import static com.haulmont.masquerade.Conditions.value
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class LocalInteractionUiTest {


    private static getSql() {
        new Sql(DriverManager.getConnection('jdbc:postgresql://localhost/glevtdemo', 'cuba', 'cuba'))
    }

    @Before
    void setUp() throws Exception {
        System.setProperty('selenide.browser', 'chrome')

        getSql().execute('delete from GLEVTDEMO_EVENT_REGISTRATION')
    }

    @Test
    void testUiEvent() {
        WebDriver wd0 = WebDriverRunner.getWebDriver()
        WebDriver wd1 = new ChromeDriver()
        try {
            // open first browser, login, open screen and check count is 0
            open("http://local1:8080/app")
            wire(LoginWindow).login()
            AppMenu.Menu<Screen1> screen1Menu_wd0 = new AppMenu.Menu(Screen1, 'application-glevtdemo', 'screen1')
            def screen1_wd0 = _$(AppMenu).openItem(screen1Menu_wd0)
            screen1_wd0.with {
                receivedLab.shouldHave(value('0'))
            }

            // open second browser, login, open screen, check count is 0 and click "Send"
            WebDriverRunner.setWebDriver(wd1)
            open('http://local2:8080/app')
            wire(LoginWindow).login()
            AppMenu.Menu<Screen1> screen1Menu_wd1 = new AppMenu.Menu(Screen1, 'application-glevtdemo', 'screen1')
            def screen1_wd1 = _$(AppMenu).openItem(screen1Menu_wd1)
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
                    new Connectors.JmxHost(null, null, "localhost:7777"))
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
        // Send event from core
        CoreTester coreTester = Connectors.jmx(CoreTester,
                new Connectors.JmxHost(null, null, "localhost:7777"))
        coreTester.sendBeanNotificationEvent()

        Sql sql = getSql()
        List rows = sql.rows('select * from GLEVTDEMO_EVENT_REGISTRATION')
        assertEquals(2, rows.size())
        assertTrue(rows.every { it.event_class == 'com.company.globaleventsdemo.BeanNotificationEvent' })
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.core.FooCoreBean' }.size())
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.web.FooWebBean' }.size())
    }
}
