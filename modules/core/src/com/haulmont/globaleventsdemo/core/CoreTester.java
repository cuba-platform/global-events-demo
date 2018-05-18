package com.haulmont.globaleventsdemo.core;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.globaleventsdemo.BeanNotificationEvent;
import com.haulmont.globaleventsdemo.UiNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("glevtdemo_CoreTester")
public class CoreTester implements CoreTesterMBean {

    private static final Logger log = LoggerFactory.getLogger(CoreTester.class);

    @Inject
    private Events events;

    @Override
    public String sendUiNotificationEvent(String message) {
        log.info("Sending UiNotificationEvent");
        events.publish(new UiNotificationEvent(this, Strings.isNullOrEmpty(message) ? "test" : message));
        return "done";
    }

    @Override
    public String sendBeanNotificationEvent() {
        log.info("Sending BeanNotificationEvent");
        events.publish(new BeanNotificationEvent(this));
        return "done";
    }
}
