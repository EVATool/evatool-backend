package com.evatool.variants.application;

import com.evatool.variants.controller.VariantController;
import com.evatool.variants.entities.Variant;
import com.evatool.variants.entities.VariantsStakeholder;
import com.evatool.variants.repositories.VariantRepository;
import com.evatool.variants.services.VariantMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class VariantsControllerTest {

    @Autowired
    private VariantController variantController;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private VariantMapper variantMapper;

    @Test
    void testGetAllVariants(){
        // Get new variant into database
        var variant = variantRepository.save(new Variant());

        variant.setVariantsStakeholder(new VariantsStakeholder());

        // Get all Variants and filter by id
        var savedVariants = Objects.requireNonNull(variantController.getAllVariants().getBody()).getContent()
                .stream().filter(var -> var.getUuid().equals(variant.getId())).toArray();

        // Check if variant is contained in list of all variants
        Assertions.assertEquals(1, savedVariants.length);
    }

    @Test
    void testGetVariantById(){
        // Get new variant into database
        var variant = variantRepository.save(new Variant());

        variant.setVariantsStakeholder(new VariantsStakeholder());

        // Get variant out of the system
        var response = variantController.getVariant(variant.getId());

        // Check if returned variant is equal to the saved one
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getUuid(), variant.getId());
    }

    @Test
    void testPostVariant(){
        // Get new variant into database
        var variant = new Variant();

        variant.setVariantsStakeholder(new VariantsStakeholder());

        variant.setTitle("Test");
        var response = variantController.createVariant(variantMapper.toDto(variant));

        // Check if variant was created correctly
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(variant.getTitle(), Objects.requireNonNull(response.getBody()).getTitle());
    }

    @Test
    void testUpdateVariant(){
        // Get new variant into database
        var variant = variantRepository.save(new Variant());
        variant.setVariantsStakeholder(new VariantsStakeholder());
        variant.setTitle("Test");

        // Update Variant
        var updatedVariant = variantController.updateVariant(variant.getId(), variantMapper.toDto(variant)).getBody();

        // Check if Variant was successfully updated
        assert updatedVariant != null;
        Assertions.assertEquals(updatedVariant.getTitle(), variant.getTitle());
    }

    @Test
    void testDeleteVariant(){
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