package com.evatool.variants.common;

import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.entities.VariantsAnalysis;

import java.util.UUID;

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

    public static Variant getVariant(String title,String description,VariantsAnalysis variantsAnalysis)
    {
        Variant variant = new Variant();
        variant.setTitle(title);
        variant.setDescription(description);
        variant.setGuiId(null);
        variant.setArchived(false);
        variant.setVariantsAnalysis(variantsAnalysis);
        return variant;

    }

    public static VariantsAnalysis getVariantsAnalysis()
    {
        return new VariantsAnalysis(UUID.randomUUID());
    }


}
