package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.interfaces.AnalysisController;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;

import java.util.UUID;

import static com.evatool.analysis.common.TestDataGenerator.getAnalysisDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AnalysisControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnalysisController analysisController;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Test
    public void testAnalysisController_ThrowException() {

        Analysis analysis = new Analysis("TestName", "description");

        //create analysis
        AnalysisDTO analysisDTO = getAnalysisDTO(analysis.getAnalysisName(), analysis.getDescription());
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
        Exception exception = assertThrows(EntityNotFoundException.class, () -> analysisController.getAnalysisById(analysisDTOObj.getRootEntityID()).getContent());

    }

    @Test
    void testNumericId_InsertTwoAnalyses_NumericIdIncrementsByOne() {
        // given
        var analysis1 = getAnalysisDTO("", "");
        var analysis2 = getAnalysisDTO("", "");

        // when
        var httpEntity1 = new HttpEntity<>(analysis1);
        var response1 = testRestTemplate.postForEntity(
                "/analysis", httpEntity1, AnalysisDTO.class);

        var httpEntity2 = new HttpEntity<>(analysis2);
        var response2 = testRestTemplate.postForEntity(
                "/analysis", httpEntity2, AnalysisDTO.class);

        // then
        var numericId1 = Integer.valueOf(response1.getBody().getUniqueString().replace("ANA", ""));
        var numericId2 = Integer.valueOf(response2.getBody().getUniqueString().replace("ANA", ""));
        assertThat(numericId1).isEqualTo(numericId2 - 1);
    }
}
