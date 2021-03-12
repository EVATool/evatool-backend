package com.evatool.variants.events.listener;

import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.variants.repositories.VariantsAnalysisRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VariantAnalysisCreatedEventListener implements ApplicationListener<AnalysisCreatedEvent> {
    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(VariantAnalysisCreatedEventListener.class);


    @Override
    public void onApplicationEvent(AnalysisCreatedEvent analysisCreatedEvent) {

        logger.info("Listening Event in VariantAnalysisCreated from {}", analysisCreatedEvent.getClass());

        // TODO uncomment once the event has been correctly implemented
//        VariantsAnalysis variantsAnalysis = gson.fromJson(analysisCreatedEvent.getMessage(), VariantsAnalysis.class);
//        variantsAnalysisRepository.save(variantsAnalysis);
    }
}
