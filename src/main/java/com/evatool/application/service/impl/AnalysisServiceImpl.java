package com.evatool.application.service.impl;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.mapper.AnalysisDtoMapper;
import com.evatool.application.mapper.ValueDtoMapper;
import com.evatool.application.service.api.AnalysisService;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.AnalysisRepository;
import com.evatool.domain.repository.ValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AnalysisServiceImpl extends CrudServiceImpl<Analysis, AnalysisDto> implements AnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final AnalysisRepository repository;

    private final ValueRepository valueRepository;

    private final AnalysisDtoMapper mapper;

    private final ValueDtoMapper valueMapper;

    public AnalysisServiceImpl(AnalysisRepository repository, ValueRepository valueRepository, AnalysisDtoMapper mapper, ValueDtoMapper valueMapper) {
        super(repository, mapper);
        this.repository = repository;
        this.valueRepository = valueRepository;
        this.mapper = mapper;
        this.valueMapper = valueMapper;
    }

    @Override
    @Transactional
    public AnalysisDto deepCopy(UUID templateAnalysisId, AnalysisDto analysisDto) {
        logger.debug("Deep Copy");
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
