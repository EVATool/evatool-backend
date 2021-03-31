package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.DimensionDto;
import com.evatool.impact.common.DimensionType;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.UUID;

import static com.evatool.impact.application.controller.UriUtil.DIMENSIONS;
import static com.evatool.impact.common.TestDataGenerator.createDummyDimensionDto;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DimensionRestController.class)
class DimensionRestControllerMockServiceTest extends ControllerMockTest {

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingDimension_ReturnDimension() throws Exception {
            // given
            var dimensionDto = createDummyDimensionDto();

            // when
            when(dimensionService.findById(any(UUID.class))).thenReturn(dimensionDto);

            // then
            mvc.perform(get(DIMENSIONS + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(dimensionDto.getName()));
        }

        @Test
        void testFindById_ExistingDimension_CorrectRestLevel3() throws Exception {
            // given
            var dimensionDto = createDummyDimensionDto();
            dimensionDto.setId(UUID.randomUUID());

            // when
            given(dimensionService.findById(any(UUID.class))).willReturn(dimensionDto);

            // then
            mvc.perform(get(DIMENSIONS + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.links").isNotEmpty())
                    .andExpect(jsonPath("$.links", hasSize(1)))
                    .andExpect(jsonPath("$.links[*].rel").value(containsInAnyOrder(
                            "self")))
                    .andExpect(jsonPath("$.links[*].href").value(containsInAnyOrder(
                            "http://localhost" + DIMENSIONS + "/" + dimensionDto.getId())));
        }

        @Test
        void testFindById_NonExistingDimension_ReturnErrorMessage() throws Exception {
            // given
            var id = UUID.randomUUID().toString();

            // when
            when(dimensionService.findById(any(UUID.class))).thenThrow(EntityNotFoundException.class);

            // then
            mvc.perform(get(DIMENSIONS + "/" + id)
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
        void testFindAll_ExistingDimensions_ReturnDimensions(int value) throws Exception {
            var dimensionDtoList = new ArrayList<DimensionDto>();
            for (int i = 0; i < value; i++) {
                // given
                var dimensionDto = createDummyDimensionDto();
                dimensionDtoList.add(dimensionDto);
            }
            // when
            given(dimensionService.findAll()).willReturn(dimensionDtoList);

            // then
            mvc.perform(get(DIMENSIONS)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(value)));
        }
    }

    @Nested
    class FindAllByType {

        @Test
        void testFindAllByType_ExistingDimensions_ReturnDimensions() throws Exception {
            // given
            var socialDimensions = new ArrayList<DimensionDto>();
            for (int i = 0; i < 3; i++) {
                var socialDimension = createDummyDimensionDto();
                socialDimension.setType(DimensionType.SOCIAL);
                socialDimensions.add(socialDimension);
            }

            var economicDimensions = new ArrayList<DimensionDto>();
            for (int i = 0; i < 4; i++) {
                var economicDimension = createDummyDimensionDto();
                economicDimension.setType(DimensionType.ECONOMIC);
                economicDimensions.add(economicDimension);
            }

            // when
            given(dimensionService.findAllByType(DimensionType.SOCIAL)).willReturn(socialDimensions);
            given(dimensionService.findAllByType(DimensionType.ECONOMIC)).willReturn(economicDimensions);

            // then
            mvc.perform(get(DIMENSIONS).param("type", DimensionType.SOCIAL.toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(socialDimensions.size())));

            mvc.perform(get(DIMENSIONS + "?type=" + DimensionType.ECONOMIC.toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(economicDimensions.size())));
        }
    }

    @Nested
    class Create {

        @Test
        void testCreate_CreatedDimension_ReturnCreatedDimension() throws Exception {
            // given
            var dimensionDto = createDummyDimensionDto();
            var id = UUID.randomUUID();
            dimensionDto.setId(id);

            // when
            when(dimensionService.create(any(DimensionDto.class))).thenReturn(dimensionDto);

            // then
            mvc.perform(post(DIMENSIONS).content(new ObjectMapper().writeValueAsString(dimensionDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdate_UpdatedDimension_ReturnUpdatedDimension() throws Exception {
            // given
            var dimensionDto = createDummyDimensionDto();
            dimensionDto.setId(UUID.randomUUID());

            // when
            when(dimensionService.create(any(DimensionDto.class))).thenReturn(dimensionDto);
            dimensionDto.setName("new_name");
            when(dimensionService.update(any(DimensionDto.class))).thenReturn(dimensionDto);

            // then
            mvc.perform(put(DIMENSIONS).content(new ObjectMapper().writeValueAsString(dimensionDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value(dimensionDto.getName()));
        }
    }

    @Nested
    class DeleteById {

        @Test
        void testDeleteById_DeletedDimension_ReturnNoDimensions() throws Exception {
            // given

            // when
            doNothing().when(dimensionService).deleteById(any(UUID.class));

            // then
            mvc.perform(delete(DIMENSIONS + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}

