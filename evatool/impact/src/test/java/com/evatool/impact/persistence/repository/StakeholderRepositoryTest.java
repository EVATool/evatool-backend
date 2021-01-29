package com.evatool.impact.persistence.repository;

import com.evatool.impact.persistence.TestDataGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class StakeholderRepositoryTest {
    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Test
    public void testFindById_InsertedStakeholder_ReturnStakeholder() {
        // given
        var stakeholder = TestDataGenerator.getStakeholder();
        stakeholderRepository.save(stakeholder);

        // when
        var found = stakeholderRepository.findById(stakeholder.getId()).orElse(null);

        // then
        assertThat(found.getId()).isEqualTo(stakeholder.getId());
    }

    @Test
    public void testFindByName_InsertedStakeholder_ReturnStakeholder() {
        // given
        var stakeholder = TestDataGenerator.getStakeholder();
        stakeholderRepository.save(stakeholder);

        // when
        var found = stakeholderRepository.findByName(stakeholder.getName()).orElse(null);

        // then
        assertThat(found.getName()).isEqualTo(stakeholder.getName());
    }

    @Test
    public void testSave_InsertedStakeholder_IdIsNotNull() {
        // given
        var stakeholder = TestDataGenerator.getStakeholder();

        // when
        stakeholderRepository.save(stakeholder);

        // then
        assertThat(stakeholder.getId()).isNotNull();
    }

    @Test
    public void testSave_UpdatedStakeholder_ReturnUpdatedDimension() {
        // given
        var stakeholder = TestDataGenerator.getStakeholder();
        stakeholderRepository.save(stakeholder);
        var newName = "new_name";

        // when
        stakeholder.setName(newName);
        stakeholderRepository.save(stakeholder);
        var changedDimension = stakeholderRepository.findById(stakeholder.getId()).orElse(null);

        // then
        assertThat(changedDimension.getName()).isEqualTo(newName);
    }

    @Test
    public void testDelete_DeletedStakeholder_ReturnNull() {
        // given
        var stakeholder = TestDataGenerator.getStakeholder();
        stakeholderRepository.save(stakeholder);

        // when
        stakeholderRepository.delete(stakeholder);
        var found = stakeholderRepository.findById(stakeholder.getId()).orElse(null);

        // then
        assertThat(found).isNull();
    }
}