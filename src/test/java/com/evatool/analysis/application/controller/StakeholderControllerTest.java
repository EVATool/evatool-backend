package com.evatool.analysis.application.controller;


import com.evatool.analysis.application.interfaces.StakeholderController;
import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.domain.enums.Dimension;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.model.AnalysisImpacts;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.repository.AnalysisImpactRepository;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.evatool.analysis.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StakeholderControllerTest {

    @Autowired
    private StakeholderController stakeholderController;

    @Autowired
    private AnalysisImpactRepository analysisImpactRepository;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Test
    public void testStakeholderController_ThrowException() {

        AnalysisImpacts analysisImpacts = getAnalysisImpacts(Dimension.SAFETY);
        analysisImpactRepository.save(analysisImpacts);

        Stakeholder stakeholder = new Stakeholder("TestName", 1, StakeholderLevel.NATURAL_PERSON);

        //create stakeholder
        StakeholderDTO stakeholderDTO = getStakeholderDTO(stakeholder.getStakeholderName(), stakeholder.getPriority(), stakeholder.getStakeholderLevel(), stakeholder.getAnalysisImpacts());
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
        Exception exception = assertThrows(EntityNotFoundException.class, ()-> stakeholderController.getStakeholderById(stakeholderDTOObj.getRootEntityID()).getContent());

    }
}
