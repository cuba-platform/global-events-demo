package com.haulmont.globaleventsdemo.core;

public interface CoreTesterMBean {

    String sendUiNotificationEvent(String message);

    String sendBeanNotificationEvent();
}
