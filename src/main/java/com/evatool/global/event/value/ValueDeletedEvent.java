package com.evatool.global.event.value;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ValueDeletedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public ValueDeletedEvent(Object source, String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
