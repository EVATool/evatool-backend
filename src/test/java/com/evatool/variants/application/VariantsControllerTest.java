package com.evatool.variants.application;

import com.evatool.variants.application.controller.VariantController;
import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.common.error.exceptions.VariantCannotDeleteException;
import com.evatool.variants.common.error.exceptions.VariantsEntityNotFoundException;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.EntityModel;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.evatool.variants.common.TestDataGenerator.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Profile("!non-async")
class VariantsControllerTest {

    @Autowired
    private VariantController variantController;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private VariantsAnalysisRepository variantsAnalysisRepository;

    @Test
    void testVariantsController_ThrowException() {

        VariantsAnalysis variantsAnalysis = new VariantsAnalysis(UUID.randomUUID());
        variantsAnalysis = variantsAnalysisRepository.save(variantsAnalysis);
        assertThat(variantsAnalysis).isNotNull();

        VariantDto variantDto = getVariantDto(variantsAnalysis);

        VariantDto variant = Objects.requireNonNull(variantController.createVariant(variantDto).getBody()).getContent();
        assertThat(variant).isNotNull();

        List<EntityModel<VariantDto>> variantList = variantController.getAllVariants().getBody();
        assertThat(variantList).isNotNull().isNotEmpty();

        List<EntityModel<VariantDto>> variantList1 = variantController.getAllVariants().getBody();
        assertThat(variantList1).isNotNull().isNotEmpty();

        VariantDto variant1 = Objects.requireNonNull(variantController.getVariant(variant.getId()).getBody()).getContent();
        assertThat(variant1).isNotNull();

        String newTitle = "new Title";
        variant1.setTitle(newTitle);

        VariantDto updatedVariant = Objects.requireNonNull(variantController.updateVariant(variant1).getBody()).getContent();

        assertThat(updatedVariant).isNotNull();
        assertThat(updatedVariant.getTitle()).isEqualTo(newTitle);
        UUID tempId = updatedVariant.getId();
        assertThrows(VariantCannotDeleteException.class, () -> variantController.deleteVariant(updatedVariant.getId()));

        updatedVariant.setArchived(true);
        variantController.updateVariant(updatedVariant);
        variantController.deleteVariant(updatedVariant.getId());
        
        assertThrows(VariantsEntityNotFoundException.class, ()-> variantController.getVariant(tempId));
    }
}
