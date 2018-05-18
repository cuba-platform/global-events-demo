package com.haulmont.globaleventsdemo;

import com.haulmont.addon.globalevents.GlobalApplicationEvent;

public class BeanNotificationEvent extends GlobalApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BeanNotificationEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "BeanNotificationEvent{" +
                "source=" + source +
                '}';
    }
}
