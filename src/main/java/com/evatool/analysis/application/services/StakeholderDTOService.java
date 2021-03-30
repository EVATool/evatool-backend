package com.evatool.analysis.application.services;


import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.dto.StakeholderMapper;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.repository.StakeholderRepository;
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

    @Autowired
    private StakeholderRepository stakeholderRepository;

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

        if (stakeholderDTO.getGuiId() == null || stakeholderDTO.getGuiId().equals(""))
        {
            stakeholder.setGuiId(generateGuiId(stakeholder.getStakeholderLevel()));
        }
        else
        {
            stakeholder.setGuiId(stakeholderDTO.getGuiId());
        }

        return stakeholder;
    }

    private String generateGuiId(StakeholderLevel stakeholderLevel) {
        List<Stakeholder> stakeholders = stakeholderRepository.findAll();

       long stakeholderLevelSize =  stakeholders.stream().filter(stakeholder -> stakeholder.getStakeholderLevel().equals(stakeholderLevel)).count();

        switch (stakeholderLevel.getStakeholderLevel()){
            case "natural person":
                return String.format("IND%d", stakeholderLevelSize + 1);
            case "organization":
                return String.format("ORG%d", stakeholderLevelSize + 1);
            case "society":
                return String.format("SOC%d", stakeholderLevelSize + 1);
            default:
                return String.format("STA%d", stakeholderLevelSize + 1);
        }
    }

}
