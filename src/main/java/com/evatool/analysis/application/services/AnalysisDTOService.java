package com.evatool.analysis.application.services;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.AnalysisMapper;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.events.AnalysisEventPublisher;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.global.event.analysis.AnalysisUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalysisDTOService {

    final Logger logger = LoggerFactory.getLogger(AnalysisDTOService.class);

    @Autowired
    private AnalysisMapper analysisMapper;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private AnalysisEventPublisher eventPublisher;


    public List<AnalysisDTO> findAll(List<Analysis> analysisDTOList) {
        logger.info("findAll");
        return analysisMapper.map(analysisDTOList);
    }

    public AnalysisDTO findById(Analysis analysis) {
        logger.debug("findId [{}]",analysis);
        return analysisMapper.map(analysis);
    }

    public Analysis create(AnalysisDTO analysisDTO) {
        logger.debug("create [{}]",analysisDTO);
        Analysis analysis = new Analysis();
        analysis.setAnalysisName(analysisDTO.getAnalysisName());
        analysis.setDescription(analysisDTO.getAnalysisDescription());
        analysis.setImage(analysisDTO.getImage());
        analysis.setLastUpdate(analysisDTO.getDate());
        return analysis;
    }

    public void update(AnalysisDTO analysisDTO){
        logger.debug("update [{}]", analysisDTO);
        Optional<Analysis> analysisOptional = analysisRepository.findById(analysisDTO.getRootEntityID());
        if(analysisOptional.isEmpty())
            throw new EntityNotFoundException(Analysis.class, analysisDTO.getRootEntityID());

        Analysis analysis = analysisOptional.get();
        analysis.setAnalysisName(analysisDTO.getAnalysisName());
        analysis.setDescription(analysisDTO.getAnalysisDescription());
        analysis.setImage(analysisDTO.getImage());
        analysis.setLastUpdate(analysisDTO.getDate());

        analysis = analysisRepository.save(analysis);
        eventPublisher.publishEvent(new AnalysisUpdatedEvent(analysis.toJson()));

    }
}
