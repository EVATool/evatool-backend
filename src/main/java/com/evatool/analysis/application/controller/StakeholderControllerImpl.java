package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.interfaces.StakeholderController;
import com.evatool.analysis.application.services.StakeholderService;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.Stakeholder;
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
    private StakeholderService stakeholderService;

    final Logger logger = LoggerFactory.getLogger(StakeholderControllerImpl.class);

    @Override
    public List<EntityModel<StakeholderDTO>> getStakeholderList() {
        logger.info("[GET] /stakeholders");
        return generateLinks(stakeholderService.findAll());
    }

    @Override
    public List<EntityModel<StakeholderDTO>> getStakeholderByAnalysis(UUID analysisId) {
        logger.info("[GET] /stakeholders?analysisId={id}");
        return generateLinks(stakeholderService.getStakeholdersByAnalysisId(analysisId));
    }

    @Override
    public List<StakeholderLevel> findAllLevels() {
        logger.info("Get Stakeholder Levels");
        return Arrays.asList(StakeholderLevel.values());
    }

    @Override
    public EntityModel<StakeholderDTO> getStakeholderById(UUID id) {
        logger.info("[POST] /stakeholders");
        return generateLinks(stakeholderService.findById(id));
    }

    @Override
    public ResponseEntity<EntityModel<StakeholderDTO>> addStakeholder(@RequestBody StakeholderDTO stakeholderDTO) {
        logger.info("[POST] /stakeholders");
        return new ResponseEntity<>( new EntityModel<>(stakeholderService.create(stakeholderDTO)), HttpStatus.CREATED);
    }

    @Override
    public EntityModel<StakeholderDTO> updateStakeholder(@RequestBody StakeholderDTO stakeholderDTO) {
        logger.info("[PUT] /stakeholder");
        stakeholderService.update(stakeholderDTO);
        return getStakeholderById(stakeholderDTO.getRootEntityID());
    }

    @Override
    public ResponseEntity<Void> deleteStakeholder(UUID id) {
        logger.info("[DELETE] /stakeholders/{id}");
        stakeholderService.deleteStakeholder(id);
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
