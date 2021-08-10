package com.evatool.application.service.impl;

import com.evatool.application.dto.*;
import com.evatool.application.mapper.*;
import com.evatool.application.service.api.ImportExportService;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    private void importAnalysis(String importAnalysis) {

    }

    @SneakyThrows // TODO remove...
    @Override
    public String exportAnalyses() { // TODO get only relevant analyses...

        // Create meta data.
        var importExportVersion = "0.0.1";

        // Create entity json.
        var analyses = analysisRepository.findAll();
        var exportAnalysisDtoList = new ArrayList<ImportExportAnalysisDto>();
        for (var analysis : analyses) {
            var exportAnalysisDto = exportAnalysis(analysis);
            exportAnalysisDtoList.add(exportAnalysisDto);
        }

        var exportAnalysesDto = new ImportExportAnalysesDto(
                importExportVersion,
                exportAnalysisDtoList.toArray(new ImportExportAnalysisDto[0]));

        var strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                for (var annotation : field.getAnnotations()) {
                    if (annotation.annotationType().equals(ImportExportInclude.class)) {
                        return false;
                    }
                }
                return true;
            }
        };

        var gson = new GsonBuilder()
                .addSerializationExclusionStrategy(strategy)
                .create();
        String exportAnalysesJson = gson.toJson(exportAnalysesDto);
        return exportAnalysesJson;
    }

    private ImportExportAnalysisDto exportAnalysis(Analysis analysis) {
        // Retrieve entities.
        var analysisId = analysis.getId();
        var values = valueRepository.findAllByAnalysisId(analysisId);
        var stakeholders = stakeholderRepository.findAllByAnalysisId(analysisId);
        var impacts = impactRepository.findAllByAnalysisId(analysisId);
        var requirements = requirementRepository.findAllByAnalysisId(analysisId);
        var requirementDeltas = requirementDeltaRepository.findAllByAnalysisId(analysisId);
        var variants = variantRepository.findAllByAnalysisId(analysisId);

        // Convert entities to json strings.
        var analysisDto = analysisMapper.toDto(analysis);
        var valuesDtoList = valueMapper.toDtoList(values);
        var stakeholdersDtoList = stakeholderMapper.toDtoList(stakeholders);
        var impactsDtoList = impactMapper.toDtoList(impacts);
        var requirementsDtoList = requirementMapper.toDtoList(requirements);
        var requirementDeltasDtoList = requirementDeltaMapper.toDtoList(requirementDeltas);
        var variantsDtoList = variantMapper.toDtoList(variants);

        var exportAnalysisDto = new ImportExportAnalysisDto(
                analysisDto,
                valuesDtoList,
                stakeholdersDtoList,
                impactsDtoList,
                requirementsDtoList,
                requirementDeltasDtoList,
                variantsDtoList);
        return exportAnalysisDto;
    }

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    private class ImportExportAnalysisDto { // TODO move to dto package...

        private AnalysisDto analysis;
        private List<ValueDto> values;
        private List<StakeholderDto> stakeholders;
        private List<ImpactDto> impacts;
        private List<RequirementDto> requirements;
        private List<RequirementDeltaDto> requirementDeltas;
        private List<VariantDto> variants;

        public ImportExportAnalysisDto(AnalysisDto analysisDtoList,
                                       List<ValueDto> valuesDtoList,
                                       List<StakeholderDto> stakeholdersDtoList,
                                       List<ImpactDto> impactsDtoList,
                                       List<RequirementDto> requirementsDtoList,
                                       List<RequirementDeltaDto> requirementDeltasDtoList,
                                       List<VariantDto> variantsDtoList) {
            this.analysis = analysisDtoList;
            this.values = valuesDtoList;
            this.stakeholders = stakeholdersDtoList;
            this.impacts = impactsDtoList;
            this.requirements = requirementsDtoList;
            this.requirementDeltas = requirementDeltasDtoList;
            this.variants = variantsDtoList;
        }
    }

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    private class ImportExportAnalysesDto {

        private String importExportVersion;

        private ImportExportAnalysisDto[] analyses;

        public ImportExportAnalysesDto(String importExportVersion, ImportExportAnalysisDto[] importExportAnalysisJson) {
            this.importExportVersion = importExportVersion;
            this.analyses = importExportAnalysisJson;
        }
    }
}
