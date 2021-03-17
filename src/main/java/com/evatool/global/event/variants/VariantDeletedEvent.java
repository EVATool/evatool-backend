package com.evatool.global.event.variants;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VariantDeletedEvent extends ApplicationEvent {
    private final String variantJson;

    public VariantDeletedEvent( String variantJson) {
        super(variantJson);
        this.variantJson = variantJson;
    }
}
