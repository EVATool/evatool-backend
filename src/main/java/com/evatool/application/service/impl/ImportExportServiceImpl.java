package com.evatool.application.service.impl;

import com.evatool.application.dto.ImportExportAnalysesDto;
import com.evatool.application.dto.ImportExportAnalysisDto;
import com.evatool.application.dto.ImportExportInclude;
import com.evatool.application.dto.SuperDto;
import com.evatool.application.mapper.*;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.ImportExportService;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.common.enums.ValueType;
import com.evatool.common.exception.ImportJsonException;
import com.evatool.common.exception.PropertyIsInvalidException;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.entity.Impact;
import com.evatool.domain.entity.Stakeholder;
import com.evatool.domain.entity.Value;
import com.evatool.domain.repository.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

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
    // TODO how do deal with database migration changes? (Done in Json Mappers?)
    @Override
    @Transactional
    public void importAnalyses(String importAnalyses) {
        try {
            var importJsonObject = new JSONObject(importAnalyses);
            var currentImportExportVersion = importJsonObject.getString("importExportVersion");
            var analysesJsonArray = importJsonObject.getJSONArray("analyses");
            var importAnalysisFunction = resolveImportAnalysisFunction(currentImportExportVersion);

            for (int i = 0; i < analysesJsonArray.length(); i++) {
                var analysisJsonObject = analysesJsonArray.getJSONObject(i);
                importAnalysisFunction.accept(analysisJsonObject);
            }
        } catch (JSONException jsonException) {
            throw new ImportJsonException(jsonException);
        }
    }

    @SneakyThrows(value = {JSONException.class, ImportJsonException.class})
    private void importAnalysis(JSONObject analysisJsonObject) {
        // Analysis.
        var analysisJson = analysisJsonObject.getJSONObject("analysis");
        var analysisName = analysisJson.getString("name");
        var analysisDescription = analysisJson.getString("description");
        var analysisIsTemplate = analysisJson.getBoolean("isTemplate");
        var analysisImageUrl = analysisJson.getString("imageUrl");
        var analysis = new Analysis(analysisName, analysisDescription, analysisIsTemplate, analysisImageUrl);
        analysisRepository.save(analysis);

        // Values.
        var valuesJson = analysisJsonObject.getJSONArray("values");
        var valuesMap = new HashMap<String, Value>();
        for (int i = 0; i < valuesJson.length(); i++) {
            var valueJson = valuesJson.getJSONObject(i);

            var valueName = valueJson.getString("name");
            var valueDescription = valueJson.getString("description");
            var valueTypeString = valueJson.getString("type");
            ValueType valueType = null;
            try {
                valueType = ValueType.valueOf(valueTypeString);
            } catch (IllegalArgumentException exception) {
                throw new ImportJsonException("Unknown ValueType (" + valueTypeString + ")");
            }
            var valueArchived = valueJson.getBoolean("archived");

            var value = new Value(valueName, valueDescription, valueType, valueArchived, analysis);
            valueRepository.save(value);

            var valueId = valueJson.getString("id");
            valuesMap.put(valueId, value);
        }

        // Stakeholders
        var stakeholdersJson = analysisJsonObject.getJSONArray("stakeholders");
        var stakeholdersMap = new HashMap<String, Stakeholder>();
        for (int i = 0; i < stakeholdersJson.length(); i++) {
            var stakeholderJson = stakeholdersJson.getJSONObject(i);

            var stakeholderName = stakeholderJson.getString("name");
            var StakeholderPriorityString = stakeholderJson.getString("priority");
            StakeholderPriority stakeholderPriority = null;
            try {
                stakeholderPriority = StakeholderPriority.valueOf(StakeholderPriorityString);
            } catch (IllegalArgumentException exception) {
                throw new ImportJsonException("Unknown StakeholderPriority (" + StakeholderPriorityString + ")");
            }
            var StakeholderLevelString = stakeholderJson.getString("level");
            StakeholderLevel stakeholderLevel = null;
            try {
                stakeholderLevel = StakeholderLevel.valueOf(StakeholderLevelString);
            } catch (IllegalArgumentException exception) {
                throw new ImportJsonException("Unknown StakeholderLevel (" + StakeholderLevelString + ")");
            }

            var stakeholder = new Stakeholder(stakeholderName, stakeholderPriority, stakeholderLevel, analysis);
            stakeholderRepository.save(stakeholder);

            var stakeholderId = stakeholderJson.getString("id");
            stakeholdersMap.put(stakeholderId, stakeholder);
        }

        // Impacts.
        var impactsJson = analysisJsonObject.getJSONArray("impacts");
        var impactsMap = new HashMap<String, Impact>();
        for (int i = 0; i < impactsJson.length(); i++) {
            var impactJson = impactsJson.getJSONObject(i);

            var impactMerit = (float) impactJson.getDouble("merit");
            var impactDescription = impactJson.getString("description");
            var valueId = impactJson.getString("valueId");
            var value = valuesMap.get(valueId);
            if (value == null) {
                throw new ImportJsonException("The Value with id " + valueId + " is referenced by this impact but not present.");
            }
            var stakeholderId = impactJson.getString("stakeholderId");
            var stakeholder = stakeholdersMap.get(stakeholderId);
            if (stakeholder == null) {
                throw new ImportJsonException("The Stakeholder with id " + stakeholderId + " is referenced by this impact but not present.");
            }
            var impact = new Impact(impactMerit, impactDescription, value, stakeholder, analysis);
            impactRepository.save(impact);

            var impactId = impactJson.getString("id");
            impactsMap.put(impactId, impact);
        }


        // Variants


        // Requirements


        // Requirement Deltas.


    }

    private Consumer<JSONObject> resolveImportAnalysisFunction(String currentImportExportVersion) {
        if (newestImportExportVersion.equals(currentImportExportVersion)) {
            return this::importAnalysis;
        } else {  // Migration.
            switch (currentImportExportVersion) {

                case "0.0.1": // Not yet reachable.
                    return this::importAnalysis; //importAnalysis_0_0_1;

                default:
                    throw new PropertyIsInvalidException("Unknown \"importExportVersion\" (" + currentImportExportVersion + ")");
            }
        }
    }

    @SneakyThrows
    @Override
    public String exportAnalyses(Iterable<UUID> analysisIds) {

        // Create meta data.
        var importExportVersion = newestImportExportVersion;

        // Create entity json.
        var analyses = analysisRepository.findAllById(analysisIds);
        analyses = TenancySentinel.handleFind(analyses);
        var exportAnalysisDtoList = new ArrayList<ImportExportAnalysisDto>();
        for (var analysis : analyses) {
            var exportAnalysisDto = exportAnalysis(analysis);
            exportAnalysisDtoList.add(exportAnalysisDto);
        }

        // Only export fields which are explicitly included.
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
                .serializeNulls()
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

        return new ImportExportAnalysisDto(
                analysisDto,
                valuesDtoList,
                stakeholdersDtoList,
                impactsDtoList,
                requirementsDtoList,
                requirementDeltasDtoList,
                variantsDtoList);
    }
}
