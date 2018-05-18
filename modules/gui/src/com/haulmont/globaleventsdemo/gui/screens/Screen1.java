package com.haulmont.globaleventsdemo.gui.screens;

import com.haulmont.globaleventsdemo.BeanNotificationEvent;
import com.haulmont.globaleventsdemo.UiNotificationEvent;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.globaleventsdemo.BeanNotificationEvent;
import com.haulmont.globaleventsdemo.UiNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

public class Screen1 extends AbstractWindow {

    private static final Logger log = LoggerFactory.getLogger(Screen1.class);

    @Inject
    private Events events;

    @Inject
    private Label receivedLab;

    private AtomicInteger count = new AtomicInteger();

    public void sendEvent() {
        events.publish(new UiNotificationEvent(this, "Test"));
    }

    @EventListener
    public void onUiNotificationEvent(UiNotificationEvent event) {
        log.info("Received {}", event);
        receivedLab.setValue(count.incrementAndGet());
    }

    // screens do not receive non-UI events!
    @EventListener
    public void onBeanNotificationEvent(BeanNotificationEvent event) {
        throw new IllegalStateException("Received " + event);
    }
}