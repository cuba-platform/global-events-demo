package com.company.globaleventsdemo.core;

import com.company.globaleventsdemo.BeanNotificationEvent;
import com.company.globaleventsdemo.CloseDesktopClientEvent;
import com.company.globaleventsdemo.UiNotificationEvent;
import com.google.common.base.Strings;
import com.haulmont.cuba.core.global.Events;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("glevtdemo_CoreTester")
public class CoreTester implements CoreTesterMBean {

    @Inject
    private Events events;

    @Override
    public String sendUiNotificationEvent(String message) {
        events.publish(new UiNotificationEvent(this, Strings.isNullOrEmpty(message) ? "test" : message));
        return "done";
    }

    @Override
    public String sendBeanNotificationEvent() {
        events.publish(new BeanNotificationEvent(this));
        return "done";
    }

    @Override
    public String sendCloseDesktopClientEvent() {
        events.publish(new CloseDesktopClientEvent(this));
        return "done";
    }
}
