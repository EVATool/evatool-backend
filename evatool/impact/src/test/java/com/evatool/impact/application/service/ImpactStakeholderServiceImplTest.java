package com.evatool.impact.application.service;

import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.common.exception.EntityNullException;
import com.evatool.impact.common.exception.IdNullException;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.evatool.impact.TestDataGenerator.getStakeholder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: [hbuhl] Use real database for tests (toggle between in-memory and real db)
// TODO: [hbuhl] Complete more elaborate testing setup in this class and copy it to other classes + test all cases!
@SpringBootTest
public class ImpactStakeholderServiceImplTest {
    @Autowired
    ImpactStakeholderService stakeholderService;

    @BeforeEach
    void clearData() {
        stakeholderService.deleteStakeholders();
    }

    void insertStakeholder() {
        var stakeholder = getStakeholder();
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

        @Test
        public void testGetStakeholderById_NullId_ThrowIdNullException() {
            // given
            var stakeholder = getStakeholder();

            // when

            // then
            assertThatExceptionOfType(IdNullException.class).isThrownBy(() -> stakeholderService.findStakeholderById(stakeholder.getId()));
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
        public void testInsertStakeholder_InsertedStakeholder_ReturnInsertedStakeholder() throws IdNullException, EntityNotFoundException {
            // given
            var stakeholder = getStakeholder();

            // when
            var insertedStakeholder = stakeholderService.createStakeholder(stakeholder);
            var retrievedStakeholder = stakeholderService.findStakeholderById(insertedStakeholder.getId());

            // then
            assertThat(retrievedStakeholder).isNotNull();
            assertThat(insertedStakeholder.getId()).isEqualTo(retrievedStakeholder.getId());
            assertThat(insertedStakeholder.getName()).isEqualTo(retrievedStakeholder.getName());
        }

        @Test
        public void testInsertStakeholder_DuplicateInsert_AllowDuplicateInsert() {
            // given
            var stakeholder = getStakeholder();

            // when
            var insertedStakeholder1 = stakeholderService.createStakeholder(stakeholder);
            var insertedStakeholder2 = stakeholderService.createStakeholder(stakeholder);
            var stakeholders = stakeholderService.getAllStakeholders();

            // then
            assertThat(stakeholders.size()).isEqualTo(1);
            assertThat(stakeholder.getId()).isEqualTo(insertedStakeholder1.getId());
            assertThat(stakeholder.getId()).isEqualTo(insertedStakeholder2.getId());
            assertThat(stakeholder.getName()).isEqualTo(insertedStakeholder1.getName());
            assertThat(stakeholder.getName()).isEqualTo(insertedStakeholder2.getName());
        }

        @Test
        public void testInsertStakeholder_NullStakeholder_ThrowEntityNullException() {
            // given
            ImpactStakeholder stakeholder = null;

            // when

            // then
            assertThatExceptionOfType(EntityNullException.class).isThrownBy(() -> stakeholderService.createStakeholder(stakeholder));
        }
    }

    @Nested
    public class Update {
        @Test
        public void testUpdateStakeholder_UpdatedStakeholder_ReturnUpdatedStakeholder() throws IdNullException, EntityNotFoundException {
            // given
            var stakeholder = getStakeholder();
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
        public void testUpdateStakeholder_UpdatedNonExistingId_ThrowEntityNotFoundException() throws IdNullException, EntityNotFoundException {
            // given
            var stakeholder = getStakeholder();
            stakeholder.setId(UUID.randomUUID().toString());

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> stakeholderService.updateStakeholder(stakeholder));
        }

        @Test
        public void testUpdateStakeholder_UpdatedNullId_ThrowEntityIdNullException() {
            // given
            var stakeholder = getStakeholder();

            // when

            // then
            assertThatExceptionOfType(IdNullException.class).isThrownBy(() -> stakeholderService.updateStakeholder(stakeholder));
        }
    }

    @Nested
    public class Delete {
        @Test
        public void testDeleteStakeholderById_DeleteStakeholder_ReturnNoStakeholders() throws IdNullException, EntityNotFoundException {
            // given
            var stakeholder = getStakeholder();

            // when
            var insertedStakeholder = stakeholderService.createStakeholder(stakeholder);
            stakeholderService.deleteStakeholderById(insertedStakeholder.getId());

            // then
            var stakeholders = stakeholderService.getAllStakeholders();
            assertThat(stakeholders.size()).isEqualTo(0);
        }

        @Test
        public void testDeleteStakeholderById_DeleteNonExistingId_ReturnHttpStatusNotFound() throws IdNullException, EntityNotFoundException {
            // given
            var stakeholder = getStakeholder();
            stakeholder.setId(UUID.randomUUID().toString());

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> stakeholderService.deleteStakeholderById(stakeholder.getId()));
        }
    }
}