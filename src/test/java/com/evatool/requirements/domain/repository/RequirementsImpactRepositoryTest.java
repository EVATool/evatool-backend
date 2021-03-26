package com.evatool.requirements.domain.repository;

import com.evatool.requirements.domain.entity.RequirementDimension;
import com.evatool.requirements.domain.entity.RequirementsImpact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.evatool.requirements.common.TestDataGenerator.getRequirementDimension;
import static com.evatool.requirements.common.TestDataGenerator.getRequirementsImpacts;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RequirementsImpactRepositoryTest {

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementDimensionRepository requirementDimensionRepository;

    @Test
    void testFindById_InsertedImpact_ReturnImpact() {

        // given
        RequirementDimension requirementDimension = getRequirementDimension();
        requirementDimensionRepository.save(requirementDimension);

        RequirementsImpact impact = getRequirementsImpacts(requirementDimension);
        requirementsImpactsRepository.save(impact);

        // when
        RequirementsImpact found = requirementsImpactsRepository.findById(impact.getId()).orElse(null);

        // then
        assertThat(found.getId()).isEqualTo(impact.getId());
    }

    @Test
    void testSave_InsertedImpact_IdIsNotNull() {

        // given
        RequirementDimension requirementDimension = getRequirementDimension();
        requirementDimensionRepository.save(requirementDimension);
        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementDimension);

        // when
        requirementsImpactsRepository.save(requirementsImpact);

        // then
        assertThat(requirementsImpact.getId()).isNotNull();
    }

    @Test
    void testSave_InsertedImpact_IdIsUuid() {

        // given
        RequirementDimension requirementDimension = getRequirementDimension();
        requirementDimensionRepository.save(requirementDimension);
        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementDimension);

        // when
        requirementsImpactsRepository.save(requirementsImpact);

        // then
        UUID.fromString(requirementsImpact.getId().toString());

        assertThat(requirementsImpact.getId()).isNotNull();

    }

    @Test
    void testDelete_DeletedImpact_ReturnNull() {

        // given
        RequirementDimension requirementDimension = getRequirementDimension();
        requirementDimensionRepository.save(requirementDimension);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementDimension);
        requirementsImpactsRepository.save(requirementsImpact);

        // when
        requirementsImpactsRepository.delete(requirementsImpact);
        var found = requirementsImpactsRepository.findById(requirementsImpact.getId()).orElse(null);

        // then
        assertThat(found).isNull();
    }
}
