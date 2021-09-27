package com.evatool.application.service.impl;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.mapper.*;
import com.evatool.application.service.api.AnalysisService;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.entity.ValueType;
import com.evatool.domain.entity.VariantType;
import com.evatool.domain.repository.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Service
public class AnalysisServiceImpl extends CrudServiceImpl<Analysis, AnalysisDto> implements AnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    @Getter
    private final AnalysisRepository repository;

    @Getter
    private final AnalysisMapper mapper;

    private final ValueTypeRepository valueTypeRepository;
    private final ValueTypeMapper valueTypeMapper;

    private final ValueRepository valueRepository;
    private final ValueMapper valueMapper;

    private final StakeholderRepository stakeholderRepository;
    private final StakeholderMapper stakeholderMapper;

    private final VariantTypeRepository variantTypeRepository;
    private final VariantTypeMapper variantTypeMapper;

    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;

    public AnalysisServiceImpl(AnalysisRepository repository,
                               ValueTypeRepository valueTypeRepository,
                               ValueRepository valueRepository,
                               AnalysisMapper mapper,
                               ValueTypeMapper valueTypeMapper,
                               ValueMapper valueMapper,
                               StakeholderRepository stakeholderRepository,
                               StakeholderMapper stakeholderMapper,
                               VariantTypeRepository variantTypeRepository,
                               VariantTypeMapper variantTypeMapper,
                               VariantRepository variantRepository,
                               VariantMapper variantMapper) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
        this.valueTypeRepository = valueTypeRepository;
        this.valueTypeMapper = valueTypeMapper;
        this.variantTypeRepository = variantTypeRepository;
        this.variantTypeMapper = variantTypeMapper;
        this.variantRepository = variantRepository;
        this.variantMapper = variantMapper;
        this.valueRepository = valueRepository;
        this.valueMapper = valueMapper;
        this.stakeholderRepository = stakeholderRepository;
        this.stakeholderMapper = stakeholderMapper;
    }

    @Override
    @Transactional
    public AnalysisDto deepCopy(UUID templateAnalysisId, AnalysisDto analysisDto) {
        logger.trace("Deep Copy");
        var deepCopyAnalysis = create(analysisDto);
        var templateAnalysis = findById(templateAnalysisId);

        // Copy value types.
        var templateValueTypes = valueTypeRepository.findAllByAnalysisId(templateAnalysis.getId());
        var copiedValueTypes = new HashMap<String, ValueType>();
        templateValueTypes.forEach(valueType -> {
            var valueTypeDto = valueTypeMapper.toDto(valueType);
            valueTypeDto.setAnalysisId(deepCopyAnalysis.getId());
            var copiedValueType = valueTypeRepository.save(valueTypeMapper.fromDto(valueTypeDto));
            copiedValueTypes.put(valueType.getId().toString(), copiedValueType);
        });

        // Copy values.
        var templateValues = valueRepository.findAllByAnalysisId(templateAnalysis.getId());
        templateValues.forEach(value -> {
            var valueDto = valueMapper.toDto(value);
            valueDto.setAnalysisId(deepCopyAnalysis.getId());
            valueDto.setValueTypeId(copiedValueTypes.get(value.getType().getId().toString()).getId());
            valueRepository.save(valueMapper.fromDto(valueDto));
        });

        // Copy stakeholders.
        var templateStakeholders = stakeholderRepository.findAllByAnalysisId(templateAnalysis.getId());
        templateStakeholders.forEach(stakeholder -> {
            var stakeholderDto = stakeholderMapper.toDto(stakeholder);
            stakeholderDto.setAnalysisId(deepCopyAnalysis.getId());
            stakeholderRepository.save(stakeholderMapper.fromDto(stakeholderDto));
        });

        // Copy variant types.
        var templateVariantTypes = variantTypeRepository.findAllByAnalysisId(templateAnalysis.getId());
        var copiedVariantTypes = new HashMap<String, VariantType>();
        templateVariantTypes.forEach(variantType -> {
            var variantTypeDto = variantTypeMapper.toDto(variantType);
            variantTypeDto.setAnalysisId(deepCopyAnalysis.getId());
            var copiedVariantType = variantTypeRepository.save(variantTypeMapper.fromDto(variantTypeDto));
            copiedVariantTypes.put(variantType.getId().toString(), copiedVariantType);
        });

        // Copy variants.
        var templateVariants = variantRepository.findAllByAnalysisId(templateAnalysis.getId());
        templateVariants.forEach(variant -> {
            var variantDto = variantMapper.toDto(variant);
            variantDto.setAnalysisId(deepCopyAnalysis.getId());
            variantDto.setVariantTypeId(copiedVariantTypes.get(variant.getType().getId().toString()).getId());
            variantRepository.save(variantMapper.fromDto(variantDto));
        });

        return deepCopyAnalysis;
    }
}
