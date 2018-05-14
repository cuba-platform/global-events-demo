package com.company.globaleventsdemo.web;

import com.company.globaleventsdemo.BeanNotificationEvent;
import com.company.globaleventsdemo.entity.EventRegistration;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.app.TrustedClientService;
import com.haulmont.cuba.security.global.LoginException;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.auth.WebAuthConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("glevtdemo_FooWebBean")
public class FooWebBean {

    private static final Logger log = LoggerFactory.getLogger(FooWebBean.class);

    @Inject
    private DataManager dataManager;

    @Inject
    private GlobalConfig globalConfig;

    @Inject
    private TrustedClientService trustedClientService;

    @Inject
    private WebAuthConfig webAuthConfig;

    @EventListener
    public void onBeanNotificationEvent(BeanNotificationEvent event) {
        log.info("Received {}", event);

        UserSession systemSession;
        try {
            systemSession = trustedClientService.getSystemSession(webAuthConfig.getTrustedClientPassword());
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        AppContext.withSecurityContext(new SecurityContext(systemSession), () -> {
            EventRegistration registration = new EventRegistration(event, this, globalConfig);
            dataManager.commit(registration);
        });
    }
}
