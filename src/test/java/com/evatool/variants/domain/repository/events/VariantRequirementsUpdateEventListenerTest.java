package com.evatool.variants.domain.repository.events;

import com.evatool.global.event.requirements.RequirementUpdatedEvent;
import com.evatool.variants.common.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.variants.common.error.exceptions.IllegalEventPayloadException;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.entities.VariantsRequirements;
import com.evatool.variants.domain.events.VariantEventListener;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantRequirementsRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static com.evatool.variants.common.TestDataGenerator.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = "non-async")
class VariantRequirementsUpdateEventListenerTest {

    @Autowired
    VariantEventListener variantEventListener;

    @Autowired
    VariantRequirementsRepository variantRequirementsRepository;

    @Autowired
    VariantRepository variantRepository;

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @Test
    void testOnApplicationEvent_PublishEvent_RequirementsUpdated() {

        VariantsAnalysis variantsAnalysis = getVariantsAnalysis();
        variantsAnalysis = variantsAnalysisRepository.save(variantsAnalysis);

        Variant variant = getVariant("VariantTitle","VariantDescription",variantsAnalysis);

        variant = variantRepository.save(variant);

        Collection<Variant> variants = new ArrayList<>();
        variants.add(variant);

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"requirementId\":\"%s\"}", id.toString());

        VariantsRequirements variantsRequirements;
        try {
            JSONObject jsonObject = new JSONObject(json);
            variantsRequirements = new VariantsRequirements();
            variantsRequirements.setRequirementId(UUID.fromString(jsonObject.getString("requirementId")));
            variantsRequirements.setVariants(variants);
        } catch (JSONException jex) {
            throw new IllegalEventPayloadException(json);
        }

        variantsRequirements = variantRequirementsRepository.save(variantsRequirements);

        Variant variant1 = variantsRequirements.getVariants().iterator().next();

        String updateTitle = "UPDATED TITLE";
        variant1.setTitle(updateTitle);
        String variant1Json = variantsRequirements.toJson();

        RequirementUpdatedEvent requirementUpdateEvent = new RequirementUpdatedEvent(variant1Json);
        variantEventListener.requirementUpdated(requirementUpdateEvent);

        // then
        Optional<VariantsRequirements> optionalRequirementsVariant = variantRequirementsRepository.findById(variantsRequirements.getRequirementId());
        assertThat(optionalRequirementsVariant).isPresent();
        assertThat(optionalRequirementsVariant.get().getVariants().iterator().next().getTitle()).isEqualTo(updateTitle);
    }

    @Test
    void testOnApplicationEvent_RequirementsDoesNotExists_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String newTitle = "newTitle";
        String newDescription = "newDescription";
        String json = String.format("{\"requirementId\":\"762f12b5-7b12-46f5-b9c5-469dd4b44d1e\",\"variants\":[{\"id\":\"b412b34e-8768-4cb7-8c26-be17a45a2c3b\",\"title\":\"UPDATED TITLE\",\"archived\":false,\"description\":\"VariantDescription\",\"variantsAnalysis\":{\"analysisId\":\"9df9db36-71cd-41cc-bdc3-c64de652b801\",\"links\":[]},\"links\":[]}],\"links\":[]}", id.toString(),newTitle,newDescription);

        // when
        RequirementUpdatedEvent requirementUpdateEvent = new RequirementUpdatedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> variantEventListener.requirementUpdated(requirementUpdateEvent));



    }

}
