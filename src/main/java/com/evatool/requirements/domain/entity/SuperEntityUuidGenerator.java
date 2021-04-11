package com.evatool.requirements.domain.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class SuperEntityUuidGenerator extends UUIDGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SuperEntityUuidGenerator.class);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        logger.debug("Generating new SuperEntityUuid");

        if (object.getClass() == Requirement.class) {
            var requirement = (Requirement) object;

            // Check existing requirement for given analysis.
            var requirementPerAnalysisQuery = session.createQuery("select a.requirmentsPerAnalysis from req_requirements_per_analysis a where a.analysisId=?1", Integer.class);
            requirementPerAnalysisQuery.setParameter(1, requirement.getRequirementsAnalysis().getAnalysisId().toString());
            var requirementPerAnalysisResult = requirementPerAnalysisQuery.getResultList();
            var requirementPerAnalysis = requirementPerAnalysisResult.isEmpty() ? 0 : requirementPerAnalysisResult.get(0);

            if (requirementPerAnalysis == 0) { // First impact for that analysis
                requirementPerAnalysis = 1;
                var insertQuery = session.createNativeQuery("insert into req_requirements_per_analysis (ANALYSIS_ID, REQUIRMENTS_PER_ANALYSIS) values (?1, ?2)");
                insertQuery.setParameter(1, requirement.getRequirementsAnalysis().getAnalysisId().toString());
                insertQuery.setParameter(2, requirementPerAnalysis);
                insertQuery.executeUpdate();
            } else {
                requirementPerAnalysis += 1;
                var updateQuery = session.createQuery("update req_requirements_per_analysis set REQUIRMENTS_PER_ANALYSIS=?1 where ANALYSIS_ID=?2");
                updateQuery.setParameter(1, requirementPerAnalysis);
                updateQuery.setParameter(2, requirement.getRequirementsAnalysis().getAnalysisId().toString());
                updateQuery.executeUpdate();
            }

            requirement.setNumericId(requirementPerAnalysis);
        }

        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
