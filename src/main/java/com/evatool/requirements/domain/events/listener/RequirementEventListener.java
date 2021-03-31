package com.evatool.requirements.domain.events.listener;

import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.dimension.DimensionCreatedEvent;
import com.evatool.global.event.dimension.DimensionDeletedEvent;
import com.evatool.global.event.dimension.DimensionUpdatedEvent;
import com.evatool.global.event.impact.ImpactCreatedEvent;
import com.evatool.global.event.impact.ImpactDeletedEvent;
import com.evatool.global.event.impact.ImpactUpdatedEvent;
import com.evatool.global.event.variants.VariantCreatedEvent;
import com.evatool.global.event.variants.VariantDeletedEvent;
import com.evatool.global.event.variants.VariantUpdatedEvent;
import com.evatool.requirements.domain.entity.RequirementDimension;
import com.evatool.requirements.domain.entity.RequirementsAnalysis;
import com.evatool.requirements.domain.entity.RequirementsImpact;
import com.evatool.requirements.domain.entity.RequirementsVariant;
import com.evatool.requirements.common.exceptions.EventEntityAlreadyExistsException;
import com.evatool.requirements.common.exceptions.EventEntityDoesNotExistException;
import com.evatool.requirements.domain.events.json.ImpactJson;
import com.evatool.requirements.domain.repository.RequirementAnalysisRepository;
import com.evatool.requirements.domain.repository.RequirementDimensionRepository;
import com.evatool.requirements.domain.repository.RequirementsImpactsRepository;
import com.evatool.requirements.domain.repository.RequirementsVariantsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RequirementEventListener {

    private static final String DEBUGFORMAT = "EVENT: %s With Payload %s";
    final Logger logger = LoggerFactory.getLogger(RequirementEventListener.class);

    @Autowired
    RequirementsImpactsRepository requirementsImpactsRepository;
    @Autowired
    RequirementsVariantsRepository requirementsVariantsRepository;
    @Autowired
    RequirementDimensionRepository requirementDimensionRepository;
    @Autowired
    RequirementAnalysisRepository requirementAnalysisRepository;

    @EventListener
    @Async
    public void impactCreated(ImpactCreatedEvent event) {
        logger.info("impact created event ");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        ImpactJson impactJson = ImpactJson.fromJson(event.getJsonPayload());
        if (requirementsImpactsRepository.existsById(UUID.fromString(impactJson.getId()))) {
            throw new EventEntityAlreadyExistsException();
        }
        RequirementsImpact requirementsImpact = RequirementsImpact.fromJson(event.getJsonPayload());
        Optional<RequirementDimension> requirementDimension = requirementDimensionRepository.findById(UUID.fromString(impactJson.getDimensionId()));
        if(requirementDimension.isEmpty()){
            logger.error("Dimension ist nicht vorhanden. ID:[{}]",impactJson.getDimensionId());
        }
        requirementsImpact.setRequirementDimension(requirementDimension.get());
        requirementsImpactsRepository.save(requirementsImpact);
    }

    @EventListener
    @Async
    public void impactUpdated(ImpactUpdatedEvent event) {
        logger.info("Impact updated event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        if (!requirementsImpactsRepository.existsById(RequirementsImpact.fromJson(event.getJsonPayload()).getId())) {
            throw new EventEntityDoesNotExistException();
        }
        requirementsImpactsRepository.save(RequirementsImpact.fromJson(event.getJsonPayload()));
    }

    @EventListener
    @Async
    public void impactDeleted(ImpactDeletedEvent event) {
        logger.info("Impact deleted event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        if (!requirementsImpactsRepository.existsById(RequirementsImpact.fromJson(event.getJsonPayload()).getId())) {
            throw new EventEntityDoesNotExistException();
        }
        requirementsImpactsRepository.delete(RequirementsImpact.fromJson(event.getJsonPayload()));
    }
    @EventListener
    @Async
    public void dimensionCreated(DimensionCreatedEvent event) {
        logger.info("dimension created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        if (requirementDimensionRepository.existsById(RequirementDimension.fromJson(event.getJsonPayload()).getId())) {
            throw new EventEntityAlreadyExistsException();
        }
        requirementDimensionRepository.save(RequirementDimension.fromJson(event.getJsonPayload()));
    }

    @EventListener
    @Async
    public void dimensionUpdated(DimensionUpdatedEvent event) {
        logger.info("dimension updated event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        if (!requirementDimensionRepository.existsById(RequirementDimension.fromJson(event.getJsonPayload()).getId())) {
            throw new EventEntityDoesNotExistException();
        }
        requirementDimensionRepository.save(RequirementDimension.fromJson(event.getJsonPayload()));
    }

    @EventListener
    @Async
    public void dimensionDeleted(DimensionDeletedEvent event) {
        logger.info("dimension deleted event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        if (!requirementDimensionRepository.existsById(RequirementDimension.fromJson(event.getJsonPayload()).getId())) {
            throw new EventEntityDoesNotExistException();
        }
        requirementDimensionRepository.delete(RequirementDimension.fromJson(event.getJsonPayload()));
    }

     @EventListener
     @Async
        public void variantsCreated(VariantCreatedEvent event){
        logger.info("Variant created event");
         if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getVariantJson()));
        if (requirementsVariantsRepository.existsById(RequirementsVariant.fromJson(event.getVariantJson()).getId())) {
            throw new EventEntityAlreadyExistsException();
        }
        requirementsVariantsRepository.save(RequirementsVariant.fromJson(event.getVariantJson()));
     }

     @EventListener
     @Async
        public void variantsUpdated (VariantUpdatedEvent event){
        logger.info("variant updated event");
         if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getVariantJson()));
        if (!requirementsVariantsRepository.existsById(RequirementsVariant.fromJson(event.getVariantJson()).getId())) {
            throw new EventEntityDoesNotExistException();
        }
        requirementsVariantsRepository.save(RequirementsVariant.fromJson(event.getVariantJson()));
     }

     @EventListener
     @Async
        public void variantsDeleted(VariantDeletedEvent event){
        logger.info("variant deleted event");
         if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getVariantJson()));
        if (!requirementsVariantsRepository.existsById(RequirementsVariant.fromJson(event.getVariantJson()).getId())) {
             throw new EventEntityDoesNotExistException();
        }
        requirementsVariantsRepository.delete(RequirementsVariant.fromJson(event.getVariantJson()));
     }

    @EventListener
    @Async
    public void analyseCreated(AnalysisCreatedEvent event){
        logger.info("analyse created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        if (requirementAnalysisRepository.existsById(RequirementsAnalysis.fromJson(event.getJsonPayload()).getAnalysisId())) {
            throw new EventEntityAlreadyExistsException();
        }
        requirementAnalysisRepository.save(RequirementsAnalysis.fromJson(event.getJsonPayload()));

    }

    @EventListener
    @Async
    public void analyseDeleted(AnalysisDeletedEvent event){
        logger.info("analyse deleted event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getSource().toString() ));
        if (!requirementAnalysisRepository.existsById(RequirementsAnalysis.fromJson(event.getJsonPayload()).getAnalysisId())) {
            throw new EventEntityDoesNotExistException();
        }
        requirementAnalysisRepository.delete(RequirementsAnalysis.fromJson(event.getJsonPayload()));
    }
}
