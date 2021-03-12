package com.evatool.global.event.analysis;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AnalysisDeletedEvent extends ApplicationEvent {

    @Getter
    private final String jsonPayload;

    public AnalysisDeletedEvent(String jsonPayload) {
        super(jsonPayload);
        this.jsonPayload = jsonPayload;
    }
}
