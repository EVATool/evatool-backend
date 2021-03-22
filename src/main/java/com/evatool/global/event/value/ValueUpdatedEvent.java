package com.evatool.global.event.value;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ValueUpdatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public ValueUpdatedEvent(Object source, String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
