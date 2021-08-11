package com.evatool.application.service.impl;

import com.evatool.application.dto.*;
import com.evatool.application.mapper.*;
import com.evatool.application.service.api.ImportExportService;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

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
    private AnalysisDtoMapper analysisMapper;

    @Autowired
    private ImpactDtoMapper impactMapper;

    @Autowired
    private RequirementDtoMapper requirementMapper;

    @Autowired
    private RequirementDeltaDtoMapper requirementDeltaMapper;

    @Autowired
    private StakeholderDtoMapper stakeholderMapper;

    @Autowired
    private ValueDtoMapper valueMapper;

    @Autowired
    private VariantDtoMapper variantMapper;

    // TODO return ImportReport object with information (success or not, file version and current version..., error causes..., default values used...)
    @SneakyThrows
    @Override
    @Transactional
    public void importAnalyses(String importAnalyses) {

    }

    private void importAnalysis(String importAnalysis) {

    }

    @SneakyThrows
    @Override
    public String exportAnalyses(Iterable<UUID> analysisIdList) { // TODO get only relevant analyses...

        // Create meta data.
        var importExportVersion = "0.0.1";

        // Create entity json.
        var analyses = analysisRepository.findAllById(analysisIdList);
        var exportAnalysisDtoList = new ArrayList<ImportExportAnalysisDto>();
        for (var analysis : analyses) {
            var exportAnalysisDto = exportAnalysis(analysis);
            exportAnalysisDtoList.add(exportAnalysisDto);
        }

        // Only include fields which are explicitly included.
        var strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                if (SuperDto.class.isAssignableFrom(field.getDeclaringClass())) {
                    return !isIncluded(field);
                }
                return false;
            }

            private boolean isIncluded(FieldAttributes field) {
                for (var annotation : field.getAnnotations()) {
                    if (annotation.annotationType().equals(ImportExportInclude.class)) {
                        return true;
                    }
                }
                return false;
            }
        };

        var exportAnalysesDto = new ImportExportAnalysesDto(
                importExportVersion,
                exportAnalysisDtoList.toArray(new ImportExportAnalysisDto[0]));

        var gson = new GsonBuilder()
                .addSerializationExclusionStrategy(strategy)
                .create();

        return gson.toJson(exportAnalysesDto);
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
}
