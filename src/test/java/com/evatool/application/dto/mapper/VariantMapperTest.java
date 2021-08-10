package com.evatool.application.dto.mapper;

import com.evatool.application.dto.VariantDto;
import com.evatool.domain.entity.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VariantMapperTest extends SuperMapperTest<Variant, VariantDto, VariantDtoMapper> {

    @Autowired
    private VariantDtoMapper mapper;

}
