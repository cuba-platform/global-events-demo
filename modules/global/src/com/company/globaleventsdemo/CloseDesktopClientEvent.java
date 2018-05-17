package com.company.globaleventsdemo;

import com.haulmont.addon.globalevents.GlobalApplicationEvent;

public class CloseDesktopClientEvent extends GlobalApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CloseDesktopClientEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "CloseDesktopClientEvent{" +
                "source=" + source +
                '}';
    }
}
