package com.evatool.variants.events.listener;

import com.evatool.global.event.requirements.RequirementUpdatedEvent;
import com.evatool.variants.entities.VariantsRequirement;
import com.evatool.variants.repositories.VariantsRequirementRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VariantRequirementUpdatedEventListener implements ApplicationListener<RequirementUpdatedEvent> {
    @Autowired
    VariantsRequirementRepository variantsRequirementRepository;
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(VariantRequirementUpdatedEventListener.class);

    @Override
    public void onApplicationEvent(RequirementUpdatedEvent requirementUpdatedEvent) {
        logger.info("Listening Event in VariantRequirementDeleted from {}", requirementUpdatedEvent.getClass());

        // TODO uncomment once the event has been correctly implemented
//        VariantsRequirement variantsRequirement = gson.fromJson(requirementUpdatedEvent.getJsonPayload(), VariantsRequirement.class);
//        variantsRequirementRepository.save(variantsRequirement);
    }
}
