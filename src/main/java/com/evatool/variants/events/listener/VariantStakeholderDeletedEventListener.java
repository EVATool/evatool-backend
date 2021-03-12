package com.evatool.variants.events.listener;

import com.evatool.global.event.stakeholder.StakeholderDeletedEvent;
import com.evatool.variants.repositories.VariantsStakeholderRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VariantStakeholderDeletedEventListener implements ApplicationListener<StakeholderDeletedEvent> {
    @Autowired
    VariantsStakeholderRepository variantsStakeholderRepository;
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(VariantStakeholderDeletedEventListener.class);

    @Override
    public void onApplicationEvent(StakeholderDeletedEvent stakeholderDeletedEvent) {
        logger.info("Listening Event in VariantStakeholderDeleted from {}", stakeholderDeletedEvent.getClass());

        // TODO Uncomment once weird test cases get solved
//        VariantsStakeholder variantsStakeholder = gson.fromJson(stakeholderDeletedEvent.getJsonPayload(), VariantsStakeholder.class);
//        variantsStakeholderRepository.delete(variantsStakeholder);
    }
}
