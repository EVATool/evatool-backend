package com.evatool.analysis.domain.events;


import com.evatool.analysis.domain.events.json.AnalysisImpactJson;
import com.evatool.analysis.domain.repository.AnalysisImpactRepository;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import com.evatool.global.event.impact.ImpactCreatedEvent;
import com.evatool.global.event.impact.ImpactDeletedEvent;
import com.evatool.global.event.impact.ImpactUpdatedEvent;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StakeholderEventListener {

    private static final String DEBUGFORMAT = "EVENT: %s With Payload %s";
    final Logger logger = LoggerFactory.getLogger(StakeholderEventListener.class);
    Gson gson = new Gson();

    @Autowired
    AnalysisImpactRepository analysisImpactRepository;

    @Autowired
    StakeholderRepository stakeholderRepository;

    @EventListener
    public void impactCreated(ImpactCreatedEvent event) {
        logger.info("Impact created event");
        if (logger.isDebugEnabled()) logger.debug(String.format(DEBUGFORMAT, event.getClass(), event.getJsonPayload()));
        AnalysisImpactJson impact = gson.fromJson(event.getJsonPayload(), AnalysisImpactJson.class);
    }

    @EventListener
    public void impactUpdated(ImpactUpdatedEvent event) {
        logger.info("impact updated event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        AnalysisImpactJson impact = gson.fromJson(event.getJsonPayload(), AnalysisImpactJson.class);
    }


    @EventListener
    public void impactDeleted(ImpactDeletedEvent event) {
        logger.info("impact deleted event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        AnalysisImpactJson impact = gson.fromJson(event.getJsonPayload(), AnalysisImpactJson.class);
    }


}
