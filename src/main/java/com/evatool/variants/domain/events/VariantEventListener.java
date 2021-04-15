package com.evatool.variants.domain.events;


import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.requirements.RequirementCreatedEvent;
import com.evatool.global.event.requirements.RequirementDeletedEvent;
import com.evatool.global.event.requirements.RequirementUpdatedEvent;
import com.evatool.variants.common.error.exceptions.EventEntityAlreadyExistsException;
import com.evatool.variants.common.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.variants.common.error.exceptions.IllegalEventPayloadException;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.entities.VariantsRequirements;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantRequirementsRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class VariantEventListener {

    private static final String DEBUGFORMAT = "EVENT: %s With Payload %s";
    final Logger logger = LoggerFactory.getLogger(VariantEventListener.class);
    Gson gson = new Gson();

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @Autowired
    VariantRequirementsRepository variantRequirementsRepository;

    @Autowired
    VariantRepository variantRepository;

    @EventListener
    public void analyseCreated(AnalysisCreatedEvent event){
        logger.info("analyse created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));

        if (variantsAnalysisRepository.existsById(VariantsAnalysis.fromJson(event.getJsonPayload()).getAnalysisId())) {
            throw new EventEntityAlreadyExistsException();
        }

        try {
            VariantsAnalysis variantsAnalysis = gson.fromJson(event.getJsonPayload(), VariantsAnalysis.class);
            variantsAnalysisRepository.save(variantsAnalysis);
        }
        catch (Exception e) {
            throw new IllegalEventPayloadException(event.getJsonPayload());
        }
    }

    @Transactional
    @EventListener
    @Async
    public void analyseDeleted(AnalysisDeletedEvent event){
        logger.info("analyse created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));

        if (!variantsAnalysisRepository.existsById(VariantsAnalysis.fromJson(event.getJsonPayload()).getAnalysisId())) {
            throw new EventEntityDoesNotExistException();
        }

        try {
            VariantsAnalysis variantsAnalysis = gson.fromJson(event.getJsonPayload(), VariantsAnalysis.class);
            variantRepository.deleteAllByVariantsAnalysis(variantsAnalysis);
            variantsAnalysisRepository.delete(variantsAnalysis);
        }
        catch (IllegalEventPayloadException e){
            throw new IllegalEventPayloadException(event.getJsonPayload());
        }
        catch (Exception e){
            throw e;
        }
    }

    @EventListener
    @Async
    public void requirementCreated(RequirementCreatedEvent requirementCreatedEvent){
        logger.info("requirement created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,requirementCreatedEvent.getClass(), requirementCreatedEvent.getJsonPayload()));

        if (variantRequirementsRepository.existsById(VariantsRequirements.fromJson(requirementCreatedEvent.getJsonPayload()).getRequirementId())) {
            throw new EventEntityAlreadyExistsException();
        }

        try {
            VariantsRequirements variantsRequirements = gson.fromJson(requirementCreatedEvent.getJsonPayload(), VariantsRequirements.class);
            variantRequirementsRepository.save(variantsRequirements);
        }
        catch (Exception e){
            throw new IllegalEventPayloadException(requirementCreatedEvent.getJsonPayload());
        }
    }

    @EventListener
    @Async
    public void requirementUpdated(RequirementUpdatedEvent requirementUpdatedEvent){
        logger.info("requirement updated event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,requirementUpdatedEvent.getClass(), requirementUpdatedEvent.getJsonPayload()));

        if (!variantRequirementsRepository.existsById(VariantsRequirements.fromJson(requirementUpdatedEvent.getJsonPayload()).getRequirementId())) {
            throw new EventEntityDoesNotExistException();
        }

        try {
            VariantsRequirements variantsRequirements = gson.fromJson(requirementUpdatedEvent.getJsonPayload(), VariantsRequirements.class);
            variantRequirementsRepository.save(variantsRequirements);
        }
        catch (Exception e){
            throw new IllegalEventPayloadException(requirementUpdatedEvent.getJsonPayload());
        }
    }

    @EventListener
    @Async
    public void requirementDeleted(RequirementDeletedEvent event){
        logger.info("requirement deleted event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));

        if (!variantRequirementsRepository.existsById(VariantsRequirements.fromJson(event.getJsonPayload()).getRequirementId())) {
            throw new EventEntityDoesNotExistException();
        }

        try {
            VariantsRequirements variantsRequirements = gson.fromJson(event.getJsonPayload(), VariantsRequirements.class);
            variantRequirementsRepository.delete(variantsRequirements);
        }
        catch (Exception e){
            throw new IllegalEventPayloadException(event.getJsonPayload());
        }
    }




}
