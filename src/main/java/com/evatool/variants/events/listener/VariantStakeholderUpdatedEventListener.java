package com.evatool.variants.events.listener;

import com.evatool.global.event.stakeholder.StakeholderUpdatedEvent;
import com.evatool.variants.repositories.VariantsStakeholderRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VariantStakeholderUpdatedEventListener implements ApplicationListener<StakeholderUpdatedEvent> {
    @Autowired
    VariantsStakeholderRepository variantsStakeholderRepository;
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(VariantStakeholderUpdatedEventListener.class);

    @Override
    public void onApplicationEvent(StakeholderUpdatedEvent stakeholderUpdatedEvent) {
        logger.info("Listening Event in VariantStakeholderUpdated from {}", stakeholderUpdatedEvent.getClass());


        // TODO Uncomment once weird test cases get solved
//        VariantsStakeholder variantsStakeholder = gson.fromJson(stakeholderUpdatedEvent.getJsonPayload(), VariantsStakeholder.class);
//        variantsStakeholderRepository.save(variantsStakeholder);
    }
}
