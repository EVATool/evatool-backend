package com.evatool.analysis.common;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.application.dto.ValueDtoMapper;
import com.evatool.analysis.common.error.ValueType;
import com.evatool.analysis.domain.enums.Dimension;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.*;
import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.ImpactStakeholderDto;
import com.evatool.impact.application.dto.mapper.ImpactDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper;
import com.evatool.impact.common.DimensionType;

public class TestDataGenerator {

    public static Analysis getAnalysis() {
        return new Analysis("AnalysisName", "descriptionTitle");
    }

    public static Stakeholder getStakholder() {
        return new Stakeholder("StakeholderName", Integer.valueOf(10), StakeholderLevel.NATURAL_PERSON);
    }



    public static AnalysisDTO getAnalysisDTO(String name, String description) {

        var analysisDTO = new AnalysisDTO();

        analysisDTO.setAnalysisName(name);
        analysisDTO.setAnalysisDescription(description);
        return analysisDTO;

    }

    public static StakeholderDTO getStakeholderDTO(String stakeholderName, int priority, StakeholderLevel stakeholderLevel, AnalysisImpacts impacts) {
        var stakeholderDTO = new StakeholderDTO();

        stakeholderDTO.setStakeholderName(stakeholderName);
        stakeholderDTO.setPriority(priority);
        stakeholderDTO.setStakeholderLevel(stakeholderLevel);

        return stakeholderDTO;
    }

    public static AnalysisImpacts getAnalysisImpacts(Dimension dimension) {
        return new AnalysisImpacts("title", "description", 1, dimension);
    }

    public static Value createDummyValue() {
        return new Value("dummyValue", ValueType.ECONOMIC, "dummyValueDescription");
    }

    public static ValueDto createDummyValueDto() {
        return ValueDtoMapper.toDto(createDummyValue());
    }


}
