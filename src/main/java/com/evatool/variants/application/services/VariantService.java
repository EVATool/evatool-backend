package com.evatool.variants.application.services;

import com.evatool.global.event.variants.VariantCreatedEvent;
import com.evatool.global.event.variants.VariantDeletedEvent;
import com.evatool.global.event.variants.VariantUpdatedEvent;
import com.evatool.variants.application.controller.VariantController;
import com.evatool.variants.application.dto.VariantMapper;
import com.evatool.variants.common.error.exceptions.VariantStillReferredException;
import com.evatool.variants.common.error.exceptions.VariantsEntityNotFoundException;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.domain.events.VariantsEventPublisher;
import com.evatool.variants.domain.repositories.VariantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * VariantService serves the VariantController
 */
@Service
public class VariantService {

    VariantRepository variantRepository;

    VariantsEventPublisher variantsEventPublisher;
    Logger logger = LoggerFactory.getLogger(VariantService.class);

    @Autowired
    public VariantService(VariantRepository variantRepository,
                          VariantsEventPublisher variantsEventPublisher) {
        this.variantRepository = variantRepository;
        this.variantsEventPublisher = variantsEventPublisher;
    }

    @Autowired
    VariantMapper variantMapper;

    public List<VariantDto> findAll(List<Variant> variantList){
        logger.info("Find all Variants");
        return variantMapper.mapAll(variantList);
    }

    /**
     * Returns an existing Variant from the Repository
     *
     * @param id Existing id to return the related Variant
     * @return VariantDTO
     */
    public VariantDto getVariant(UUID id) {
        logger.debug("findById [{}]",id);
        Variant variant = variantRepository.findVariantById(id);
        if (variant == null) {
            throw new VariantsEntityNotFoundException(id.toString());
        }
        variant.add(linkTo(VariantController.class).slash(id).withSelfRel());
        return variantMapper.toDto(variant);
    }


    /**
     * Returns all Variants from the Repository by analysis id
     *
     * @return Collection of VariantDTO
     */
    public List<VariantDto> getVariantsByAnalysis(UUID id) {
        logger.debug("findByAnalysisId [{}]",id);
        List<Variant> variants = variantRepository.findAll();
        variants.removeIf(variant -> !variant.getVariantsAnalysis().getAnalysisId().equals(id));
        return variantMapper.mapAll(variants);
    }


    /**
     * Returns all Variants from the Repository
     *
     * @return Collection of VariantDTO
     */
    public List<VariantDto> getAllVariants() {
        logger.info("getAllVariants");
        List<Variant> variants = variantRepository.findAll();
        return variantMapper.mapAll(variants);
    }

    /**
     * Updates an existing Variant from the Repository and publishes a corresponding event
     *
     * @param updatedVariant Overwrites the Variant that will be updated
     * @return VariantDTO
     */
    public VariantDto updateVariant(VariantDto updatedVariant) {
        logger.debug("update [{}]",updatedVariant);
        Variant variant = variantRepository.findVariantById(updatedVariant.getId());
        if (variant == null) {
            throw new VariantsEntityNotFoundException(updatedVariant.getId().toString());
        } else {
            Variant savedVariant = variantRepository.save(variantMapper.fromDto(updatedVariant));
            variantsEventPublisher.publishEvent(new VariantUpdatedEvent(savedVariant.toJson()));
            return variantMapper.toDto(savedVariant);
        }
    }

    /**
     * Deletes an existing Variant from the Repository and publishes a corresponding event
     *
     * @param id Existing id for deleting related Variant
     */
    public void deleteVariant(UUID id) {
        logger.debug("delete [{}]",id);
        Variant variant = variantRepository.findVariantById(id);
        if(!variantMapper.checkIfDeletable(id)){
            throw new VariantStillReferredException();
        }
        if (variant == null) {
            throw new VariantsEntityNotFoundException(id.toString());
        } else {
            variantRepository.delete(variant);
            variantsEventPublisher.publishEvent(new VariantDeletedEvent(variant.toJson()));
        }
    }

    /**
     * Creates a new Variant, saves it using the repository and publishes a corresponding event
     *
     * @param newVariantDto New VariantDto-Object to be saved
     * @return VariantDTO
     */
    public VariantDto createVariant(VariantDto newVariantDto) {
        logger.debug("create [{}]",newVariantDto);
        Variant newVariant = variantRepository.save(variantMapper.fromDto(newVariantDto));
        variantsEventPublisher.publishEvent(new VariantCreatedEvent(newVariant.toJson()));
        return variantMapper.toDto(newVariant);
    }
}
