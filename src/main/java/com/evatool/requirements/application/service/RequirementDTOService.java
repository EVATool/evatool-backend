package com.evatool.requirements.application.service;

import com.evatool.global.event.requirements.RequirementCreatedEvent;
import com.evatool.global.event.requirements.RequirementDeletedEvent;
import com.evatool.global.event.requirements.RequirementUpdatedEvent;
import com.evatool.requirements.application.controller.RequirementPointController;
import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.dto.VariantsDTO;
import com.evatool.requirements.domain.entity.Requirement;
import com.evatool.requirements.domain.entity.RequirementsAnalysis;
import com.evatool.requirements.domain.entity.RequirementsVariant;
import com.evatool.requirements.common.exceptions.EntityNotFoundException;
import com.evatool.requirements.domain.events.RequirementEventPublisher;
import com.evatool.requirements.domain.repository.RequirementAnalysisRepository;
import com.evatool.requirements.domain.repository.RequirementRepository;
import com.evatool.requirements.domain.repository.RequirementsVariantsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequirementDTOService {

    final Logger logger = LoggerFactory.getLogger(RequirementDTOService.class);

    @Autowired
    RequirementMapper requirementMapper;

    @Autowired
    RequirementsVariantsRepository requirementsVariantsRepository;

    @Autowired
    RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private RequirementPointController requirementPointController;

    @Autowired
    private RequirementEventPublisher eventPublisher;

    public List<RequirementDTO> findAll() {
        List<Requirement> resultList = requirementRepository.findAll();
        return requirementMapper.mapList(resultList);
    }
    public List<RequirementDTO> findAllForAnalysis(UUID analysisId) {
        Optional<RequirementsAnalysis> optionalRequirementsAnalysis = requirementAnalysisRepository.findById(analysisId);
        if(optionalRequirementsAnalysis.isEmpty()) throw new EntityNotFoundException(RequirementsAnalysis.class, analysisId);
        Collection<Requirement> resultList = requirementRepository.findByRequirementsAnalysis(optionalRequirementsAnalysis.get());
        return requirementMapper.mapList(resultList);
    }

    public RequirementDTO findById(UUID id) {
        logger.debug("findById [{}]",id);
        Optional<Requirement> requirement = requirementRepository.findById(id);
        if(requirement.isEmpty()) throw new EntityNotFoundException(Requirement.class, id);
        return requirementMapper.map(requirement.get());
    }

    public void deleteRequirement(UUID id) {
        logger.debug("delete [{}]",id);
        Optional<Requirement> requirementOptional = requirementRepository.findById(id);
        if(requirementOptional.isEmpty()) throw new EntityNotFoundException(Requirement.class, id);
        Requirement requirement = requirementOptional.get();
        requirementRepository.deleteById(id);
        requirementPointController.deletePointsForRequirement(requirement);
        eventPublisher.publishEvent(new RequirementDeletedEvent(requirement.toJson()));
    }

    public void update(RequirementDTO requirementDTO) {
        logger.debug("update [{}]",requirementDTO);
        Optional<Requirement> requirementOptional = requirementRepository.findById(requirementDTO.getRootEntityId());
        if(requirementOptional.isEmpty()) throw new EntityNotFoundException(Requirement.class, requirementDTO.getRootEntityId());
        Requirement requirement = requirementOptional.get();
        requirement.setDescription(requirementDTO.getRequirementDescription());
        requirement.setTitle(requirementDTO.getRequirementTitle());
        Collection<RequirementsVariant> requirementsVariantCollectionDTO = requirementsVariantsRepository.findAllById(requirementDTO.getVariantsTitle().keySet());
        //Remove the Variants which are removed
        Collection<RequirementsVariant> newCollection = requirement.getVariants().stream().filter(requirementsVariantCollectionDTO::contains).collect(Collectors.toList());
        //Add the new Variants
        requirementsVariantCollectionDTO.stream().forEach(e->{
            if(!newCollection.contains(e)){
                newCollection.add(e);
            }
        });
        requirement.setVariants(newCollection);
        requirementPointController.updatePoints(requirement,requirementDTO);
        requirement = requirementRepository.save(requirement);
        eventPublisher.publishEvent(new RequirementUpdatedEvent(requirement.toJson()));
    }

    public UUID create(RequirementDTO requirementDTO) {
        logger.debug("create [{}]",requirementDTO);
        Requirement requirement = new Requirement();
        requirement.setTitle(requirementDTO.getRequirementTitle());
        requirement.setDescription(requirementDTO.getRequirementDescription());
        Optional<RequirementsAnalysis> requirementsAnalysis = requirementAnalysisRepository.findById(requirementDTO.getProjectID());
        if(requirementsAnalysis.isPresent())
        {
            requirement.setRequirementsAnalysis(requirementsAnalysis.get());
        }
        Collection<RequirementsVariant> requirementsVariantCollection = new ArrayList<>();
        for( Map.Entry<UUID, EntityModel<VariantsDTO>> entry:requirementDTO.getVariantsTitle().entrySet()) {
            Optional<RequirementsVariant> requirementsVariant = requirementsVariantsRepository.findById(entry.getKey());
            if(requirementsVariant.isEmpty()) throw new EntityNotFoundException(RequirementsVariant.class,entry.getKey());
            requirementsVariantCollection.add(requirementsVariant.get());
        }
        requirement.setVariants(requirementsVariantCollection);
        requirementPointController.createPoints(requirement,requirementDTO);
        requirementRepository.save(requirement);
        eventPublisher.publishEvent(new RequirementCreatedEvent(requirement.toJson()));
        return requirement.getId();
    }
}
