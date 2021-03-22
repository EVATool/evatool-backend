package com.evatool.variants.application.service;


import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.application.dto.VariantMapper;
import com.evatool.variants.common.error.exceptions.IllegalAnalysisException;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.repositories.VariantRequirementsRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import com.google.common.annotations.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;

import java.util.UUID;

import static com.evatool.variants.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Profile("!non-async")
class VariantMapperTest {

    @Autowired
    VariantMapper variantMapper;

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @Test
    void testOnApplicationMapper_VariantsMapperTest_map_fromDTO()
    {
        Variant variant = getVariant("Test1","TEST2",getVariantsAnalysis());
        VariantDto variantDto = variantMapper.toDto(variant);
        assertThat(variant.getTitle()).isEqualTo(variantDto.getTitle());

        assertThrows(IllegalAnalysisException.class, ()->variantMapper.fromDto(variantDto));
    }

    @Test
    void testOnApplicationMapper_VariantsMapperTest_map_toDTO()
    {
        VariantsAnalysis variantsAnalysis = getVariantsAnalysis();
        VariantsAnalysis variantsAnalysis1 = variantsAnalysisRepository.save(variantsAnalysis);

        Variant variant1 = getVariant("Test12","Description",variantsAnalysis1);

        VariantDto variantDto1 = variantMapper.toDto(variant1);
        assertThat(variant1.getTitle()).isEqualTo(variantDto1.getTitle());

        Variant variantDto2 = variantMapper.fromDto(variantDto1);

        assertThat(variant1.getTitle()).isEqualTo(variantDto2.getTitle());
    }

}
