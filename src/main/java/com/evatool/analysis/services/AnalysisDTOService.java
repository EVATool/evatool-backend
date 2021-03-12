package com.evatool.analysis.services;

import com.evatool.analysis.dto.AnalysisDTO;
import com.evatool.analysis.model.Analysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnalysisDTOService {
    Logger logger = LoggerFactory.getLogger(AnalysisDTOService.class);

    @Autowired
    private AnalysisMapper analysisMapper;

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
        return analysis;
    }
}
