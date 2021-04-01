package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.interfaces.AnalysisController;
import com.evatool.analysis.application.services.AnalysisDTOService;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.events.AnalysisEventPublisher;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
    private AnalysisEventPublisher analysisEventPublisher;

    @Autowired
    private AnalysisDTOService analysisDTOService;

    Logger logger = LoggerFactory.getLogger(AnalysisControllerImpl.class);

    @Override
    public List<EntityModel<AnalysisDTO>> getAnalysisList(@ApiParam(value = "Is A Template") @Valid @RequestParam(value = "isTemplate", required = false) Boolean isTemplate) {
        logger.info("[GET] /analysis");
        List<Analysis> analysisList = new ArrayList<>();
        if (isTemplate == null) {
            analysisList = analysisRepository.findAll();
        } else {
            analysisList = analysisDTOService.findAllByIdTemplate(isTemplate);
        }

        if (analysisList.isEmpty()) {
            return Arrays.asList();
        }
        return generateLinks(analysisDTOService.findAll(analysisList));
    }

    @Override
    public EntityModel<AnalysisDTO> getAnalysisById(UUID id) {
        logger.info("[GET] /analysis/{id}");
        Optional<Analysis> analysis = analysisRepository.findById(id);
        if (analysis.isEmpty()){
            throw new EntityNotFoundException(Analysis.class, id);
        }
        return generateLinks(analysisDTOService.findById(analysis.get()));
    }

    @Override
    public EntityModel<AnalysisDTO> addAnalysis(@RequestBody AnalysisDTO analysisDTO) {
        logger.info("[POST] /analysis");
        Analysis analysis = analysisRepository.save(analysisDTOService.create(analysisDTO));
        analysisEventPublisher.publishEvent(new AnalysisCreatedEvent(analysis.toJson()));
        return getAnalysisById(analysis.getAnalysisId());
    }

    @Override
    public EntityModel<AnalysisDTO> updateAnalysis(@RequestBody AnalysisDTO analysisDTO) {
        logger.info("[PUT] /analysis");
        analysisDTOService.update(analysisDTO);
        return getAnalysisById(analysisDTO.getRootEntityID());
    }

    @Override
    public ResponseEntity<Void> deleteAnalysis(UUID id) {
        logger.info("[DELETE] /analysis/{id}");
        Optional<Analysis> analysisOptional = analysisRepository.findById(id);
        if (analysisOptional.isEmpty()){
            throw new EntityNotFoundException(Analysis.class, id);
        }
        Analysis analysis = analysisOptional.get();
        analysisRepository.deleteById(id);
        analysisEventPublisher.publishEvent(new AnalysisDeletedEvent(analysis.toString()));
        return ResponseEntity.ok().build();

    }

    private EntityModel<AnalysisDTO> generateLinks(AnalysisDTO analysisDTO){
        EntityModel<AnalysisDTO> analysisDTOEntityModel = EntityModel.of(analysisDTO);
        analysisDTOEntityModel.add(linkTo(methodOn(AnalysisController.class).getAnalysisById(analysisDTO.getRootEntityID())).withSelfRel());
        return analysisDTOEntityModel;
    }

    private List<EntityModel<AnalysisDTO>> generateLinks(List<AnalysisDTO> analysisDTOList){
        List<EntityModel<AnalysisDTO>> returnList = new ArrayList<>();
        analysisDTOList.stream().forEach(element -> returnList.add(generateLinks(element)));
        return returnList;

    }
}
