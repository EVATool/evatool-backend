package com.evatool.application.service;

import com.evatool.application.service.impl.ImportExportServiceImpl;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.common.enums.ValueType;
import com.evatool.common.util.PrintUtil;
import com.evatool.domain.entity.*;
import com.evatool.domain.repository.*;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ImportExportServiceTest {

    @Autowired
    private ImportExportServiceImpl importExportService;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private ImpactRepository impactRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private RequirementDeltaRepository requirementDeltaRepository;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Autowired
    private ValueRepository valueRepository;

    @Autowired
    private VariantRepository variantRepository;

    @BeforeEach
    protected void clearDatabase() {
        analysisRepository.deleteAll();
    }

    @Test
    void testImportAnalyses() {
        // given

        // when

        // then

    }

    @SneakyThrows
    @Test
    void testExportAnalyses() {
        // given
        var analysis1 = saveDummyAnalysisWithManyChildEntities();
        var analysis2 = saveDummyAnalysisWithManyChildEntities();

        // when
        var analysisJson = importExportService.exportAnalyses(Arrays.asList(analysis1.getId(), analysis2.getId()));

        // then
        System.out.println(analysisJson);
        PrintUtil.prettyPrintJson(analysisJson);
    }

    private Analysis saveDummyAnalysisWithManyChildEntities() {
        var analysis1 = new Analysis("ANA1 NAME", "ANA1 DESC", false);
        analysisRepository.save(analysis1);

        // Values.
        var value1 = new Value("", "", ValueType.SOCIAL, false, analysis1);
        valueRepository.save(value1);

        // Stakeholders.
        var stakeholder1 = new Stakeholder("", StakeholderPriority.ONE, StakeholderLevel.SOCIETY, analysis1);
        stakeholderRepository.save(stakeholder1);

        // Impacts.
        var impact1 = new Impact(0.2f, "", value1, stakeholder1, analysis1);
        impactRepository.save(impact1);

        // Requirements.
        var requirement1 = new Requirement("", analysis1);
        requirementRepository.save(requirement1);

        // Requirement Deltas.
        var requirementDelta1 = new RequirementDelta(0.1f, impact1, requirement1);
        requirementDeltaRepository.save(requirementDelta1);

        // Variants.
        var variant1 = new Variant("", "", false, analysis1);
        variantRepository.save(variant1);

        return analysis1;
    }
}
