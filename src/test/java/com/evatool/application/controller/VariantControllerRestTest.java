package com.evatool.application.controller;

import com.evatool.application.dto.VariantDto;
import com.evatool.domain.entity.Variant;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class VariantControllerRestTest extends CrudControllerRestTest<Variant, VariantDto> implements FindByAnalysisControllerRestTest {

}
