package com.evatool.variants.application;

import com.evatool.variants.application.controller.VariantController;
import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.application.dto.VariantMapper;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.UUID;

import static com.evatool.variants.common.TestDataGenerator.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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

        variantController.createVariant(variantDto);






    }
}
