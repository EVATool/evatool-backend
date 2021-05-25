package com.evatool.application.controller.impl;

import com.evatool.application.controller.UriUtil;
import com.evatool.application.controller.api.AnalysisController;
import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.service.impl.AnalysisServiceImpl;
import com.evatool.domain.entity.Analysis;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Api(tags = "Analysis API-Endpoint")
@RestController
public class AnalysisControllerImpl extends CrudControllerImpl<Analysis, AnalysisDto> implements AnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisControllerImpl.class);

    private final AnalysisServiceImpl service;

    public AnalysisControllerImpl(AnalysisServiceImpl service) {
        super(service);
        this.service = service;
    }

    @GetMapping(value = "/test", params = {"!text"})
    public ResponseEntity<Iterable<EntityModel<AnalysisDto>>> test(@Valid @RequestParam Map<String, String> params) {
        for (var key : params.keySet()) {
            System.out.println(String.format("Key: %s, Value: %s", key, params.get(key)));
        }
        return null;
    }

    @GetMapping(value = "/test")
    public ResponseEntity<Iterable<EntityModel<AnalysisDto>>> test2(@Valid @RequestParam(name = "text") String lol) {
        System.out.println(lol);
        return null;
    }

    @Override
    @GetMapping(UriUtil.ANALYSES)
    public ResponseEntity<Iterable<EntityModel<AnalysisDto>>> findAll() { // TODO Tests
        var dtoListFound = service.findAll();
        return new ResponseEntity<>(withLinks(dtoListFound), HttpStatus.OK);
    }

    @Override
    @PostMapping(UriUtil.ANALYSES_DEEP_COPY)
    public ResponseEntity<EntityModel<AnalysisDto>> deepCopy(UUID templateAnalysisId, AnalysisDto analysisDto) {
        logger.debug("Deep Copy");
        return new ResponseEntity<>(withLinks(service.deepCopy(templateAnalysisId, analysisDto)), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(UriUtil.ANALYSES_ID)
    public ResponseEntity<EntityModel<AnalysisDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.ANALYSES)
    public ResponseEntity<EntityModel<AnalysisDto>> create(AnalysisDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.ANALYSES)
    public ResponseEntity<EntityModel<AnalysisDto>> update(AnalysisDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.ANALYSES_ID)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
