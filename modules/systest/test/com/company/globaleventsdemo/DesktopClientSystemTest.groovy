package com.company.globaleventsdemo

import com.codeborne.selenide.WebDriverRunner
import com.company.globaleventsdemo.jmx.CoreTester
import com.haulmont.masquerade.Connectors
import groovy.sql.Sql
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import static SystemTestSupport.getSql
import static com.codeborne.selenide.Selenide.open
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class DesktopClientSystemTest {

    @Before
    void setUp() throws Exception {
        getSql().execute('delete from GLEVTDEMO_EVENT_REGISTRATION')
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

        // Wait for desktop to connect
        Thread.sleep(2000)

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

        // Assumes that core, web and portal blocks are running
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.core.FooCoreBean' }.size())
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.web.FooWebBean' }.size())
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.portal.FooPortalBean' }.size())
        assertEquals(1, rows.findAll { it.receiver == 'com.company.globaleventsdemo.desktop.FooDesktopBean' }.size())
        assertTrue(rows.every { it.event_class == 'com.company.globaleventsdemo.BeanNotificationEvent' })
        assertEquals(4, rows.size())
    }

//    @AfterClass
//    static void tearDown() throws Exception {
//        CoreTester coreTester = Connectors.jmx(CoreTester,
//                new Connectors.JmxHost(null, null, "localhost:7770"))
//        coreTester.sendCloseDesktopClientEvent()
//    }
}
