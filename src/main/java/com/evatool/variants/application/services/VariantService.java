package com.evatool.variants.application.services;

import com.evatool.global.event.variants.VariantCreatedEvent;
import com.evatool.global.event.variants.VariantDeletedEvent;
import com.evatool.global.event.variants.VariantUpdatedEvent;
import com.evatool.variants.application.controller.VariantController;
import com.evatool.variants.application.dto.VariantMapper;
import com.evatool.variants.common.error.exceptions.VariantsEntityNotFoundException;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.domain.events.VariantsEventPublisher;
import com.evatool.variants.domain.repositories.VariantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        logger.info("Find all Variants ");
        return variantMapper.mapAll(variantList);
    }

    /**
     * Returns an existing Variant from the Repository
     *
     * @param id Existing id to return the related Variant
     * @return A ResponseEntity containing a message and corresponding Http-Status Code
     */
    public ResponseEntity<VariantDto> getVariant(UUID id) {
        Variant variant = variantRepository.findVariantById(id);
        VariantDto variantDto = variantMapper.toDto(variant);
        if (variant == null) {
            throw new VariantsEntityNotFoundException(id.toString());
        } else {
            variant.add(linkTo(VariantController.class).slash(id).withSelfRel());
            return new ResponseEntity<>(variantDto, HttpStatus.OK);
        }
    }


    /**
     * Returns all Variants from the Repository
     *
     * @return A ResponseEntity containing a message and corresponding Http-Status Code
     */
    public ResponseEntity<CollectionModel<VariantDto>> getAllVariants() {
        List<Variant> variants = variantRepository.findAll();

        variants.forEach(variant -> variant.add(linkTo(VariantController.class).slash(variant.getId()).withSelfRel()));

        Link variantsLink = linkTo(methodOn(VariantController.class).getAllVariants()).withSelfRel();
        CollectionModel<VariantDto> variantCollectionModel = CollectionModel.of(variantMapper.mapAll(variants));
        variantCollectionModel.add(variantsLink);

        return new ResponseEntity<>(variantCollectionModel, HttpStatus.OK);
    }

    /**
     * Updates an existing Variant from the Repository and publishes a corresponding event
     *
     * @param id             Existing id for updating related Variant
     * @param updatedVariant Overwrites the Variant that will be updated
     * @return A ResponseEntity containing a message and corresponding Http-Status Code
     */
    public ResponseEntity<VariantDto> updateVariant(UUID id, VariantDto updatedVariant) {
        Variant variant = variantRepository.findVariantById(id);
        if (variant == null) {
            throw new VariantsEntityNotFoundException(id.toString());
        } else {
            //TODO validate updateVariant
            Variant savedVariant = variantRepository.save(variantMapper.fromDto(updatedVariant));
            VariantDto savedVariantDto = variantMapper.toDto(savedVariant);
            variantsEventPublisher.publishEvent(new VariantUpdatedEvent(savedVariant.toJson()));
            return new ResponseEntity<>(savedVariantDto, HttpStatus.OK);
        }
    }


    /**
     * Deletes an existing Variant from the Repository and publishes a corresponding event
     *
     * @param id Existing id for deleting related Variant
     * @return A ResponseEntity containing a message and corresponding Http-Status Code
     */
    public ResponseEntity<VariantDto> deleteVariant(UUID id) {
        Variant variant = variantRepository.findVariantById(id);
        if (variant == null) {
            throw new VariantsEntityNotFoundException(id.toString());
        } else {
            variantRepository.delete(variant);
            variantsEventPublisher.publishEvent(new VariantDeletedEvent(variant.toJson()));
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * Creates a new Variant, saves it using the repository and publishes a corresponding event
     *
     * @param newVariantDto New VariantDto-Object to be saved
     * @return A ResponseEntity containing a message and corresponding Http-Status Code
     */
    public ResponseEntity<VariantDto> createVariant(VariantDto newVariantDto) {
        //TODO Validate newVariant
        Variant newVariant = variantMapper.fromDto(newVariantDto);
        Variant savedVariant = variantRepository.save(newVariant);
        variantsEventPublisher.publishEvent(new VariantCreatedEvent(savedVariant.toJson()));
        return new ResponseEntity<>(variantMapper.toDto(savedVariant), HttpStatus.CREATED);
    }
}
