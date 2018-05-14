package com.company.globaleventsdemo.core;

import com.company.globaleventsdemo.BeanNotificationEvent;
import com.company.globaleventsdemo.entity.EventRegistration;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.security.app.Authenticated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("glevtdemo_FooWebBean")
public class FooCoreBean {

    private static final Logger log = LoggerFactory.getLogger(FooCoreBean.class);

    @Inject
    private DataManager dataManager;

    @Inject
    private GlobalConfig globalConfig;

    @Authenticated
    @EventListener
    public void onBeanNotificationEvent(BeanNotificationEvent event) {
        log.info("Received {}", event);
        EventRegistration registration = new EventRegistration(event, this, globalConfig);
        dataManager.commit(registration);
    }
}
