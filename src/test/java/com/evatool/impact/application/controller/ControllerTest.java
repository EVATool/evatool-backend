package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactValueDtoMapper;
import com.evatool.impact.application.service.ImpactService;
import com.evatool.impact.application.service.ImpactValueService;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import com.evatool.impact.domain.repository.ImpactValueRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.UUID;

import static com.evatool.impact.application.dto.mapper.ImpactDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ControllerTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected ImpactService impactService;

    @Autowired
    protected ImpactValueRepository valueRepository;

    @Autowired
    protected ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    protected ImpactAnalysisRepository analysisRepository;

    @Autowired
    protected ImpactValueService valueService;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        impactService.deleteAll();
        stakeholderRepository.deleteAll();
        valueRepository.deleteAll();
        analysisRepository.deleteAll();
    }

    protected ImpactDto saveFullDummyImpactDto() {
        var analysis = analysisRepository.save(new ImpactAnalysis(UUID.randomUUID()));
        var value = valueRepository.save(createDummyValue(analysis));
        var impact = createDummyImpact(analysis);
        impact.setValueEntity(value);
        impact.setStakeholder(stakeholderRepository.save(impact.getStakeholder()));
        return impactService.create(toDto(impact));
    }

    protected ImpactDto saveDummyImpactDtoChildren() {
        var impactDto = createDummyImpactDto();
        analysisRepository.save(ImpactAnalysisDtoMapper.fromDto(impactDto.getAnalysis()));
        impactDto.getValueEntity().setAnalysis(impactDto.getAnalysis());
        valueRepository.save(ImpactValueDtoMapper.fromDto(impactDto.getValueEntity()));
        stakeholderRepository.save(ImpactStakeholderDtoMapper.fromDto(impactDto.getStakeholder()));
        return impactDto;
    }
}
