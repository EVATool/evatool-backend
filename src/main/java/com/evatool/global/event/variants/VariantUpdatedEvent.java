package com.evatool.global.event.variants;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VariantUpdatedEvent extends ApplicationEvent {
    private final String variantJson;

    public VariantUpdatedEvent(String variantJson) {
        super(variantJson);
        this.variantJson = variantJson;
    }
}
