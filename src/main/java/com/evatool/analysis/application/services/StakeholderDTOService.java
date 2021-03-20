package com.evatool.analysis.application.services;


import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.domain.model.Stakeholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StakeholderDTOService {
    Logger logger = LoggerFactory.getLogger(StakeholderDTOService.class);

    @Autowired
    private StakeholderMapper stakeholderMapper;

    public List<StakeholderDTO> findAll(List<Stakeholder> stakeholderDTOList) {
        logger.info("findAll");
        return stakeholderMapper.map(stakeholderDTOList);
    }

    public StakeholderDTO findById(Stakeholder stakeholder) {
        logger.debug("findId [{}]",stakeholder);
        return stakeholderMapper.map(stakeholder);
    }

    public Stakeholder create(StakeholderDTO stakeholderDTO) {
        logger.debug("create [{}]",stakeholderDTO);
        Stakeholder stakeholder = new Stakeholder();
        stakeholder.setStakeholderName(stakeholderDTO.getStakeholderName());
        stakeholder.setPriority(stakeholderDTO.getPriority());
        stakeholder.setStakeholderLevel(stakeholderDTO.getStakeholderLevel());
        return stakeholder;
    }

}
