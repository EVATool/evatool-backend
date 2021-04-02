package com.evatool.analysis.common;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.application.dto.ValueDtoMapper;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.enums.ValueType;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.model.Value;

public class TestDataGenerator {

  public static Analysis getAnalysis() {
    return new Analysis("AnalysisName", "descriptionTitle");
  }

  public static Stakeholder getStakeholder() {
    return new Stakeholder("StakeholderName", 10, StakeholderLevel.NATURAL_PERSON);
  }

  public static AnalysisDTO getAnalysisDTO(String name, String description) {
    var analysisDTO = new AnalysisDTO();
    analysisDTO.setAnalysisName(name);
    analysisDTO.setAnalysisDescription(description);
    return analysisDTO;
  }

  public static StakeholderDTO getStakeholderDTO(String stakeholderName, int priority,
      StakeholderLevel stakeholderLevel) {
    var stakeholderDTO = new StakeholderDTO();
    stakeholderDTO.setStakeholderName(stakeholderName);
    stakeholderDTO.setPriority(priority);
    stakeholderDTO.setStakeholderLevel(stakeholderLevel);
    return stakeholderDTO;
  }

  public static Value createDummyValue() {
    return new Value("dummyValue", ValueType.ECONOMIC, "dummyValueDescription");
  }

  public static ValueDto createDummyValueDto() {
    return ValueDtoMapper.toDto(createDummyValue());
  }
}
