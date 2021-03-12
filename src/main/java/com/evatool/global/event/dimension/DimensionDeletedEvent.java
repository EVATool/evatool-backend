package com.evatool.global.event.dimension;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class DimensionDeletedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public DimensionDeletedEvent(Object source, String jsonPayload) {
        super(source);
        this.jsonPayload = jsonPayload;
    }
}
