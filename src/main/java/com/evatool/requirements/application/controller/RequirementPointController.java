package com.evatool.requirements.application.controller;

import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.domain.entity.RequirementsImpact;
import com.evatool.requirements.domain.entity.Requirement;
import com.evatool.requirements.domain.entity.RequirementPoint;
import com.evatool.requirements.common.exceptions.EntityNotFoundException;
import com.evatool.requirements.domain.repository.RequirementsImpactsRepository;
import com.evatool.requirements.domain.repository.RequirementRepository;
import com.evatool.requirements.domain.repository.RequirementPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	public void newRequirementPoint(Collection<RequirementPoint> requirementPointList) {
		logger.debug("newRequirementPoint [{}]",requirementPointList);
		requirementPointRepository.saveAll(requirementPointList);
	}

	public void updateRequirementPoint(Collection<RequirementPoint> requirementPoint) {
		logger.debug("updateRequirementPoint [{}]",requirementPoint);
		requirementPointRepository.saveAll(requirementPoint);
	}

	public void deleteRequirementPoint(Collection<RequirementPoint> requirementPoint) {
		logger.debug("updateRequirementPoint [{}]",requirementPoint);
		requirementPointRepository.deleteAll(requirementPoint);
	}

	public void deleteRequirementPoint(RequirementPoint requirementPoint) {
		logger.debug("deleteRequirementPoint [{}]",requirementPoint);
		requirementPointRepository.delete(requirementPoint);
	}

	public Requirement createPoints(Requirement requirement, RequirementDTO requirementDTO) {
		Collection<RequirementPoint> requirementPointCollection = new ArrayList<>();
		for( Map.Entry<UUID, Float> entry:requirementDTO.getRequirementImpactPoints().entrySet()) {
			RequirementPoint requirementPoint = new RequirementPoint();
			requirementPoint.setPoints(entry.getValue());
			Optional<RequirementsImpact> requirementsImpact = requirementsImpactsRepository.findById(entry.getKey());
			if(requirementsImpact.isEmpty()){
				throw new EntityNotFoundException(RequirementsImpact.class,entry.getKey());
			}
			requirementPoint.setRequirementsImpact(requirementsImpact.get());
			requirementPointCollection.add(requirementPoint);
		}
		this.newRequirementPoint(requirementPointCollection);
		requirement.getRequirementPointCollection().addAll(requirementPointCollection);
		return requirement;
	}

	public Requirement updatePoints(Requirement requirement, RequirementDTO requirementDTO) {
		logger.debug("updatePoints [{}] [{}]",requirement,requirementDTO);
		Collection<RequirementPoint> requirementPointCollectionFromEntity = requirement.getRequirementPointCollection();
		Collection<RequirementPoint> updateList = new ArrayList<>();
		Collection<RequirementPoint> deleteList = new ArrayList<>();
		Map<UUID, Float> requirementImpactPointsMap=requirementDTO.getRequirementImpactPoints();
		for (RequirementPoint requirementPoint:requirementPointCollectionFromEntity){
			if(requirementImpactPointsMap.get(requirementPoint.getId())==null) {
				deleteList.add(requirementPoint);
			}
			else if(requirementImpactPointsMap.get(requirementPoint.getId())!=requirementPoint.getPoints()){
				requirementPoint.setPoints(requirementImpactPointsMap.get(requirementPoint.getId()));
				updateList.add(requirementPoint);
				requirementDTO.getRequirementImpactPoints().remove(requirementPoint.getId());
			}
		}
		requirement.getRequirementPointCollection().removeAll(deleteList);
		requirement = this.requirementRepository.save(requirement);
		this.updateRequirementPoint(updateList);
		this.deleteRequirementPoint(updateList);
		this.createPoints(requirement,requirementDTO);
		return requirement;
	}

	public void deletePointsForRequirement(Requirement requirement) {
		logger.debug("deletePointsForRequirement [{}]",requirement);
		Collection<RequirementPoint> collection = requirement.getRequirementPointCollection();
		requirementPointRepository.deleteAll(collection);
		requirement.getRequirementPointCollection().clear();
	}
}
