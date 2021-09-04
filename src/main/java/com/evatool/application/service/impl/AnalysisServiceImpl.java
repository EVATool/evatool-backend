package com.evatool.application.service.impl;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.mapper.AnalysisMapper;
import com.evatool.application.mapper.StakeholderMapper;
import com.evatool.application.mapper.ValueMapper;
import com.evatool.application.service.api.AnalysisService;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.AnalysisRepository;
import com.evatool.domain.repository.StakeholderRepository;
import com.evatool.domain.repository.ValueRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AnalysisServiceImpl extends CrudServiceImpl<Analysis, AnalysisDto> implements AnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    @Getter
    private final AnalysisRepository repository;

    @Getter
    private final AnalysisMapper mapper;

    private final ValueRepository valueRepository;

    private final ValueMapper valueMapper;

    private final StakeholderRepository stakeholderRepository;

    private final StakeholderMapper stakeholderMapper;

    public AnalysisServiceImpl(AnalysisRepository repository, ValueRepository valueRepository, AnalysisMapper mapper, ValueMapper valueMapper, StakeholderRepository stakeholderRepository, StakeholderMapper stakeholderMapper) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
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

        // Copy values.
        var templateValues = valueRepository.findAllByAnalysisId(templateAnalysis.getId());
        templateValues.forEach(value -> {
            var valueDto = valueMapper.toDto(value);
            valueDto.setAnalysisId(deepCopyAnalysis.getId());
            valueRepository.save(valueMapper.fromDto(valueDto));
        });

        // Copy stakeholders.
        var templateStakeholders = stakeholderRepository.findAllByAnalysisId(templateAnalysis.getId());
        templateStakeholders.forEach(stakeholder -> {
            var stakeholderDto = stakeholderMapper.toDto(stakeholder);
            stakeholderDto.setAnalysisId(deepCopyAnalysis.getId());
            stakeholderRepository.save(stakeholderMapper.fromDto(stakeholderDto));
        });

        return deepCopyAnalysis;
    }
}
