package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactAnalysisDto;
import com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper;
import com.evatool.impact.common.exception.EntityIdRequiredException;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImpactAnalysisServiceImpl implements ImpactAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactAnalysisServiceImpl.class);

    private final ImpactAnalysisRepository analysisRepository;

    public ImpactAnalysisServiceImpl(ImpactAnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public ImpactAnalysisDto findById(UUID id) {
        logger.info("Get Analysis");
        if (id == null) {
            throw new EntityIdRequiredException(ImpactAnalysis.class.getSimpleName());
        }
        var analysis = analysisRepository.findById(id);
        if (analysis.isEmpty()) {
            throw new EntityNotFoundException(ImpactAnalysis.class, id);
        }
        return ImpactAnalysisDtoMapper.toDto(analysis.get());
    }

    @Override
    public List<ImpactAnalysisDto> findAll() {
        logger.info("Get Analyses");
        var analyses = analysisRepository.findAll();
        var analysisDtoList = new ArrayList<ImpactAnalysisDto>();
        analyses.forEach(analysis -> analysisDtoList.add(ImpactAnalysisDtoMapper.toDto(analysis)));
        return analysisDtoList;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete Analysis");
        analysisRepository.deleteAll();
    }
}
