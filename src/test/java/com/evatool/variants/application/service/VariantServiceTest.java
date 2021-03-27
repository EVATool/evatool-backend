package com.evatool.variants.application.service;

import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.application.services.VariantService;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;
import static com.evatool.variants.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Profile("!non-async")
class VariantServiceTest {


    @Autowired
    VariantRepository variantRepository;

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @Autowired
    VariantService variantService;

    @Test
    void testServiceMethods(){

        VariantsAnalysis variantsAnalysis = variantsAnalysisRepository.save(getVariantsAnalysis());

        VariantDto variantDto = getVariantDto(variantsAnalysis);

        VariantDto variantDto1 = variantService.createVariant(variantDto);
        assertThat(variantDto1).isNotNull();

        assertThat(variantService.getVariant(variantDto1.getId())).isNotNull();
        assertThat(variantService.getVariantsByAnalysis(variantDto1.getAnalysisId())).isNotNull();
        assertThat(variantService.getAllVariants()).isNotNull().isNotEmpty();

        String newTitle = "newTitle";
        variantDto1.setTitle(newTitle);
        variantService.updateVariant(variantDto1);
        assertThat(variantService.getVariant(variantDto1.getId()).getTitle()).isEqualTo(newTitle);

    }
}
