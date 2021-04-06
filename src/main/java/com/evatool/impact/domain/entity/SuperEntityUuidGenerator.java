package com.evatool.impact.domain.entity;

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

        if (object.getClass() == Impact.class) {
            var impact = (Impact) object;

            // Check existing impacts for given analysis.
            var impactsPerAnalysisQuery = session.createQuery("select a.impactsPerAnalysis from imp_impacts_per_analysis a where a.analysisId=?1", Integer.class);
            impactsPerAnalysisQuery.setParameter(1, impact.getAnalysis().getId().toString());
            var impactsPerAnalysisResult = impactsPerAnalysisQuery.getResultList();
            var impactsPerAnalysis = impactsPerAnalysisResult.isEmpty() ? 0 : impactsPerAnalysisResult.get(0);

            if (impactsPerAnalysis == 0) { // First impact for that analysis
                impactsPerAnalysis = 1;
                var insertQuery = session.createNativeQuery("insert into imp_impacts_per_analysis (analysis_id, impacts_per_analysis) values (?1, ?2)");
                insertQuery.setParameter(1, impact.getAnalysis().getId().toString());
                insertQuery.setParameter(2, impactsPerAnalysis);
                insertQuery.executeUpdate();
            } else {
                impactsPerAnalysis += 1;
                var updateQuery = session.createQuery("update imp_impacts_per_analysis set impactsPerAnalysis=?1 where analysisId=?2");
                updateQuery.setParameter(1, impactsPerAnalysis);
                updateQuery.setParameter(2, impact.getAnalysis().getId().toString());
                updateQuery.executeUpdate();
            }

            impact.setNumericId(impactsPerAnalysis);
        }

        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
