package com.company.globaleventsdemo.web;

import com.company.globaleventsdemo.BeanNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("glevtdemo_FooWebBean")
public class FooWebBean {

    private static final Logger log = LoggerFactory.getLogger(FooWebBean.class);

    @EventListener
    public void onBeanNotificationEvent(BeanNotificationEvent event) {
        log.info("Received {}", event);
    }
}
