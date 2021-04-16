package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.AnalysisMapper;
import com.evatool.analysis.application.interfaces.AnalysisController;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.analysis.domain.repository.ValueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.evatool.analysis.common.TestDataGenerator.createDummyValue;
import static com.evatool.analysis.common.TestDataGenerator.getAnalysis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AnalysisControllerTest {

    @Autowired
    private AnalysisController analysisController;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    ValueRepository valueRepository;

    private AnalysisMapper analysisMapper = new AnalysisMapper();

    @Test
    void testAnalysisController_ThrowException() {

        Analysis analysis = new Analysis("TestName", "description");

        //create analysis
        AnalysisDTO analysisDTO = analysisMapper.map(analysis);
        AnalysisDTO analysisDTOObj = analysisController.addAnalysis(analysisDTO).getBody().getContent();

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
        var templateAnalysis = analysisRepository.save(getAnalysis());

        var value1 = createDummyValue();
        value1.setAnalysis(templateAnalysis);
        value1 = valueRepository.save(value1);

        var value2 = createDummyValue();
        value2.setAnalysis(templateAnalysis);
        value2 = valueRepository.save(value2);

        // when
        var newAnalysis = new Analysis("deep copy", "deep copy");
        var newAnalysisDto = analysisController.deepCopyAnalysis(templateAnalysis.getAnalysisId(), analysisMapper.map(newAnalysis)).getBody().getContent();

        // then
        var templateValues = valueRepository.findAllByAnalysisAnalysisId(templateAnalysis.getAnalysisId());
        var newAnalysisValues = valueRepository.findAllByAnalysisAnalysisId(newAnalysisDto.getRootEntityID());

        assertThat(templateValues.size()).isEqualTo(newAnalysisValues.size());

        assertThat(templateValues.get(0).getAnalysis().getAnalysisName()).isEqualTo(templateAnalysis.getAnalysisName());
        assertThat(templateValues.get(1).getAnalysis().getAnalysisName()).isEqualTo(templateAnalysis.getAnalysisName());

        assertThat(newAnalysisValues.get(0).getAnalysis().getAnalysisName()).isEqualTo(newAnalysis.getAnalysisName());
        assertThat(newAnalysisValues.get(1).getAnalysis().getAnalysisName()).isEqualTo(newAnalysis.getAnalysisName());
    }
}
