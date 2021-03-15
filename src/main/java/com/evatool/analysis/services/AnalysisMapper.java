package com.evatool.analysis.services;

import com.evatool.analysis.model.Analysis;
import org.springframework.stereotype.Service;
import com.evatool.analysis.dto.AnalysisDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalysisMapper {


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

        return analysisDTO;
    }

}
