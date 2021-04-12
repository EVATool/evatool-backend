package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.interfaces.StakeholderController;
import com.evatool.analysis.application.services.StakeholderDTOService;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.events.AnalysisEventPublisher;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class StakeholderControllerImpl implements StakeholderController {

    @Autowired
    private StakeholderDTOService stakeholderDTOService;


    final Logger logger = LoggerFactory.getLogger(StakeholderControllerImpl.class);

    @Override
    public List<EntityModel<StakeholderDTO>> getStakeholderList() {
        logger.info("[GET] /stakeholders");
        return generateLinks(stakeholderDTOService.findAll());
    }

    @Override
    public List<EntityModel<StakeholderDTO>> getStakeholderByAnalysis(UUID analysisId) {
        logger.info("[GET] /stakeholders?analysisId={id}");
        List<Stakeholder> stakeholderList = stakeholderDTOService.getStakeholdersAsList();

        stakeholderList.removeIf(stakeholder -> !stakeholder.getAnalysis().getAnalysisId().equals(analysisId));
        if (stakeholderList.isEmpty()){
            return Collections.emptyList();
        }
        return generateLinks(stakeholderDTOService.findAll());
    }

    @Override
    public List<StakeholderLevel> findAllLevels() {
        logger.info("Get Stakeholder Levels");
        return Arrays.asList(StakeholderLevel.values());
    }

    @Override
    public EntityModel<StakeholderDTO> getStakeholderById(UUID id) {
        logger.info("[POST] /stakeholders");
        return generateLinks(stakeholderDTOService.findById(id));
    }

    @Override
    public ResponseEntity<EntityModel<StakeholderDTO>> addStakeholder(@RequestBody StakeholderDTO stakeholderDTO) {
        logger.info("[POST] /stakeholders");
        return new ResponseEntity<>(getStakeholderById(stakeholderDTOService.create(stakeholderDTO)), HttpStatus.CREATED);
    }

    @Override
    public EntityModel<StakeholderDTO> updateStakeholder(@RequestBody StakeholderDTO stakeholderDTO) {
        logger.info("[PUT] /stakeholder");
        stakeholderDTOService.update(stakeholderDTO);
        return getStakeholderById(stakeholderDTO.getRootEntityID());
    }

    @Override
    public ResponseEntity<Void> deleteStakeholder(UUID id) {
        logger.info("[DELETE] /stakeholders/{id}");
        stakeholderDTOService.deleteStakeholder(id);
        return ResponseEntity.ok().build();
    }

    private EntityModel<StakeholderDTO> generateLinks(StakeholderDTO stakeholderDTO){
        EntityModel<StakeholderDTO> stakeholderDTOEntityModel = EntityModel.of(stakeholderDTO);
        stakeholderDTOEntityModel.add(linkTo(methodOn(StakeholderController.class).getStakeholderById(stakeholderDTO.getRootEntityID())).withSelfRel());
        return stakeholderDTOEntityModel;
    }

    private List<EntityModel<StakeholderDTO>> generateLinks(List<StakeholderDTO> stakeholderDTOList){
        List<EntityModel<StakeholderDTO>> returnList = new ArrayList<>();
        stakeholderDTOList.forEach(e -> returnList.add(generateLinks(e)));
        return returnList;
    }
}
