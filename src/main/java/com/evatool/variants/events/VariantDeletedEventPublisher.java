package com.evatool.variants.events;

import com.evatool.global.event.variants.VariantDeletedEvent;
import com.evatool.variants.entities.Variant;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class VariantDeletedEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    Logger logger = LoggerFactory.getLogger(VariantDeletedEventPublisher.class);


    public void publishVariantDeletedEvent(Variant variant) {
        Gson gson = new Gson();
        String variantJson = gson.toJson(variant);

        logger.info("Publishing Event in {}", applicationEventPublisher.getClass());
        logger.debug("{} Eventpayload: {}", variant.getClass(), variantJson);

        VariantDeletedEvent variantDeletedEvent = new VariantDeletedEvent(this, variantJson);
        applicationEventPublisher.publishEvent(variantDeletedEvent);
    }

}