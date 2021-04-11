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
            var impact = (Requirement) object;

            // Check existing impacts for given analysis.
            var impactsPerAnalysisQuery = session.createQuery("select a.requirmentsPerAnalysis from REQ_REQUIREMENTS_PER_ANALYSIS a where a.analysisId=?1", Integer.class);
            impactsPerAnalysisQuery.setParameter(1, impact.getRequirementsAnalysis().getAnalysisId().toString());
            var impactsPerAnalysisResult = impactsPerAnalysisQuery.getResultList();
            var impactsPerAnalysis = impactsPerAnalysisResult.isEmpty() ? 0 : impactsPerAnalysisResult.get(0);

            if (impactsPerAnalysis == 0) { // First impact for that analysis
                impactsPerAnalysis = 1;
                var insertQuery = session.createNativeQuery("insert into REQ_REQUIREMENTS_PER_ANALYSIS (ANALYSIS_ID, REQUIRMENTS_PER_ANALYSIS) values (?1, ?2)");
                insertQuery.setParameter(1, impact.getRequirementsAnalysis().getAnalysisId().toString());
                insertQuery.setParameter(2, impactsPerAnalysis);
                insertQuery.executeUpdate();
            } else {
                impactsPerAnalysis += 1;
                var updateQuery = session.createQuery("update REQ_REQUIREMENTS_PER_ANALYSIS set REQUIRMENTS_PER_ANALYSIS=?1 where ANALYSIS_ID=?2");
                updateQuery.setParameter(1, impactsPerAnalysis);
                updateQuery.setParameter(2, impact.getRequirementsAnalysis().getAnalysisId().toString());
                updateQuery.executeUpdate();
            }

            impact.setNumericId(impactsPerAnalysis);
        }

        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
