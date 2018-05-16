package com.company.globaleventsdemo.desktop;

import javax.swing.*;

public class App extends com.haulmont.cuba.desktop.App {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app = new App();
                app.init(args);
                app.show();
                app.showLoginDialog();
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
}
