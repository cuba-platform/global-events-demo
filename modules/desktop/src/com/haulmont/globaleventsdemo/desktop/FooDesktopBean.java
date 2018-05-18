package com.haulmont.globaleventsdemo.desktop;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.globaleventsdemo.BeanNotificationEvent;
import com.haulmont.globaleventsdemo.entity.EventRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("glevtdemo_FooDesktopBean")
public class FooDesktopBean {

    private static final Logger log = LoggerFactory.getLogger(FooDesktopBean.class);

    @Inject
    private DataManager dataManager;

    @Inject
    private GlobalConfig globalConfig;

    @Inject
    private UserSessionSource userSessionSource;

    @EventListener
    public void onBeanNotificationEvent(BeanNotificationEvent event) {
        log.info("Received {}", event);

        if (userSessionSource.checkCurrentUserSession()) {
            AppContext.withSecurityContext(new SecurityContext(userSessionSource.getUserSession()), () -> {
                EventRegistration registration = new EventRegistration(event, this, globalConfig);
                dataManager.commit(registration);
            });
        } else {
            log.info("Not connected");
        }
    }
}
