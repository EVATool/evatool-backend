package com.evatool.application.service.impl;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.mapper.AnalysisMapper;
import com.evatool.application.mapper.ValueMapper;
import com.evatool.application.service.api.AnalysisService;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.AnalysisRepository;
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

    public AnalysisServiceImpl(AnalysisRepository repository, ValueRepository valueRepository, AnalysisMapper mapper, ValueMapper valueMapper) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.valueRepository = valueRepository;
        this.mapper = mapper;
        this.valueMapper = valueMapper;
    }

    @Override
    @Transactional
    public AnalysisDto deepCopy(UUID templateAnalysisId, AnalysisDto analysisDto) {
        logger.trace("Deep Copy");
        var deepCopyAnalysis = create(analysisDto);
        var templateAnalysis = findById(templateAnalysisId);

        var templateValues = valueRepository.findAllByAnalysisId(templateAnalysis.getId());
        templateValues.forEach(value -> {
            var valueDto = valueMapper.toDto(value);
            valueDto.setAnalysisId(deepCopyAnalysis.getId());
            valueRepository.save(valueMapper.fromDto(valueDto));
        });

        return deepCopyAnalysis;
    }
}
