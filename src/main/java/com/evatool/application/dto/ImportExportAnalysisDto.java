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
    private List<ValueDto> values;
    private List<StakeholderDto> stakeholders;
    private List<ImpactDto> impacts;
    private List<RequirementDto> requirements;
    private List<RequirementDeltaDto> requirementDeltas;
    private List<VariantDto> variants;

    public ImportExportAnalysisDto(AnalysisDto analysisDtoList,
                                   List<ValueDto> valuesDtoList,
                                   List<StakeholderDto> stakeholdersDtoList,
                                   List<ImpactDto> impactsDtoList,
                                   List<RequirementDto> requirementsDtoList,
                                   List<RequirementDeltaDto> requirementDeltasDtoList,
                                   List<VariantDto> variantsDtoList) {
        this.analysis = analysisDtoList;
        this.values = valuesDtoList;
        this.stakeholders = stakeholdersDtoList;
        this.impacts = impactsDtoList;
        this.requirements = requirementsDtoList;
        this.requirementDeltas = requirementDeltasDtoList;
        this.variants = variantsDtoList;
    }
}
