package com.evatool.variants.application;

import com.evatool.variants.controller.VariantController;
import com.evatool.variants.entities.Variant;
import com.evatool.variants.entities.VariantsStakeholder;
import com.evatool.variants.repositories.VariantRepository;
import com.evatool.variants.services.VariantMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class VariantsControllerTest {

    @Autowired
    private VariantController variantController;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private VariantMapper variantMapper;

    @Test
    public void testGetAllVariants(){
        // Get new variant into database
        var variant = variantRepository.save(new Variant());

        variant.setVariantsStakeholder(new VariantsStakeholder());

        // Get all Variants and filter by id
        var savedVariants = variantController.getAllVariants().getBody().getContent()
                .stream().filter(var -> var.getUuid().equals(variant.getId())).toArray();

        // Check if variant is contained in list of all variants
        Assertions.assertEquals(savedVariants.length, 1);
    }

    @Test
    public void testGetVariantById(){
        // Get new variant into database
        var variant = variantRepository.save(new Variant());

        variant.setVariantsStakeholder(new VariantsStakeholder());

        // Get variant out of the system
        var response = variantController.getVariant(variant.getId());

        // Check if returned variant is equal to the saved one
        Assertions.assertEquals(response.getBody().getUuid(), variant.getId());
    }

    @Test
    public void testPostVariant(){
        // Get new variant into database
        var variant = new Variant();

        variant.setVariantsStakeholder(new VariantsStakeholder());

        variant.setTitle("Test");
        var response = variantController.createVariant(variantMapper.toDto(variant));

        // Check if variant was created correctly
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(variant.getTitle(), response.getBody().getTitle());
    }

    @Test
    public void testUpdateVariant(){
        // Get new variant into database
        var variant = variantRepository.save(new Variant());
        variant.setVariantsStakeholder(new VariantsStakeholder());
        variant.setTitle("Test");

        // Update Variant
        var updatedVariant = variantController.updateVariant(variant.getId(), variantMapper.toDto(variant)).getBody();

        // Check if Variant was successfully updated
        Assertions.assertEquals(updatedVariant.getTitle(), variant.getTitle());
    }

    @Test
    public void testDeleteVariant(){
        // Get new variant into database
        var variant = variantRepository.save(new Variant());
        variant.setVariantsStakeholder(new VariantsStakeholder());

        // Delete Variant
        variantController.deleteVariant(variant.getId());
        var deleted = variantRepository.findVariantById(variant.getId());

        // Check if entry was successfully deleted
        Assertions.assertNull(deleted);
    }
}