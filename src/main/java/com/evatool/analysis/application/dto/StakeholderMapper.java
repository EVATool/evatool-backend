package com.evatool.analysis.application.dto;

import com.evatool.analysis.application.controller.StakeholderControllerImpl;
import com.evatool.analysis.domain.model.Stakeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StakeholderMapper {

    @Autowired
    StakeholderControllerImpl stakeholderController;

    public List<StakeholderDTO> map(List<Stakeholder> resultList) {
        List<StakeholderDTO> stakeholderDTOList = new ArrayList<>();
        for(Stakeholder stakeholder : resultList){
            stakeholderDTOList.add(map(stakeholder));
        }
        return stakeholderDTOList;
    }

    public StakeholderDTO map(Stakeholder stakeholder) {
        StakeholderDTO stakeholderDTO = new StakeholderDTO();
        stakeholderDTO.setPriority(stakeholder.getPriority());
        stakeholderDTO.setStakeholderName(stakeholder.getStakeholderName());
        stakeholderDTO.setStakeholderLevel(stakeholder.getStakeholderLevel());
        stakeholderDTO.setRootEntityID(stakeholder.getStakeholderId());
        stakeholderDTO.setGuiId(stakeholder.getGuiId());
        stakeholderDTO.setImpactList(stakeholder.getImpact());
        stakeholderDTO.setAnalysisId(stakeholder.getAnalysis().getAnalysisId());
        return stakeholderDTO;
    }
}
