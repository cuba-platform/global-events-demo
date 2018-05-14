package com.company.globaleventsdemo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.util.Date;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.Creatable;
import com.haulmont.cuba.core.global.GlobalConfig;

@Table(name = "GLEVTDEMO_EVENT_REGISTRATION")
@Entity(name = "glevtdemo$EventRegistration")
public class EventRegistration extends BaseUuidEntity implements Creatable {
    private static final long serialVersionUID = 7722308017749808727L;

    @Column(name = "CREATE_TS")
    protected Date createTs;

    @Column(name = "CREATED_BY", length = 50)
    protected String createdBy;

    @Column(name = "EVENT_CLASS")
    protected String eventClass;

    @Column(name = "EVENT_PAYLOAD")
    protected String eventPayload;

    @Column(name = "RECEIVER")
    protected String receiver;

    @Column(name = "RECEIVED_AT")
    protected String receivedAt;

    public EventRegistration() {
    }

    public EventRegistration(Object event, Object receiever, GlobalConfig globalConfig) {
        setEventClass(event.getClass().getName());
        setEventPayload(event.toString());
        setReceiver(receiever.getClass().getName());
        setReceivedAt(globalConfig.getWebPort() + ":" + globalConfig.getWebContextName());
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }


    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }


    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    public String getEventClass() {
        return eventClass;
    }

    public void setEventPayload(String eventPayload) {
        this.eventPayload = eventPayload;
    }

    public String getEventPayload() {
        return eventPayload;
    }

    public void setReceivedAt(String receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getReceivedAt() {
        return receivedAt;
    }


}