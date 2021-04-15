package com.evatool.analysis.application.controller;


import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.interfaces.StakeholderController;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.evatool.analysis.common.TestDataGenerator.getAnalysis;
import static com.evatool.analysis.common.TestDataGenerator.getStakeholderDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StakeholderControllerTest {

    @Autowired
    private StakeholderController stakeholderController;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Test
    void testStakeholderController_ThrowException() {

        Analysis analysis = analysisRepository.save(getAnalysis());

        Stakeholder stakeholder = new Stakeholder("TestName", 1, StakeholderLevel.NATURAL_PERSON);
        stakeholder.setAnalysis(analysis);

        //create stakeholder
        StakeholderDTO stakeholderDTO = getStakeholderDTO(stakeholder.getStakeholderName(), stakeholder.getPriority(), stakeholder.getStakeholderLevel());
        stakeholderDTO.setAnalysisId(stakeholder.getAnalysis().getAnalysisId());
        StakeholderDTO stakeholderDTOObj = stakeholderController.addStakeholder(stakeholderDTO).getContent();


        //check is stakeholder created
        assertThat(stakeholderController.getStakeholderById(stakeholderDTOObj.getRootEntityID())).isNotNull();

        //change stakeholder title
        String testName = "TestName";
        stakeholderDTOObj.setStakeholderName(testName);
        stakeholderController.updateStakeholder(stakeholderDTOObj);

        //check is stakeholder title changed
        StakeholderDTO stakeholderAfterUpdate = stakeholderController.getStakeholderById(stakeholderDTOObj.getRootEntityID()).getContent();

        assertThat(stakeholderAfterUpdate.getStakeholderName()).isEqualTo(testName);

        //delete stakeholder
        UUID id = stakeholderDTOObj.getRootEntityID();
        stakeholderController.deleteStakeholder(id);

        //check is stakeholder deleted
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> stakeholderController.getStakeholderById(stakeholderDTOObj.getRootEntityID()).getContent());
    }
}
