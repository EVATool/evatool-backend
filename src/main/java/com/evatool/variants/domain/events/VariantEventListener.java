package com.evatool.variants.domain.events;


import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.requirements.RequirementCreatedEvent;
import com.evatool.global.event.requirements.RequirementDeletedEvent;
import com.evatool.global.event.requirements.RequirementUpdatedEvent;
import com.evatool.variants.common.error.exceptions.IllegalEventPayloadException;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class VariantEventListener {

    private static final String DEBUGFORMAT = "EVENT: %s With Payload %s";
    final Logger logger = LoggerFactory.getLogger(VariantEventListener.class);
    Gson gson = new Gson();

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @EventListener
    @Async
    public void analyseCreated(AnalysisCreatedEvent event){
        logger.info("analyse created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        try {
            VariantsAnalysis variantsAnalysis = gson.fromJson(event.getJsonPayload(), VariantsAnalysis.class);
            variantsAnalysisRepository.save(variantsAnalysis);
        }
        catch (Exception e) {
            throw new IllegalEventPayloadException(event.getJsonPayload());
        }
    }

    @EventListener
    @Async
    public void analyseDeleted(AnalysisDeletedEvent event){
        logger.info("analyse created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        try {
            VariantsAnalysis variantsAnalysis = gson.fromJson(event.getJsonPayload(), VariantsAnalysis.class);
            variantsAnalysisRepository.delete(variantsAnalysis);
        }
        catch (Exception e){
            throw new IllegalEventPayloadException(event.getJsonPayload());
        }
    }

    @EventListener
    @Async
    public void requirementCreated(RequirementCreatedEvent event){
        logger.info("requirement created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
    }

    @EventListener
    @Async
    public void requirementUpdated(RequirementUpdatedEvent event){
        logger.info("requirement updated event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
    }

    @EventListener
    @Async
    public void requirementDeleted(RequirementDeletedEvent event){
        logger.info("requirement deleted event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
    }


}
