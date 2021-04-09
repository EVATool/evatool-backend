package com.evatool.analysis.application.services;


import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.dto.StakeholderMapper;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.common.error.execptions.IllegalDtoValueException;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import com.evatool.variants.domain.entities.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class StakeholderDTOService {

    final Logger logger = LoggerFactory.getLogger(StakeholderDTOService.class);

    @Autowired
    private StakeholderMapper stakeholderMapper;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Autowired
    private AnalysisRepository analysisRepository;

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

        if(stakeholderDTO.getAnalysisId() == null){
            throw new IllegalDtoValueException("Analysis id is null.");
        }

        Stakeholder stakeholder = new Stakeholder();
        stakeholder.setStakeholderName(stakeholderDTO.getStakeholderName());
        stakeholder.setPriority(stakeholderDTO.getPriority());
        stakeholder.setStakeholderLevel(stakeholderDTO.getStakeholderLevel());
        Analysis analysis = analysisRepository.findById(stakeholderDTO.getAnalysisId()).get();
        if(analysis == null){
            throw new EntityNotFoundException(Analysis.class,stakeholderDTO.getAnalysisId());
        }
        stakeholder.setAnalysis(analysis);

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


    public Stakeholder update(StakeholderDTO stakeholderDTO){

        Optional<Stakeholder> stakeholderOptional = stakeholderRepository.findById(stakeholderDTO.getRootEntityID());
        Stakeholder stakeholder = stakeholderOptional.orElseThrow();
        stakeholder.setStakeholderName(stakeholderDTO.getStakeholderName());
        stakeholder.setPriority(stakeholderDTO.getPriority());
        if(!stakeholderDTO.getStakeholderLevel().getStakeholderLevel().equals(stakeholder.getStakeholderLevel().getStakeholderLevel()))
        {
            stakeholder.setGuiId(generateGuiId(stakeholderDTO.getStakeholderLevel()));
        }
        stakeholder.setStakeholderLevel(stakeholderDTO.getStakeholderLevel());

        return stakeholder;
    }

    private String generateGuiId(StakeholderLevel stakeholderLevel) {
        List<Stakeholder> stakeholders = stakeholderRepository.findAll();

        final int[] ind = {0};
        final int[] org = {0};
        final int[] soc = {0};
        final int[] sta = {0};


        stakeholders.forEach(stakeholder -> {
            if(stakeholder.getGuiId().contains("IND") && Integer.parseInt(stakeholder.getGuiId().split("IND")[1]) >= ind[0]){
                ind[0] = Integer.parseInt(stakeholder.getGuiId().split("IND")[1]);
            }
            else if(stakeholder.getGuiId().contains("ORG") && Integer.parseInt(stakeholder.getGuiId().split("ORG")[1]) >= org[0]){
                org[0] = Integer.parseInt(stakeholder.getGuiId().split("ORG")[1]);
            }
            else if(stakeholder.getGuiId().contains("SOC") && Integer.parseInt(stakeholder.getGuiId().split("SOC")[1]) >= soc[0]){
                soc[0] = Integer.parseInt(stakeholder.getGuiId().split("SOC")[1]);
            }
        });

        switch (stakeholderLevel.getStakeholderLevel()) {
            case "natural person":
                return String.format("IND%d", ind[0] + 1);
            case "organization":
                return String.format("ORG%d", org[0] + 1);
            case "society":
                return String.format("SOC%d", soc[0] + 1);
            default:
                return String.format("STA%d", sta[0] + 1);
        }
    }
}
