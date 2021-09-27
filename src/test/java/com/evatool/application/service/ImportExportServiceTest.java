package com.evatool.application.service;

import com.evatool.application.mapper.AnalysisMapper;
import com.evatool.application.service.api.ImportExportService;
import com.evatool.application.service.impl.ImportExportServiceImpl;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.common.exception.functional.http400.ImportJsonException;
import com.evatool.common.util.IterableUtil;
import com.evatool.domain.entity.*;
import com.evatool.domain.repository.*;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class ImportExportServiceTest {

    @Autowired
    private ImportExportServiceImpl importExportService;

    @Autowired
    private AnalysisMapper analysisMapper;

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
    private ValueTypeRepository valueTypeRepository;

    @Autowired
    private ValueRepository valueRepository;

    @Autowired
    private VariantTypeRepository variantTypeRepository;

    @Autowired
    private VariantRepository variantRepository;

    @BeforeEach
    protected void clearDatabase() {
        analysisRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void testImportAnalyses() {
        // given
        var analysis1 = saveDummyAnalysisWithFewChildEntities();
        var analysis2 = saveDummyAnalysisWithFewChildEntities();

        var exportAnalysesJson = importExportService.exportAnalyses(Arrays.asList(analysis1.getId(), analysis2.getId()));

        // when
        importExportService.importAnalyses(exportAnalysesJson);

        // then
        var analyses = analysisRepository.findAll();
        assertThat(IterableUtil.iterableSize(analyses)).isEqualTo(4);
    }

    @SneakyThrows
    @Test
    void testImportAnalysis() { // TODO equality checks could be more strict and validate that child entities are equal.
        // given
        var originalAnalysis = saveDummyAnalysisWithManyChildEntities();

        var exportAnalysesJson = importExportService.exportAnalyses(Collections.singletonList(originalAnalysis.getId()));

        // when
        var importedAnalyses = importExportService.importAnalyses(exportAnalysesJson);
        var importedAnalysis = analysisMapper.fromDto(IterableUtil.iterableToList(importedAnalyses).get(0));
        var importedAnalysisOptional = analysisRepository.findById(importedAnalysis.getId());
        assertThat(importedAnalysisOptional).isPresent();
        importedAnalysis = importedAnalysisOptional.get();

        // then
        assertThat(IterableUtil.iterableSize(importedAnalyses)).isEqualTo(1);
        var analyses = IterableUtil.iterableToList(analysisRepository.findAll());
        assertThat(analyses).hasSize(2);

        // Check analysis equality.
        assertThat(originalAnalysis.getName()).isEqualTo(importedAnalysis.getName());
        assertThat(originalAnalysis.getDescription()).isEqualTo(importedAnalysis.getDescription());
        assertThat(originalAnalysis.getIsTemplate()).isEqualTo(importedAnalysis.getIsTemplate());
        assertThat(originalAnalysis.getImageUrl()).isEqualTo(importedAnalysis.getImageUrl());

        // Check values equality.
        var originalValues = new ArrayList<>(originalAnalysis.getValues());
        var importedValues = new ArrayList<>(importedAnalysis.getValues());
        assertThat(originalValues).hasSize(importedValues.size());
        for (int i = 0; i < originalAnalysis.getValues().size(); i++) {
            var originalValue = originalValues.get(i);
            var importedValue = importedValues.get(i);

            assertThat(originalValue.getName()).isEqualTo(importedValue.getName());
            assertThat(originalValue.getDescription()).isEqualTo(importedValue.getDescription());
            assertThat(originalValue.getType()).isEqualTo(importedValue.getType());
            assertThat(originalValue.getArchived()).isEqualTo(importedValue.getArchived());
        }

        // Check stakeholders equality.
        var originalStakeholders = new ArrayList<>(originalAnalysis.getStakeholders());
        var importedStakeholders = new ArrayList<>(importedAnalysis.getStakeholders());
        assertThat(originalStakeholders).hasSize(importedStakeholders.size());
        for (int i = 0; i < originalAnalysis.getStakeholders().size(); i++) {
            var originalStakeholder = originalStakeholders.get(i);
            var importedStakeholder = importedStakeholders.get(i);

            assertThat(originalStakeholder.getName()).isEqualTo(importedStakeholder.getName());
            assertThat(originalStakeholder.getPriority()).isEqualTo(importedStakeholder.getPriority());
            assertThat(originalStakeholder.getLevel()).isEqualTo(importedStakeholder.getLevel());
        }

        // Check impacts equality.
        var originalImpacts = new ArrayList<>(originalAnalysis.getImpacts());
        var importedImpacts = new ArrayList<>(importedAnalysis.getImpacts());
        assertThat(originalImpacts).hasSize(importedImpacts.size());
        for (int i = 0; i < originalAnalysis.getImpacts().size(); i++) {
            var originalImpact = originalImpacts.get(i);
            var importedImpact = importedImpacts.get(i);

            assertThat(originalImpact.getMerit()).isEqualTo(importedImpact.getMerit());
            assertThat(originalImpact.getDescription()).isEqualTo(importedImpact.getDescription());
        }

        // Check variants equality.
        var originalVariants = new ArrayList<>(originalAnalysis.getVariants());
        var importedVariants = new ArrayList<>(importedAnalysis.getVariants());
        assertThat(originalVariants).hasSize(importedVariants.size());
        for (int i = 0; i < originalAnalysis.getVariants().size(); i++) {
            var originalVariant = originalVariants.get(i);
            var importedVariant = importedVariants.get(i);

            assertThat(originalVariant.getName()).isEqualTo(importedVariant.getName());
            assertThat(originalVariant.getDescription()).isEqualTo(importedVariant.getDescription());
            assertThat(originalVariant.getArchived()).isEqualTo(importedVariant.getArchived());
        }

        // Check requirements equality.
        var originalRequirements = new ArrayList<>(originalAnalysis.getRequirements());
        var importedRequirements = new ArrayList<>(importedAnalysis.getRequirements());
        assertThat(originalRequirements).hasSize(importedRequirements.size());
        for (int i = 0; i < originalAnalysis.getRequirements().size(); i++) {
            var originalRequirement = originalRequirements.get(i);
            var importedRequirement = importedRequirements.get(i);

            assertThat(originalRequirement.getDescription()).isEqualTo(importedRequirement.getDescription());

            // Check requirement deltas equality.
            // TODO The order of both RequirementDelta Sets is not deterministic and thus cannot be used to compare.
            //assertThat(originalRequirement.getRequirementDeltas()).hasSize(importedRequirement.getRequirementDeltas().size());
        }
    }

    @SneakyThrows
    @Test
    void testImportAnalyses_FieldIsMissing_ThrowsImportJsonException() {
        // given
        var analysis1 = saveDummyAnalysisWithFewChildEntities();
        var analysis2 = saveDummyAnalysisWithFewChildEntities();

        var exportAnalysesJson = importExportService.exportAnalyses(Arrays.asList(analysis1.getId(), analysis2.getId()));

        // when
        var exportAnalysesJsonObject = new JSONObject(exportAnalysesJson);
        exportAnalysesJsonObject.remove("importExportVersion");
        exportAnalysesJson = exportAnalysesJsonObject.toString();

        // then
        final String finalExportAnalysesJson = exportAnalysesJson;
        assertThatExceptionOfType(ImportJsonException.class).isThrownBy(() -> importExportService.importAnalyses(finalExportAnalysesJson));
    }

    @SneakyThrows
    @Test
    void testExportAnalyses() {
        // given
        var analysis1 = saveDummyAnalysisWithFewChildEntities();
        var analysis2 = saveDummyAnalysisWithFewChildEntities();

        // when
        var exportAnalysesJson = importExportService.exportAnalyses(Arrays.asList(analysis1.getId(), analysis2.getId()));
        var analysesJson = new JSONObject(exportAnalysesJson);

        // then

        // Check meta data and number of analyses.
        assertThat(analysesJson.get("importExportVersion")).isEqualTo(ImportExportService.NEWEST_IMPORT_EXPORT_VERSION);
        assertThat(analysesJson.getJSONArray("analyses").length()).isEqualTo(2);

        // Check content of one analysis (number of entities and number of attributes of each entity).
        var analysisJson = analysesJson.getJSONArray("analyses").getJSONObject(0);
        assertThat(analysisJson.getJSONObject("analysis").length()).isEqualTo(5);

        var requirementsJson = analysisJson.getJSONArray("requirements");
        assertThat(requirementsJson.length()).isEqualTo(1);
        assertThat(requirementsJson.getJSONObject(0).length()).isEqualTo(3);

        var valuesJson = analysisJson.getJSONArray("values");
        assertThat(valuesJson.length()).isEqualTo(1);
        assertThat(valuesJson.getJSONObject(0).length()).isEqualTo(5);

        var stakeholdersJson = analysisJson.getJSONArray("stakeholders");
        assertThat(stakeholdersJson.length()).isEqualTo(1);
        assertThat(stakeholdersJson.getJSONObject(0).length()).isEqualTo(5);

        var impactsJson = analysisJson.getJSONArray("impacts");
        assertThat(impactsJson.length()).isEqualTo(1);
        assertThat(impactsJson.getJSONObject(0).length()).isEqualTo(5);

        var variantsJson = analysisJson.getJSONArray("variants");
        assertThat(variantsJson.length()).isEqualTo(1);
        assertThat(variantsJson.getJSONObject(0).length()).isEqualTo(4);

        var requirementDeltasJson = analysisJson.getJSONArray("requirementDeltas");
        assertThat(requirementDeltasJson.length()).isEqualTo(1);
        assertThat(requirementDeltasJson.getJSONObject(0).length()).isEqualTo(4);
    }

    // TODO perform this test for each file in the directory with version files.
    @SneakyThrows
    @Test
    void testImportMigration_FromPreviousVersion_0_0_1() {
        // given
        var importPath = System.getProperty("user.dir") + "/src/test/java/com/evatool/application/service/import-version-files/version_0.0.1.json";
        var importFile = new File(importPath);
        var inputStream = new FileInputStream(importFile);
        var importJson = new String(inputStream.readAllBytes());

        // when

        // then
        importExportService.importAnalyses(importJson);
        // TODO actually assert import analysis instead of only checking if the import does not cause any exceptions.
    }

    private Analysis saveDummyAnalysisWithFewChildEntities() {
        var analysis = new Analysis("", "", false);
        analysisRepository.save(analysis);

        // Value types.
        var valueType1 = new ValueType("", "", analysis);
        valueTypeRepository.save(valueType1);

        // Values.
        var value1 = new Value("", "", false, valueType1, analysis);
        valueRepository.save(value1);

        // Stakeholders.
        var stakeholder1 = new Stakeholder("", "", StakeholderPriority.ONE, StakeholderLevel.SOCIETY, analysis);
        stakeholderRepository.save(stakeholder1);

        // Impacts.
        var impact1 = new Impact(0.2f, "", value1, stakeholder1, analysis);
        impactRepository.save(impact1);

        // Variant types.
        var variantType1 = new VariantType("", "", analysis);
        variantTypeRepository.save(variantType1);

        // Variants.
        var variant1 = new Variant("", "", false, variantType1, analysis);
        variantRepository.save(variant1);

        // Requirements.
        var requirement1 = new Requirement("", analysis);
        requirement1.getVariants().add(variant1);
        requirementRepository.save(requirement1);

        // Requirement Deltas.
        var requirementDelta1 = new RequirementDelta(impact1, requirement1);
        requirementDeltaRepository.save(requirementDelta1);

        return analysis;
    }

    private Analysis saveDummyAnalysisWithManyChildEntities() {
        var analysis = new Analysis("", "", false);
        analysisRepository.save(analysis);

        // Value types.
        var valueType1 = new ValueType("", "", analysis);
        valueTypeRepository.save(valueType1);
        var valueType2 = new ValueType("", "", analysis);
        valueTypeRepository.save(valueType2);

        // Values.
        var value1 = new Value("", "", false, valueType1, analysis);
        valueRepository.save(value1);
        var value2 = new Value("", "", false, valueType2, analysis);
        valueRepository.save(value2);
        var value3 = new Value("", "", false, valueType1, analysis);
        valueRepository.save(value3);

        // Stakeholders.
        var stakeholder1 = new Stakeholder("", "", StakeholderPriority.ONE, StakeholderLevel.SOCIETY, analysis);
        stakeholderRepository.save(stakeholder1);
        var stakeholder2 = new Stakeholder("", "", StakeholderPriority.ONE, StakeholderLevel.SOCIETY, analysis);
        stakeholderRepository.save(stakeholder2);
        var stakeholder3 = new Stakeholder("", "", StakeholderPriority.ONE, StakeholderLevel.SOCIETY, analysis);
        stakeholderRepository.save(stakeholder3);

        // Impacts.
        var impact1 = new Impact(0.2f, "", value1, stakeholder1, analysis);
        impactRepository.save(impact1);
        var impact2 = new Impact(0.2f, "", value2, stakeholder3, analysis);
        impactRepository.save(impact2);
        var impact3 = new Impact(0.2f, "", value3, stakeholder2, analysis);
        impactRepository.save(impact3);
        var impact4 = new Impact(0.2f, "", value1, stakeholder2, analysis);
        impactRepository.save(impact4);

        // Variant types.
        var variantType1 = new VariantType("", "", analysis);
        variantTypeRepository.save(variantType1);
        var variantType2 = new VariantType("", "", analysis);
        variantTypeRepository.save(variantType2);

        // Variants.
        var variant1 = new Variant("", "", false, variantType1, analysis);
        variantRepository.save(variant1);
        var variant2 = new Variant("", "", false, variantType1, analysis);
        variantRepository.save(variant2);
        var variant3 = new Variant("", "", false, variantType2, analysis);
        variantRepository.save(variant3);

        // Requirements.
        var requirement1 = new Requirement("", analysis);
        requirement1.getVariants().add(variant1);
        requirementRepository.save(requirement1);
        var requirement2 = new Requirement("", analysis);
        requirement2.getVariants().add(variant1);
        requirement2.getVariants().add(variant2);
        requirement2.getVariants().add(variant3);
        requirementRepository.save(requirement2);
        var requirement3 = new Requirement("", analysis);
        requirementRepository.save(requirement3);

        // Requirement Deltas.
        var requirementDelta1 = new RequirementDelta(impact1, requirement2);
        requirementDeltaRepository.save(requirementDelta1);
        var requirementDelta2 = new RequirementDelta(impact2, requirement2);
        requirementDeltaRepository.save(requirementDelta2);
        var requirementDelta3 = new RequirementDelta(impact1, requirement3);
        requirementDeltaRepository.save(requirementDelta3);
        var requirementDelta4 = new RequirementDelta(impact3, requirement1);
        requirementDeltaRepository.save(requirementDelta4);
        var requirementDelta5 = new RequirementDelta(impact4, requirement2);
        requirementDeltaRepository.save(requirementDelta5);

        return analysis;
    }
}
