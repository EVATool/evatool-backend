package com.evatool.requirements.application.controller;

import com.evatool.global.event.requirements.RequirementPointCreatedEvent;
import com.evatool.global.event.requirements.RequirementPointDeletedEvent;
import com.evatool.global.event.requirements.RequirementPointUpdatedEvent;
import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.dto.RequirementPointDTO;
import com.evatool.requirements.domain.entity.RequirementsImpact;
import com.evatool.requirements.domain.entity.Requirement;
import com.evatool.requirements.domain.entity.RequirementPoint;
import com.evatool.requirements.common.exceptions.EntityNotFoundException;
import com.evatool.requirements.domain.events.RequirementEventPublisher;
import com.evatool.requirements.domain.repository.RequirementsImpactsRepository;
import com.evatool.requirements.domain.repository.RequirementRepository;
import com.evatool.requirements.domain.repository.RequirementPointRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RequirementPointController {

	final Logger logger = LoggerFactory.getLogger(RequirementPointController.class);

	@Autowired
	private RequirementPointRepository requirementPointRepository;

	@Autowired
	private RequirementRepository requirementRepository;

	@Autowired
	private RequirementsImpactsRepository requirementsImpactsRepository;

	@Autowired
	private RequirementEventPublisher eventPublisher;

	public void newRequirementPoint(Collection<RequirementPoint> requirementPointList) {
		logger.debug("newRequirementPoint [{}]",requirementPointList);
		requirementPointRepository.saveAll(requirementPointList);
		eventPublisher.publishEvent(new RequirementPointCreatedEvent(new Gson().toJson(requirementPointList)));
	}

	public void updateRequirementPoint(Collection<RequirementPoint> requirementPointList) {
		logger.debug("updateRequirementPoint [{}]",requirementPointList);
		requirementPointRepository.saveAll(requirementPointList);
		eventPublisher.publishEvent(new RequirementPointUpdatedEvent(new Gson().toJson(requirementPointList)));
	}

	public void deleteRequirementPoint(Collection<RequirementPoint> requirementPointList) {
		logger.debug("updateRequirementPoint [{}]",requirementPointList);
		requirementPointRepository.deleteAll(requirementPointList);
		eventPublisher.publishEvent(new RequirementPointDeletedEvent(new Gson().toJson(requirementPointList)));
	}

	public void deleteRequirementPoint(RequirementPoint requirementPoint) {
		logger.debug("deleteRequirementPoint [{}]",requirementPoint);
		requirementPointRepository.delete(requirementPoint);
	}

	public Requirement createPoints(Requirement requirement, RequirementDTO requirementDTO) {
		Collection<RequirementPoint> requirementPointCollection = new ArrayList<>();
		for( EntityModel<RequirementPointDTO> requirementPointDTOEntityModel:requirementDTO.getRequirementImpactPoints()) {
			RequirementPoint requirementPoint = new RequirementPoint();
			requirementPoint.setPoints(requirementPointDTOEntityModel.getContent().getPoints());
			Optional<RequirementsImpact> requirementsImpact = requirementsImpactsRepository.findById(requirementPointDTOEntityModel.getContent().getEntityId());
			if(requirementsImpact.isEmpty()){
				throw new EntityNotFoundException(RequirementsImpact.class,requirementPointDTOEntityModel.getContent().getEntityId());
			}
			requirementPoint.setRequirementsImpact(requirementsImpact.get());
			requirementPointCollection.add(requirementPoint);
		}
		if(requirementPointCollection.size()>0) {
			this.newRequirementPoint(requirementPointCollection);
		}
		requirement.getRequirementPointCollection().addAll(requirementPointCollection);
		return requirement;
	}

	public Requirement updatePoints(Requirement requirement, RequirementDTO requirementDTO) {
		logger.debug("updatePoints [{}] [{}]",requirement,requirementDTO);
		Collection<RequirementPoint> requirementPointCollectionFromEntity = requirement.getRequirementPointCollection();
		Collection<RequirementPoint> updateList = new ArrayList<>();
		Collection<RequirementPoint> deleteList = new ArrayList<>();
		Collection<RequirementPoint> equalsList = new ArrayList<>();
		Set<EntityModel<RequirementPointDTO>> requirementImpactPointsFromDTO=requirementDTO.getRequirementImpactPoints();
		for (RequirementPoint requirementPoint:requirementPointCollectionFromEntity){
			boolean exist=false;
			for (EntityModel<RequirementPointDTO> requirementPointDTO:requirementImpactPointsFromDTO){
				if(requirementPointDTO.getContent().getEntityId().equals(requirementPoint.getRequirementsImpact().getId())) {
					exist=true;
					if(!requirementPointDTO.getContent().getPoints().equals(requirementPoint.getPoints())){
						requirementPoint.setPoints(requirementPointDTO.getContent().getPoints());
						updateList.add(requirementPoint);
						requirementDTO.getRequirementImpactPoints().remove(requirementPoint.getId());
					}else{
						equalsList.add(requirementPoint);
					}
					break;
				}
			}
			if(!exist){
				deleteList.add(requirementPoint);
			}
		}
		requirement.getRequirementPointCollection().removeAll(deleteList);
		requirement = this.requirementRepository.save(requirement);
		if(updateList.size()>0) {
			this.updateRequirementPoint(updateList);
			this.removePoints(requirementDTO,updateList);
		}
		if(deleteList.size()>0) {
			this.deleteRequirementPoint(deleteList);
			this.removePoints(requirementDTO,deleteList);
		}
		if(equalsList.size()>0) {
			this.removePoints(requirementDTO,equalsList);
		}
		this.createPoints(requirement,requirementDTO);
		return requirement;
	}

	public void deletePointsForRequirement(Requirement requirement) {
		logger.debug("deletePointsForRequirement [{}]",requirement);
		Collection<RequirementPoint> collection = requirement.getRequirementPointCollection();
		requirementPointRepository.deleteAll(collection);
		requirement.getRequirementPointCollection().clear();
	}

	private void removePoints(RequirementDTO requirementDTO, Collection<RequirementPoint> list){
		for (RequirementPoint requirementPoint:list){
			boolean remove=false;
			EntityModel<RequirementPointDTO> toRemove=null;
			for (EntityModel<RequirementPointDTO> requirementPointDTO:requirementDTO.getRequirementImpactPoints()){
				if(requirementPointDTO.getContent().getEntityId().equals(requirementPoint.getRequirementsImpact().getId())) {
					remove=true;
					toRemove=requirementPointDTO;
					break;
				}
			}
			if(remove && toRemove!=null){
				requirementDTO.getRequirementImpactPoints().remove(toRemove);
			}
		}
	}
}
