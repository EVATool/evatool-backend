package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.AnalysisMapper;
import com.evatool.analysis.application.interfaces.AnalysisController;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AnalysisControllerTest {

    @Autowired
    private AnalysisController analysisController;

    @Autowired
    private AnalysisRepository analysisRepository;

    private AnalysisMapper analysisMapper = new AnalysisMapper();

    @Test
    void testAnalysisController_ThrowException() {

        Analysis analysis = new Analysis("TestName", "description");

        //create analysis
        AnalysisDTO analysisDTO = analysisMapper.map(analysis);
        AnalysisDTO analysisDTOObj = analysisController.addAnalysis(analysisDTO).getContent();

        //check is analysis created
        assertThat(analysisController.getAnalysisById(analysisDTOObj.getRootEntityID())).isNotNull();

        //change analysis title
        String testName = "TestName";
        analysisDTOObj.setAnalysisName(testName);
        analysisController.updateAnalysis(analysisDTOObj);

        //check is analysis title changed
        AnalysisDTO analysisAfterUpdate = analysisController.getAnalysisById(analysisDTOObj.getRootEntityID()).getContent();

        assertThat(analysisAfterUpdate.getAnalysisName()).isEqualTo(testName);

        //delete analysis
        UUID id = analysisDTOObj.getRootEntityID();
        analysisController.deleteAnalysis(id);

        //check is analysis deleted
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> analysisController.getAnalysisById(analysisDTOObj.getRootEntityID()).getContent());
    }

    @Test
    void testDeepCopyAnalysis() {
        // given


        // when


        // then

    }
}
