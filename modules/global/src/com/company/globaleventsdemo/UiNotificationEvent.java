package com.company.globaleventsdemo;

import com.haulmont.addon.globalevents.GlobalApplicationEvent;
import com.haulmont.addon.globalevents.GlobalUiEvent;

public class UiNotificationEvent extends GlobalApplicationEvent implements GlobalUiEvent {

    private String message;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public UiNotificationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "UiNotificationEvent{" +
                "message='" + message + '\'' +
                ", source=" + source +
                '}';
    }
}
