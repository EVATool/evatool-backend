package com.evatool.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class ImportExportAnalysisDto {

    private AnalysisDto analysis;
    private List<ValueTypeDto> valueTypes;
    private List<ValueDto> values;
    private List<StakeholderDto> stakeholders;
    private List<ImpactDto> impacts;
    private List<RequirementDto> requirements;
    private List<RequirementDeltaDto> requirementDeltas;
    private List<VariantTypeDto> variantTypes;
    private List<VariantDto> variants;

    public ImportExportAnalysisDto(AnalysisDto analysisDtoList,
                                   List<ValueTypeDto> valueTypesDtoList,
                                   List<ValueDto> valuesDtoList,
                                   List<StakeholderDto> stakeholdersDtoList,
                                   List<ImpactDto> impactsDtoList,
                                   List<RequirementDto> requirementsDtoList,
                                   List<RequirementDeltaDto> requirementDeltasDtoList,
                                   List<VariantTypeDto> variantTypesDtoList,
                                   List<VariantDto> variantsDtoList) {
        this.analysis = analysisDtoList;
        this.valueTypes = valueTypesDtoList;
        this.values = valuesDtoList;
        this.stakeholders = stakeholdersDtoList;
        this.impacts = impactsDtoList;
        this.requirements = requirementsDtoList;
        this.requirementDeltas = requirementDeltasDtoList;
        this.variantTypes = variantTypesDtoList;
        this.variants = variantsDtoList;
    }
}
