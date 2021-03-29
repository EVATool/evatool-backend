package com.evatool.analysis.application.controller;

import com.evatool.EvaToolApp;
import com.evatool.analysis.domain.enums.ValueType;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.global.config.SwaggerConfig;
import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.application.services.ValueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.evatool.analysis.common.TestDataGenerator.*;

@WebMvcTest(ValueControllerImpl.class)
@ContextConfiguration(classes = {SwaggerConfig.class, EvaToolApp.class})
class ValueRestControllerMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ValueService valueService;

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingValues_ReturnValues() throws Exception {
            // given
            var valueDto = createDummyValueDto();

            // when
            when(valueService.findById(any(UUID.class))).thenReturn(valueDto);

            // then
            mvc.perform(get("/value" + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(valueDto.getName()));
        }

        @Test
        void testFindById_ExistingValue_CorrectRestLevel3() throws Exception {
            // given
            var valueDto = createDummyValueDto();
            valueDto.setId(UUID.randomUUID());

            // when
            given(valueService.findById(any(UUID.class))).willReturn(valueDto);

            // then
            mvc.perform(get("/value" + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.links").isNotEmpty())
                    .andExpect(jsonPath("$.links", hasSize(1)))
                    .andExpect(jsonPath("$.links[*].rel").value(containsInAnyOrder(
                            "self")))
                    .andExpect(jsonPath("$.links[*].href").value(containsInAnyOrder(
                            "http://localhost" + "/value" + "/" + valueDto.getId())));
        }

        @Test
        void testFindById_NonExistingvalue_ReturnErrorMessage() throws Exception {
            // given
            var id = UUID.randomUUID().toString();

            // when
            when(valueService.findById(any(UUID.class))).thenThrow(EntityNotFoundException.class);

            // then
            mvc.perform(get("/value" + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.*", hasSize(6)))
                    .andExpect(jsonPath("$.timestamp").isNotEmpty())
                    .andExpect(jsonPath("$.status").isNotEmpty())
                    .andExpect(jsonPath("$.error").isNotEmpty())
                    .andExpect(jsonPath("$.trace").isNotEmpty())
                    .andExpect(jsonPath("$.message").isEmpty()) // exists but contains null.
                    .andExpect(jsonPath("$.path").isNotEmpty());
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingValues_ReturnValue(int value) throws Exception {
            var valueDtos = new ArrayList<ValueDto>();
            for (int i = 0; i < value; i++) {
                // given
                var valueDto = createDummyValueDto();
                valueDtos.add(valueDto);
            }
            // when
            given(valueService.findAll()).willReturn(valueDtos);

            // then
            mvc.perform(get("/value")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(value)));
        }
    }

    @Nested
    class FindAllByType {

        @Test
        void testFindAllByType_ExistingValues_ReturnValue() throws Exception {
            // given
            var socialValues = new ArrayList<ValueDto>();
            for (int i = 0; i < 3; i++) {
                var socialValue = createDummyValueDto();
                socialValue.setType(ValueType.SOCIAL);
                socialValues.add(socialValue);
            }

            var economicValues = new ArrayList<ValueDto>();
            for (int i = 0; i < 4; i++) {
                var economicValue = createDummyValueDto();
                economicValue.setType(ValueType.ECONOMIC);
                economicValues.add(economicValue);
            }

            // when
            given(valueService.findAllByType(ValueType.SOCIAL)).willReturn(socialValues);
            given(valueService.findAllByType(ValueType.ECONOMIC)).willReturn(economicValues);

            // then
            mvc.perform(get("/value").param("type", ValueType.SOCIAL.toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(socialValues.size())));

            mvc.perform(get("/value" + "?type=" + ValueType.ECONOMIC.toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(economicValues.size())));
        }
    }

    @Nested
    class Create {

        @Test
        void testCreate_CreatedValue_ReturnCreatedValue() throws Exception {
            // given
            var valuesDto = createDummyValueDto();
            var id = UUID.randomUUID();
            valuesDto.setId(id);

            // when
            when(valueService.create(any(ValueDto.class))).thenReturn(valuesDto);

            // then
            mvc.perform(post("/value").content(new ObjectMapper().writeValueAsString(valuesDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdate_UpdatedValue_ReturnUpdatedValue() throws Exception {
            // given
            var valueDto = createDummyValueDto();
            valueDto.setId(UUID.randomUUID());

            // when
            when(valueService.create(any(ValueDto.class))).thenReturn(valueDto);
            valueDto.setName("new_name");
            when(valueService.update(any(ValueDto.class))).thenReturn(valueDto);

            // then
            mvc.perform(put("/value").content(new ObjectMapper().writeValueAsString(valueDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value(valueDto.getName()));
        }
    }

    @Nested
    class DeleteById {

        @Test
        void testDeleteById_DeletedValue_ReturnNoValue() throws Exception {
            // given

            // when
            doNothing().when(valueService).deleteById(any(UUID.class));

            // then
            mvc.perform(delete("/value" + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}

