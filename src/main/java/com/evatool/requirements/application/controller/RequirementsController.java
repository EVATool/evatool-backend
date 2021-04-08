package com.evatool.requirements.application.controller;


import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.service.RequirementDTOService;
import com.evatool.requirements.domain.repository.RequirementPointRepository;
import com.evatool.requirements.domain.repository.RequirementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Api("API-endpoint RequirementsController!")
public class RequirementsController {

	final Logger logger = LoggerFactory.getLogger(RequirementsController.class);

	@Autowired
	private RequirementDTOService dtoService;

	@Autowired
	private RequirementRepository requirementRepository;

	@Autowired
	private RequirementPointRepository requirementPointRepository;

	@GetMapping("/requirements")
	@ApiOperation(value = "This method returns a list of all Requirements.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All entities returned")})
	public List<EntityModel<RequirementDTO>> getRequirementList() {
		logger.info("[GET] /requirements");
		return generateLinks(dtoService.findAll());
	}

	@GetMapping(value = "/requirements",params = "analysisId")
	@ApiOperation(value = "This method returns a list of all Requirements for an Analysis Id.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All entities returned"),
			@ApiResponse(code = 400, message = "The id was invalid"),
			@ApiResponse(code = 404, message = "The entity was not found")})
	public List<EntityModel<RequirementDTO>> getRequirementListForAnalysis(@RequestParam("analysisId") UUID analysisId) {
		logger.info("[GET] /requirements?analysisId");
		return generateLinks(dtoService.findAllForAnalysis(analysisId));
	}

	@GetMapping("/requirements/{id}")
	@ApiOperation(value = "Read requirement by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The entity was found"),
			@ApiResponse(code = 400, message = "The id was invalid"),
			@ApiResponse(code = 404, message = "The entity was not found")})
	public EntityModel<RequirementDTO> getRequirementById(@PathVariable UUID id) {
		logger.info("[GET] /requirements/{id}");
		return generateLinks(dtoService.findById(id));
	}

	@PostMapping("/requirements")
	@ApiOperation(value = "Create requirement")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The entity was inserted"),
			@ApiResponse(code = 400, message = "The entity was invalid"),
			@ApiResponse(code = 404, message = "The entity was not found")})
	public ResponseEntity<EntityModel<RequirementDTO>> newRequirement(@RequestBody RequirementDTO requirementDTO) {
		logger.info("[POST] /requirements");
		return new ResponseEntity<>(getRequirementById(dtoService.create(requirementDTO)), HttpStatus.CREATED);
	}

	@PutMapping("/requirements")
	@ApiOperation(value = "Update requirement by ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The entity was deleted"),
			@ApiResponse(code = 404, message = "The entity was not found")})
	public EntityModel<RequirementDTO> updateRequirement(@RequestBody RequirementDTO requirementDTO) {
		logger.info("[PUT] /requirements");
		dtoService.update(requirementDTO);
		return getRequirementById(requirementDTO.getRootEntityId());
	}

	@DeleteMapping("/requirements/{id}")
	@ApiOperation(value = "Delete requirement by ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The entity was updated"),
			@ApiResponse(code = 400, message = "The entity was invalid"),
			@ApiResponse(code = 404, message = "The entity was not found")})
	public ResponseEntity<Void> deleteRequirement(@PathVariable UUID id) {
		logger.info("[DELETE] /requirements/{id}");
		dtoService.deleteRequirement(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/requirements/referenced-by-impact/{impactId}")
	public ResponseEntity<Boolean> referencedByImpact(@PathVariable UUID impactId) {
		var requirementPoints = requirementPointRepository.findAllByRequirementsImpactId(impactId);
		return new ResponseEntity<>(requirementPoints.isEmpty(), HttpStatus.OK);
	}

	private EntityModel<RequirementDTO> generateLinks(RequirementDTO requirementDTO) {
		EntityModel<RequirementDTO> requirementDTOEntityModel = EntityModel.of(requirementDTO);
		requirementDTOEntityModel.add(linkTo(methodOn(RequirementsController.class).getRequirementById(requirementDTO.getRootEntityId())).withSelfRel());
		return requirementDTOEntityModel;
	}

	private List<EntityModel<RequirementDTO>> generateLinks(List<RequirementDTO> requirementDTOList) {
		List<EntityModel<RequirementDTO>> returnList = new ArrayList<>();
		requirementDTOList.stream().forEach(e -> returnList.add(generateLinks(e)));
		return returnList;
	}
}
