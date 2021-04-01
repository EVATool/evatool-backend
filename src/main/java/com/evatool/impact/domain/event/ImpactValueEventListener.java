package com.evatool.impact.domain.event;

import com.evatool.global.event.value.ValueCreatedEvent;
import com.evatool.global.event.value.ValueDeletedEvent;
import com.evatool.global.event.value.ValueUpdatedEvent;
import com.evatool.impact.common.exception.EventEntityAlreadyExistsException;
import com.evatool.impact.common.exception.EventEntityDoesNotExistException;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactValue;
import com.evatool.impact.domain.event.json.ImpactValueJson;
import com.evatool.impact.domain.event.json.mapper.ImpactValueJsonMapper;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import com.evatool.impact.domain.repository.ImpactValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ImpactValueEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueEventListener.class);

    private final ImpactValueRepository impactValueRepository;

    private final ImpactAnalysisRepository impactAnalysisRepository;

    public ImpactValueEventListener(ImpactValueRepository impactValueRepository, ImpactAnalysisRepository impactAnalysisRepository) {
        this.impactValueRepository = impactValueRepository;
        this.impactAnalysisRepository = impactAnalysisRepository;
    }

    @EventListener
    public void onValueCreatedEvent(final ValueCreatedEvent event) {
        logger.info("Value created event received");
        var jsonPayload = event.getJsonPayload();
        var valueJson = ImpactValueJsonMapper.fromString(jsonPayload);
        assertChildrenExist(valueJson);
        var value = ImpactValueJsonMapper.fromJson(valueJson);
        if (impactValueRepository.existsById(valueJson.getId())) {
            throw new EventEntityAlreadyExistsException(ImpactValue.class.getSimpleName());
        }
        impactValueRepository.save(value);
        logger.info("Value created event successfully processed");
    }

    @EventListener
    public void onValueDeletedEvent(final ValueDeletedEvent event) {
        logger.info("Value deleted event received");
        var jsonPayload = event.getJsonPayload();
        var valueJson = ImpactValueJsonMapper.fromString(jsonPayload);
        assertChildrenExist(valueJson);
        var value = ImpactValueJsonMapper.fromJson(valueJson);
        if (!impactValueRepository.existsById(valueJson.getId())) {
            throw new EventEntityDoesNotExistException(ImpactValue.class.getSimpleName());
        }
        impactValueRepository.delete(value);
        logger.info("Value deleted event successfully processed");
    }

    @EventListener
    public void onValueUpdatedEvent(final ValueUpdatedEvent event) {
        logger.info("Value updated event received");
        var jsonPayload = event.getJsonPayload();
        var valueJson = ImpactValueJsonMapper.fromString(jsonPayload);
        assertChildrenExist(valueJson);
        var value = ImpactValueJsonMapper.fromJson(valueJson);
        if (!impactValueRepository.existsById(valueJson.getId())) {
            throw new EventEntityDoesNotExistException(ImpactValue.class.getSimpleName());
        }
        impactValueRepository.save(value);
        logger.info("Value updated event successfully processed");
    }

    private void assertChildrenExist(ImpactValueJson impactValueJson) { // TODO add tests
        var analysis = this.impactAnalysisRepository.findById(impactValueJson.getAnalysisId());
        if (analysis.isEmpty()) {
            throw new EventEntityDoesNotExistException(ImpactAnalysis.class.getSimpleName() + " (child)");
        }
    }
}
