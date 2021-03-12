package com.evatool.variants.events.listener;

import com.evatool.global.event.stakeholder.StakeholderCreatedEvent;
import com.evatool.variants.repositories.VariantsStakeholderRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VariantStakeholderCreatedEventListener implements ApplicationListener<StakeholderCreatedEvent> {
    @Autowired
    VariantsStakeholderRepository variantsStakeholderRepository;
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(VariantStakeholderCreatedEventListener.class);

    @Override
    public void onApplicationEvent(StakeholderCreatedEvent stakeholderCreatedEvent) {
        logger.info("Listening Event in VariantRequirementDeleted from {}", stakeholderCreatedEvent.getClass());

        // TODO Uncomment once weird test cases get solved
//        VariantsStakeholder variantsStakeholder = gson.fromJson(stakeholderCreatedEvent.getJsonPayload(), VariantsStakeholder.class);
//        variantsStakeholderRepository.save(variantsStakeholder);
    }
}
