package com.evatool.variants.domain.repository.events;

import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.requirements.RequirementCreatedEvent;
import com.evatool.global.event.requirements.RequirementDeletedEvent;
import com.evatool.variants.common.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.variants.domain.entities.VariantsRequirements;
import com.evatool.variants.domain.events.VariantEventListener;
import com.evatool.variants.domain.repositories.VariantRequirementsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
class VariantRequirementsDeletedEventListenerTest {

    @Autowired
    VariantEventListener variantEventListener;

    @Autowired
    VariantRequirementsRepository variantRequirementsRepository;

    @Test
    void testOnApplicationEvent_PublishEvent_RequirementsDeleted() {

        // given
        UUID id = UUID.randomUUID();
        VariantsRequirements variantsRequirements = new VariantsRequirements();
        variantsRequirements.setRequirementId(id);
        variantsRequirements = variantRequirementsRepository.save(variantsRequirements);

        String json = String.format("{\"requirementId\":\"%s\"}", variantsRequirements.getRequirementId().toString());
        UUID tempId = variantsRequirements.getRequirementId();

        // when
        RequirementDeletedEvent requirementDeletedEvent = new RequirementDeletedEvent(json);
        variantEventListener.requirementDeleted(requirementDeletedEvent);

        // then
        Optional<VariantsRequirements> optionalRequirementsAnalysis = variantRequirementsRepository.findById(tempId);
        assertThat(optionalRequirementsAnalysis).isNotPresent();

    }

    @Test
    void testOnApplicationEvent_RequirementsDoesNotExist_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"analysisId\":\"%s\"}", id.toString());

        // when
        AnalysisDeletedEvent analysisDeletedEvent = new AnalysisDeletedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> variantEventListener.analyseDeleted(analysisDeletedEvent));
    }
}
