package com.evatool.analysis.application.controller;


import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.interfaces.StakeholderController;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.enums.ValueType;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.evatool.analysis.common.TestDataGenerator.getStakeholderDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StakeholderControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StakeholderController stakeholderController;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Test
    public void testStakeholderController_ThrowException() {


        Stakeholder stakeholder = new Stakeholder("TestName", 1, StakeholderLevel.NATURAL_PERSON);

        //create stakeholder
        StakeholderDTO stakeholderDTO = getStakeholderDTO(stakeholder.getStakeholderName(), stakeholder.getPriority(), stakeholder.getStakeholderLevel());
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
        Exception exception = assertThrows(EntityNotFoundException.class, () -> stakeholderController.getStakeholderById(stakeholderDTOObj.getRootEntityID()).getContent());

    }

    @Test
    void testFindAllLevels_ReturnAllPossibleLevels() {
        // given

        // when
        var valueTypes = testRestTemplate.getForEntity(
                "/stakeholders/levels", StakeholderLevel[].class);

        // then
        assertThat(valueTypes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(valueTypes.getBody()).isEqualTo(StakeholderLevel.values());
    }
}
