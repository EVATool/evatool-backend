package com.evatool.global.event.user;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class UserUpdatedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public UserUpdatedEvent(Object source, final String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
