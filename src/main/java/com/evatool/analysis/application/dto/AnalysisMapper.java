package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.model.Analysis;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalysisMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(AnalysisMapper.class);


    public List<AnalysisDTO> map(List<Analysis> resultList) {
        List<AnalysisDTO> analysisDTOList = new ArrayList<>();
        for(Analysis analysis : resultList){
            analysisDTOList.add(map(analysis));
        }
        return analysisDTOList;
    }

    public AnalysisDTO map(Analysis analysis) {
        AnalysisDTO analysisDTO = new AnalysisDTO();
        analysisDTO.setAnalysisName(analysis.getAnalysisName());
        analysisDTO.setAnalysisDescription(analysis.getDescription());
        analysisDTO.setRootEntityID(analysis.getAnalysisId());
        analysisDTO.setImage(analysis.getImage());
        analysisDTO.setLastUpdate(analysis.getLastUpdate());
        analysisDTO.setTemplate(analysis.getIsTemplate());
        analysisDTO.setUniqueString(analysis.getUniqueString());
        return analysisDTO;
    }

    public Analysis map(AnalysisDTO analysisDTO) {
        logger.info("Map to entity");
        var analysis = modelMapper.map(analysisDTO, Analysis.class);
        logger.info(analysis.toString());
        return analysis;
    }
}
