package com.evatool.impact.domain.event;

import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.analysis.AnalysisUpdatedEvent;
import com.evatool.impact.common.exception.EventEntityAlreadyExistsException;
import com.evatool.impact.common.exception.EventEntityDoesNotExistException;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.event.json.mapper.ImpactAnalysisJsonMapper;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ImpactAnalysisEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ImpactAnalysisEventListener.class);

    private final ImpactAnalysisRepository analysisRepository;

    public ImpactAnalysisEventListener(ImpactAnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @EventListener
    public void onAnalysisCreatedEvent(final AnalysisCreatedEvent event) {
        logger.info("Analysis created event received");
        var jsonPayload = event.getJsonPayload();
        var analysis = ImpactAnalysisJsonMapper.fromJson(jsonPayload);
        if (analysisRepository.existsById(analysis.getId())) {
            throw new EventEntityAlreadyExistsException(ImpactAnalysis.class.getSimpleName());
        }
        analysisRepository.save(analysis);
        logger.info("Analysis created event successfully processed");
    }

    @EventListener
    public void onAnalysisDeletedEvent(final AnalysisDeletedEvent event) {
        logger.info("Analysis deleted event received");
        var jsonPayload = event.getJsonPayload();
        var analysis = ImpactAnalysisJsonMapper.fromJson(jsonPayload);
        if (!analysisRepository.existsById(analysis.getId())) {
            throw new EventEntityDoesNotExistException(ImpactAnalysis.class.getSimpleName());
        }
        analysisRepository.delete(analysis);
        logger.info("Analysis deleted event successfully processed");
    }
}
