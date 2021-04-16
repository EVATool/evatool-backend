package com.evatool.analysis.domain.events.listener;

import com.evatool.analysis.common.error.execptions.EventEntityAlreadyExistsException;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
public class AnalysisEventListener {

    private static final String DEBUGFORMAT = "EVENT: %s With Payload %s";
    final Logger logger = LoggerFactory.getLogger(AnalysisEventListener.class);

    @Autowired
    private AnalysisRepository analysisRepository;

    @EventListener
    @Async
    public void analyseCreated(AnalysisCreatedEvent event){
        logger.info("analyse created event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        if (analysisRepository.existsById(Analysis.fromJson(event.getJsonPayload()).getAnalysisId())) {
            throw new EventEntityAlreadyExistsException();
        }
        Analysis analysis = Analysis.fromJson(event.getJsonPayload());
        analysisRepository.save(analysis);
    }
}
