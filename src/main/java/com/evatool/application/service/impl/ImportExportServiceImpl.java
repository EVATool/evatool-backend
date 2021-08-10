package com.evatool.application.service.impl;

import com.evatool.application.mapper.*;
import com.evatool.application.service.api.ImportExportService;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ImportExportServiceImpl implements ImportExportService {

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

    @Autowired
    private AnalysisMapper analysisMapper;

    @Autowired
    private ImpactMapper impactMapper;

    @Autowired
    private RequirementMapper requirementMapper;

    @Autowired
    private RequirementDeltaMapper requirementDeltaMapper;

    @Autowired
    private StakeholderMapper stakeholderMapper;

    @Autowired
    private ValueMapper valueMapper;

    @Autowired
    private VariantMapper variantMapper;

    // TODO return ImportReport object with information (success or not, file version and current version..., error causes..., default values used...)
    @SneakyThrows // TODO remove...
    @Override
    @Transactional
    public void importAnalyses(String importAnalyses) {

    }

    private void importAnalysis(String importExportAnalysisJson) {

    }

    @SneakyThrows // TODO remove...
    @Override
    public String exportAnalyses() { // TODO get only relevant analyses...

        // Create meta data.
        var importExportVersion = "0.0.1";

        // Create entity json.
        var analyses = analysisRepository.findAll();
        var analysisJsonList = new ArrayList<ImportExportAnalysisJson>();
        for (var analysis : analyses) {
            var exportAnalysis = exportAnalysis(analysis);
            analysisJsonList.add(exportAnalysis);
        }

        var exportAnalyses = new ImportExportAnalysesJson(
                importExportVersion,
                analysisJsonList.toArray(new ImportExportAnalysisJson[0]));
        return new ObjectMapper().writeValueAsString(exportAnalyses);
    }

    private ImportExportAnalysisJson exportAnalysis(Analysis analysis) {
        // Retrieve entities.
        var analysisId = analysis.getId();
        var values = valueRepository.findAllByAnalysisId(analysisId);
        var stakeholders = stakeholderRepository.findAllByAnalysisId(analysisId);
        var impacts = impactRepository.findAllByAnalysisId(analysisId);
        var requirements = requirementRepository.findAllByAnalysisId(analysisId);
        var requirementDeltas = requirementDeltaRepository.findAllByAnalysisId(analysisId);
        var variants = variantRepository.findAllByAnalysisId(analysisId);

        // Convert entities to json strings.
        var analysisJson = analysisMapper.toJson(analysis);
        var valuesJson = valueMapper.toJson(values);
        var stakeholdersJson = stakeholderMapper.toJson(stakeholders);
        var impactsJson = impactMapper.toJson(impacts);
        var requirementsJson = requirementMapper.toJson(requirements);
        var requirementDeltasJson = requirementDeltaMapper.toJson(requirementDeltas);
        var variantsJson = variantMapper.toJson(variants);

        var exportedAnalysis = new ImportExportAnalysisJson(
                analysisJson,
                valuesJson,
                stakeholdersJson,
                impactsJson,
                requirementsJson,
                requirementDeltasJson,
                variantsJson);
        return exportedAnalysis;
    }

    @EqualsAndHashCode
    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    private class ImportExportAnalysisJson {

        // TODO maybe actually use JSON object?
        private String analysis;
        private String values;
        private String stakeholders;
        private String impacts;
        private String requirements;
        private String requirementDeltas;
        private String variants;

        public ImportExportAnalysisJson(String analysisJson,
                                        String valuesJson,
                                        String stakeholdersJson,
                                        String impactsJson,
                                        String requirementsJson,
                                        String requirementDeltasJson,
                                        String variantsJson) {
            this.analysis = analysisJson;
            this.values = valuesJson;
            this.stakeholders = stakeholdersJson;
            this.impacts = impactsJson;
            this.requirements = requirementsJson;
            this.requirementDeltas = requirementDeltasJson;
            this.variants = variantsJson;
        }
    }

    @EqualsAndHashCode
    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    private class ImportExportAnalysesJson {

        private String importExportVersion;

        private ImportExportAnalysisJson[] analyses;

        public ImportExportAnalysesJson(String importExportVersion, ImportExportAnalysisJson[] analyses) {
            this.importExportVersion = importExportVersion;
            this.analyses = analyses;
        }
    }
}
