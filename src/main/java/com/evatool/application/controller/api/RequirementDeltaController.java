package com.evatool.application.controller.api;

import com.evatool.application.dto.RequirementDeltaDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface RequirementDeltaController extends CrudController<RequirementDeltaDto>, FindByAnalysisController<RequirementDeltaDto> {

}
