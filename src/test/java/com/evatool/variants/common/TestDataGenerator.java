package com.evatool.variants.common;

import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.domain.entities.VariantsAnalysis;

public class TestDataGenerator {

    public static VariantDto getVariantDto(VariantsAnalysis variantsAnalysis) {
        var variantDto = new VariantDto();
        variantDto.setArchived(false);
        variantDto.setDescription("Description");
        variantDto.setTitle("title");
        variantDto.setAnalysisId(variantsAnalysis.getAnalysisId());
        variantDto.setGuiId("VAR-001");
        return variantDto;
    }
}
