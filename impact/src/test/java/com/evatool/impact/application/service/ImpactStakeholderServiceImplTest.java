package com.evatool.impact.application.service;

import com.evatool.impact.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.evatool.impact.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ImpactStakeholderServiceImplTest {
    @Autowired
    ImpactStakeholderService stakeholderService;

    @BeforeEach
    void clearDatabase() {
        stakeholderService.deleteStakeholders();
    }

    void insertStakeholder() {
        var stakeholder = getStakeholderDto();
        stakeholderService.createStakeholder(stakeholder).getId();
    }

    @Nested
    public class GetById {
        @Test
        public void testGetStakeholderById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var stakeholder = getStakeholder();
            stakeholder.setId(UUID.randomUUID().toString());

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> stakeholderService.findStakeholderById(stakeholder.getId()));
        }
    }

    @Nested
    public class GetAll {
        @Test
        public void testGetAllStakeholders_InsertedStakeholder_ReturnStakeholder() {
            // given
            insertStakeholder();

            // when
            var stakeholders = stakeholderService.getAllStakeholders();

            // then
            assertThat(stakeholders.size()).isEqualTo(1);
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void testGetAllStakeholders_InsertedStakeholders_ReturnStakeholders(int value) {
            // given
            for (int i = 0; i < value; i++) {
                insertStakeholder();
            }

            // when
            var stakeholders = stakeholderService.getAllStakeholders();

            // then
            assertThat(stakeholders.size()).isEqualTo(value);
        }
    }

    @Nested
    public class Insert {
        @Test
        public void testInsertStakeholder_InsertedStakeholder_ReturnInsertedStakeholder() throws EntityNotFoundException {
            // given
            var stakeholder = getStakeholderDto();

            // when
            var insertedStakeholder = stakeholderService.createStakeholder(stakeholder);
            var retrievedStakeholder = stakeholderService.findStakeholderById(insertedStakeholder.getId());

            // then
            assertThat(retrievedStakeholder).isNotNull();
            assertThat(insertedStakeholder.getId()).isEqualTo(retrievedStakeholder.getId());
            assertThat(insertedStakeholder.getName()).isEqualTo(retrievedStakeholder.getName());
        }
    }

    @Nested
    public class Update {
        @Test
        public void testUpdateStakeholder_UpdatedStakeholder_ReturnUpdatedStakeholder() throws EntityNotFoundException {
            // given
            var stakeholder = getStakeholderDto();
            var insertedStakeholder = stakeholderService.createStakeholder(stakeholder);

            // when
            var newName = "new_name";
            insertedStakeholder.setName(newName);

            // then
            stakeholderService.updateStakeholder(insertedStakeholder);
            var updatedStakeholder = stakeholderService.findStakeholderById(insertedStakeholder.getId());
            assertThat(insertedStakeholder.getId()).isEqualTo(updatedStakeholder.getId());
            assertThat(updatedStakeholder.getName()).isEqualTo(newName);
        }

        @Test
        public void testUpdateStakeholder_UpdatedNonExistingId_ThrowEntityNotFoundException() {
            // given
            var stakeholder = getStakeholderDto();
            stakeholder.setId(UUID.randomUUID().toString());

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> stakeholderService.updateStakeholder(stakeholder));
        }
    }

    @Nested
    public class Delete {
        @Test
        public void testDeleteStakeholderById_DeleteStakeholder_ReturnNoStakeholders() throws EntityNotFoundException {
            // given
            var stakeholder = getStakeholderDto();

            // when
            var insertedStakeholder = stakeholderService.createStakeholder(stakeholder);
            stakeholderService.deleteStakeholderById(insertedStakeholder.getId());

            // then
            var stakeholders = stakeholderService.getAllStakeholders();
            assertThat(stakeholders.size()).isEqualTo(0);
        }

        @Test
        public void testDeleteStakeholderById_DeleteNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var stakeholder = getStakeholder();
            stakeholder.setId(UUID.randomUUID().toString());

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> stakeholderService.deleteStakeholderById(stakeholder.getId()));
        }
    }

    @Nested
    public class DeleteAll {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void testDeleteAll_InsertStakeholders_ReturnNoStakeholders(int value) {
            // given
            for (int i = 0; i < value; i++) {
                insertStakeholder();
            }

            // when
            stakeholderService.deleteStakeholders();

            // then
            var stakeholders = stakeholderService.getAllStakeholders();
            assertThat(stakeholders.size()).isEqualTo(0);
        }
    }
}