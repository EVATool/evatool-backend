package com.evatool.global.event.variants;

import com.evatool.variants.entities.Variant;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VariantCreatedEvent extends ApplicationEvent {
    private final String variantJson;

    public VariantCreatedEvent( String variantJson) {
        super(variantJson);
        this.variantJson = variantJson;
    }
}
