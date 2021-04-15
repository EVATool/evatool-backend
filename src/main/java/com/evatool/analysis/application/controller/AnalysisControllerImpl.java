package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.ValueDtoMapper;
import com.evatool.analysis.application.interfaces.AnalysisController;
import com.evatool.analysis.application.services.AnalysisService;
import com.evatool.analysis.application.services.ValueServiceImpl;
import com.evatool.analysis.domain.model.Value;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AnalysisControllerImpl implements AnalysisController {

  @Autowired
  private AnalysisRepository analysisRepository;

  @Autowired
  private AnalysisService analysisService;

  @Autowired
  private ValueServiceImpl valueService;

  final Logger logger = LoggerFactory.getLogger(AnalysisControllerImpl.class);

  @Override
  public List<EntityModel<AnalysisDTO>> getAnalysisList(
          @ApiParam(value = "Is A Template") @Valid @RequestParam(value = "isTemplate", required = false) Boolean isTemplate) {
    logger.info("[GET] /analyses");
    return generateLinks(analysisService.findAll());
  }

  @Override
  public EntityModel<AnalysisDTO> getAnalysisById(UUID id) {
    logger.info("[POST] /analyses");
    return generateLinks(analysisService.findById(id));
  }

  @Override
  public ResponseEntity<EntityModel<AnalysisDTO>> addAnalysis(@RequestBody AnalysisDTO analysisDTO) {
    logger.info("[POST] /analyses");
    return new ResponseEntity<>(getAnalysisById(analysisService.createAnalysisWithUUID(analysisDTO)), HttpStatus.CREATED);
  }

  @Override
  public EntityModel<AnalysisDTO> updateAnalysis(@RequestBody AnalysisDTO analysisDTO) {
    logger.info("[PUT] /analyses");
    analysisService.update(analysisDTO);
    return getAnalysisById(analysisDTO.getRootEntityID());
  }

  @Override
  public ResponseEntity<Void> deleteAnalysis(UUID id) {
    logger.info("[DELETE] /analyses/{id}");
    analysisService.deleteAnalysis(id);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<EntityModel<AnalysisDTO>> deepCopyAnalysis(UUID id, AnalysisDTO analysisDTO) {
    var newAnalysisDto = addAnalysis(analysisDTO);
    var newAnalysis = analysisRepository.findById(newAnalysisDto.getBody().getContent().getRootEntityID()).get();

    var templateAnalysisValues = valueService.findAllByAnalysisId(id);
    for (var value : templateAnalysisValues) {
      var copiedValue = new Value(value.getName(), value.getType(), value.getDescription());
      copiedValue.setArchived(value.getArchived());
      copiedValue.setAnalysis(newAnalysis);
      valueService.create(ValueDtoMapper.toDto(copiedValue));
    }

    return newAnalysisDto;
  }

  private EntityModel<AnalysisDTO> generateLinks(AnalysisDTO analysisDTO) {
    EntityModel<AnalysisDTO> analysisDTOEntityModel = EntityModel.of(analysisDTO);
    analysisDTOEntityModel
            .add(linkTo(methodOn(AnalysisController.class).getAnalysisById(analysisDTO.getRootEntityID())).withSelfRel());
    return analysisDTOEntityModel;
  }

  private List<EntityModel<AnalysisDTO>> generateLinks(List<AnalysisDTO> analysisDTOList) {
    List<EntityModel<AnalysisDTO>> returnList = new ArrayList<>();
    analysisDTOList.forEach(element -> returnList.add(generateLinks(element)));
    return returnList;

  }
}
