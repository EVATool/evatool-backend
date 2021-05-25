package com.evatool.application.mapper;

import com.evatool.application.dto.ValueDto;
import com.evatool.domain.entity.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValueMapperTest extends SuperMapperTest<Value, ValueDto, ValueMapper> {

    @Autowired
    private ValueMapper mapper;

}
