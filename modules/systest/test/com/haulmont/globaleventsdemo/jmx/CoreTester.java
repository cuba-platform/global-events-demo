package com.haulmont.globaleventsdemo.jmx;

import com.haulmont.masquerade.jmx.JmxName;

@JmxName("app-core.glevtdemo:type=CoreTester")
public interface CoreTester {

    String sendUiNotificationEvent(String message);

    String sendBeanNotificationEvent();

    String sendCloseDesktopClientEvent();
}
