package com.evatool.analysis.application.services;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.AnalysisMapper;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.events.AnalysisEventPublisher;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.model.NumericId;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.analysis.domain.repository.StakeholderRepository;
import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.analysis.AnalysisUpdatedEvent;
import com.evatool.global.event.requirements.RequirementDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnalysisService {

    final Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    @Autowired
    private AnalysisMapper analysisMapper;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private AnalysisEventPublisher eventPublisher;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    public List<AnalysisDTO> findAll() {
        List<Analysis> analysisList = analysisRepository.findAll();
        return analysisMapper.map(analysisList);
    }

    public List<Analysis> findAllByIdTemplate(boolean isTemplate) {
        logger.info("findAll");
        var analyses = analysisRepository.findAllByIsTemplate(isTemplate);
        return analyses;
    }

    public AnalysisDTO findById(UUID id) {
        logger.debug("findById [{}]",id);
        Optional<Analysis> analysis = analysisRepository.findById(id);
        if(analysis.isEmpty()) throw new EntityNotFoundException(Analysis.class, id);
        return analysisMapper.map(analysis.get());
    }

    public UUID createAnalysisWithUUID(AnalysisDTO analysisDTO) {
        logger.debug("create [{}]",analysisDTO);
        Analysis analysis = new Analysis();
        analysis.setAnalysisName(analysisDTO.getAnalysisName());
        analysis.setDescription(analysisDTO.getAnalysisDescription());
        analysis.setImage(analysisDTO.getImage());
        analysis.setLastUpdate(analysisDTO.getLastUpdate());
        analysis.setIsTemplate(analysisDTO.getIsTemplate());

        analysis = analysisRepository.save(analysis);
        eventPublisher.publishEvent(new AnalysisCreatedEvent(analysis.toJson()));
        return analysis.getAnalysisId();
    }

    public Analysis createAnalysis(AnalysisDTO analysisDTO) {
        logger.debug("create [{}]",analysisDTO);
        Analysis analysis = new Analysis();
        analysis.setAnalysisName(analysisDTO.getAnalysisName());
        analysis.setDescription(analysisDTO.getAnalysisDescription());
        analysis.setImage(analysisDTO.getImage());
        analysis.setLastUpdate(analysisDTO.getLastUpdate());
        analysis.setIsTemplate(analysisDTO.getIsTemplate());


        analysis = analysisRepository.save(analysis);
        eventPublisher.publishEvent(new AnalysisCreatedEvent(analysis.toJson()));
        return analysis;
    }

    public void update(AnalysisDTO analysisDTO){
        logger.debug("update [{}]", analysisDTO);
        Optional<Analysis> analysisOptional = analysisRepository.findById(analysisDTO.getRootEntityID());
        if (analysisOptional.isEmpty())
            throw new EntityNotFoundException(Analysis.class, analysisDTO.getRootEntityID());

        Analysis analysis = analysisMapper.map(analysisDTO);
        if (analysisDTO.getUniqueString() != null) {
            var numericId = new NumericId();
            numericId.setNumericId(Integer.valueOf(analysisDTO.getUniqueString().replace("ANA", "")));
            analysis.setNumericId(numericId);
        }

        analysis = analysisRepository.save(analysis);
        eventPublisher.publishEvent(new AnalysisUpdatedEvent(analysis.toJson()));
    }

    public void deleteAnalysis(UUID id) {
        logger.info("delete [{}]",id);
        Optional<Analysis> analysisOptional = analysisRepository.findById(id);
        if(analysisOptional.isEmpty()) throw new EntityNotFoundException(Analysis.class, id);
        Analysis analysis = analysisOptional.get();
        //delete Stakeholder
        stakeholderRepository.findAll().forEach(stakeholder -> {
            if(stakeholder.getAnalysis().getAnalysisId() == id){
                stakeholderRepository.delete(stakeholder);
            }
        });

        analysisRepository.deleteById(id);
        eventPublisher.publishEvent(new AnalysisDeletedEvent(analysis.toJson()));
    }
}
