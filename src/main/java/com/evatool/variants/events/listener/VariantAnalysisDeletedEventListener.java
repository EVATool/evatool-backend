package com.evatool.variants.events.listener;

import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.variants.repositories.VariantsAnalysisRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VariantAnalysisDeletedEventListener implements ApplicationListener<AnalysisDeletedEvent> {
    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(VariantAnalysisUpdatedEventListener.class);


    @Override
    public void onApplicationEvent(AnalysisDeletedEvent analysisDeletedEvent) {

        logger.info("Listening Event in VariantAnalysisDeleted from {}", analysisDeletedEvent.getClass());

        // TODO uncomment once the event has been correctly implemented
        // VariantsAnalysis variantsAnalysis = gson.fromJson(analysisDeletedEvent.getMessage(), VariantsAnalysis.class);
        // variantsAnalysisRepository.delete(variantsAnalysis);
    }
}
