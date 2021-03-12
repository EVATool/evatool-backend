package com.evatool.global.event.analysis;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AnalysisUpdatedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public AnalysisUpdatedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
