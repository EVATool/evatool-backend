package com.evatool.impact.application.controller;

import com.evatool.EvaToolApp;
import com.evatool.global.config.SwaggerConfig;
import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.service.ImpactService;
import com.evatool.impact.common.exception.EntityNotFoundException;
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

import static com.evatool.impact.application.controller.UriUtil.*;
import static com.evatool.impact.application.controller.UriUtil.DELETE_DIMENSION;
import static com.evatool.impact.common.TestDataGenerator.*;
import static com.evatool.impact.common.TestDataGenerator.createDummyImpactDto;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImpactRestController.class)
@ContextConfiguration(classes = {SwaggerConfig.class, EvaToolApp.class})
public class ImpactRestControllerMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImpactService impactService;

    @Nested
    class GetById {

        @Test
        void testGetImpactById_ExistingImpact_ReturnImpact() throws Exception {
            // given
            var impactDto = createDummyImpactDto();

            // when
            when(impactService.findImpactById(any(UUID.class))).thenReturn(impactDto);

            // then
            mvc.perform(get(IMPACTS + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.value").value(impactDto.getValue()));
        }

        @Test
        void testGetImpact_ExistingImpact_CorrectRestLevel3() throws Exception {
            // given
            var impactDto = createDummyImpactDto();
            impactDto.getDimension().setId(UUID.randomUUID());
            impactDto.getStakeholder().setId(UUID.randomUUID());
            impactDto.setId(UUID.randomUUID());

            // when
            given(impactService.findImpactById(any(UUID.class))).willReturn(impactDto);

            // then
            mvc.perform(get(IMPACTS + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.links").isNotEmpty())
                    .andExpect(jsonPath("$.links", hasSize(5)))
                    .andExpect(jsonPath("$.links[*].rel").value(containsInAnyOrder(
                            "self",
                            UPDATE_IMPACT,
                            DELETE_IMPACT,
                            GET_STAKEHOLDER,
                            GET_DIMENSION)))
                    .andExpect(jsonPath("$.links[*].href").value(containsInAnyOrder(
                            "http://localhost" + IMPACTS,
                            "http://localhost" + IMPACTS + "/" + impactDto.getId(),
                            "http://localhost" + IMPACTS + "/" + impactDto.getId(),
                            "http://localhost" + STAKEHOLDERS + "/" + impactDto.getStakeholder().getId(),
                            "http://localhost" + DIMENSIONS + "/" + impactDto.getDimension().getId())));
        }

        @Test
        void testGetImpactById_NonExistingImpact_ReturnErrorMessage() throws Exception {
            // given
            var id = UUID.randomUUID().toString();

            // when
            when(impactService.findImpactById(any(UUID.class))).thenThrow(EntityNotFoundException.class);

            // then
            mvc.perform(get(IMPACTS + "/" + id)
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
    class GetAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        void testGetAllImpacts_ExistingImpacts_ReturnImpacts(int value) throws Exception {
            var impactDtoList = new ArrayList<ImpactDto>();
            for (int i = 0; i < value; i++) {
                // given
                var impactDto = createDummyImpactDto();
                impactDtoList.add(impactDto);
            }
            // when
            given(impactService.getAllImpacts()).willReturn(impactDtoList);

            // then
            mvc.perform(get(IMPACTS)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(value)));
        }
    }


    @Nested
    class Insert {

        @Test
        void testInsertImpact_InsertedImpact_ReturnInsertedImpact() throws Exception {
            // given
            var impactDto = createDummyImpactDto();
            var id = UUID.randomUUID();
            impactDto.setId(id);

            // when
            when(impactService.createImpact(any(ImpactDto.class))).thenReturn(impactDto);

            // then
            mvc.perform(post(IMPACTS).content(new ObjectMapper().writeValueAsString(impactDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdateImpact_UpdatedImpact_ReturnUpdatedImpact() throws Exception {
            // given
            var impactDto = createDummyImpactDto();
            impactDto.setId(UUID.randomUUID());

            // when
            when(impactService.createImpact(any(ImpactDto.class))).thenReturn(impactDto);
            impactDto.setValue(0.75);
            when(impactService.updateImpact(any(ImpactDto.class))).thenReturn(impactDto);

            // then
            mvc.perform(put(IMPACTS).content(new ObjectMapper().writeValueAsString(impactDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.value").value(impactDto.getValue()));
        }
    }

    @Nested
    class Delete {

        @Test
        void testDeleteImpact_DeletedImpact_ReturnNoImpacts() throws Exception {
            // given

            // when
            doNothing().when(impactService).deleteImpactById(any(UUID.class));

            // then
            mvc.perform(delete(IMPACTS + "/" + UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}
