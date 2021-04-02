package com.evatool.global.event.user;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {

    @Getter
    private String jsonPayload;

    public UserCreatedEvent(final String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
