package com.company.globaleventsdemo.desktop;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.PasswordEncryption;
import com.haulmont.cuba.security.global.LoginException;

import javax.swing.*;
import java.util.Locale;

public class App extends com.haulmont.cuba.desktop.App {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app = new App();
                app.init(args);
                app.show();
                if (isAutoTest()) {
                    try {
                        PasswordEncryption passwordEncryption = AppBeans.get(PasswordEncryption.NAME);
                        app.getConnection().login("admin", passwordEncryption.getPlainHash("admin"), Locale.ENGLISH);
                    } catch (LoginException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    app.showLoginDialog();
                }
            }
        });
    }
    
    @Override
    protected String getDefaultAppComponents() {
        return "com.haulmont.cuba com.haulmont.addon.globalevents";
    }
    
    @Override
    protected String getDefaultAppPropertiesConfig() {
        return " /com/company/globaleventsdemo/desktop-app.properties";
    }

    @Override
    protected String getDefaultHomeDir() {
        return System.getProperty("user.home") + "/.cuba/global-events-demo";
    }

    @Override
    protected String getDefaultLogConfig() {
        return "desktop-logback.xml";
    }

    public static boolean isAutoTest() {
        return Boolean.valueOf(System.getProperty("glevtdemo.autoTest"));
    }
}
