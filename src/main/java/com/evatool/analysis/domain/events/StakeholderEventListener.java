package com.evatool.analysis.domain.events;


import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.events.json.AnalysisImpactJson;
import com.evatool.analysis.domain.model.AnalysisImpact;
import com.evatool.analysis.domain.model.Stakeholder;
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
import java.util.Optional;


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
        AnalysisImpactJson impactJson = gson.fromJson(event.getJsonPayload(), AnalysisImpactJson.class);
        AnalysisImpact impact = new AnalysisImpact();
        impact.setId(impactJson.getId());
        impact.setImpactValue(impactJson.getValue());
        impact = analysisImpactRepository.save(impact);
        Optional<Stakeholder> stakeholderOptional = stakeholderRepository.findById(impactJson.getStakeholderId());
        if(!stakeholderOptional.isEmpty()){
            Stakeholder stakeholder = stakeholderOptional.get();
            stakeholder.getImpact().add(impact);
            stakeholderRepository.save(stakeholder);
        }
    }

    @EventListener
    public void impactUpdated(ImpactUpdatedEvent event) {
        logger.info("impact updated event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        AnalysisImpactJson impactJson = gson.fromJson(event.getJsonPayload(), AnalysisImpactJson.class);
        Optional<AnalysisImpact> impactOptional = analysisImpactRepository.findById(impactJson.getId());
        if(impactOptional.isEmpty()){
            throw new EntityNotFoundException(AnalysisImpact.class, impactJson.getId());
        }else{
            AnalysisImpact impact = impactOptional.get();
            impact.setImpactValue(impactJson.getValue());
            impact = analysisImpactRepository.save(impact);
            Optional<Stakeholder> stakeholderOptional  = stakeholderRepository.findById(impactJson.getStakeholderId());
            if(!impactOptional.isEmpty()) {
                Stakeholder stakeholder = stakeholderOptional.get();
                if(!stakeholder.getImpact().contains(impact)){
                    stakeholder.getImpact().add(impact);
                    stakeholderRepository.save(stakeholder);
                }
            }
        }
    }


    @EventListener
    public void impactDeleted(ImpactDeletedEvent event) {
        logger.info("impact deleted event");
        if(logger.isDebugEnabled())logger.debug(String.format(DEBUGFORMAT,event.getClass(), event.getJsonPayload()));
        AnalysisImpactJson impactJson = gson.fromJson(event.getJsonPayload(), AnalysisImpactJson.class);
        AnalysisImpact impact = new AnalysisImpact();
        impact.setId(impactJson.getId());
        impact.setImpactValue(impactJson.getValue());
        Optional<Stakeholder> stakeholderOptional = stakeholderRepository.findById(impactJson.getStakeholderId());
        if(stakeholderOptional.isEmpty()){
            analysisImpactRepository.delete(impact);
        }else{
            Stakeholder stakeholder = stakeholderOptional.get();
            stakeholder.getImpact().remove(impact);
            stakeholderRepository.save(stakeholder);
            analysisImpactRepository.delete(impact);
        }
    }


}
