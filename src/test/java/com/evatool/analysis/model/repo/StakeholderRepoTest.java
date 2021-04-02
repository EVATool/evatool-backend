package com.evatool.analysis.model.repo;
import com.evatool.analysis.common.TestDataGenerator;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StakeholderRepoTest {

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Test
    void testFindByIdExistingStakeholder() {

        // given
        Stakeholder stakeholder = TestDataGenerator.getStakholder();
        stakeholder = stakeholderRepository.save(stakeholder);

        // when
        Stakeholder stakeholderFound = stakeholderRepository.findById(stakeholder.getStakeholderId()).orElse(null);

        // then
        assertThat(stakeholderFound.getStakeholderId()).isEqualTo(stakeholder.getStakeholderId());
    }

    @Test
    void testSaveInsertedStakeholderIdIsNotNull() {
        // given
        Stakeholder stakeholder = TestDataGenerator.getStakholder();

        // when
        stakeholderRepository.save(stakeholder);

        // then
        assertThat(stakeholder.getStakeholderId()).isNotNull();
    }

    @Test
    void testSaveInsertedStakeholderIdIsUuid() {
        // given
        Stakeholder stakeholder = TestDataGenerator.getStakholder();

        // when
        stakeholderRepository.save(stakeholder);

        // then
        UUID.fromString(stakeholder.getStakeholderId().toString());
        assertThat(stakeholder.getStakeholderId()).isNotNull();
    }

    @Test
    void testDeleteStakeholderReturnNull() {
        // given
        Stakeholder stakeholder = TestDataGenerator.getStakholder();
        stakeholderRepository.save(stakeholder);

        // when
        stakeholderRepository.delete(stakeholder);
        Stakeholder stakeholderFound = stakeholderRepository.findById(stakeholder.getStakeholderId()).orElse(null);

        // then
        assertThat(stakeholderFound).isNull();
    }
}
