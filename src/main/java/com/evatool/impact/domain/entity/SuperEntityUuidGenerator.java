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
            var impactsPerAnalysisQuery = session.createQuery("select a.impactsPerAnalysis from IMP_IMPACTS_PER_ANALYSIS a where a.analysisId=?1", Integer.class);
            System.out.println("Select query: " + impactsPerAnalysisQuery.getQueryString());
            impactsPerAnalysisQuery.setParameter(1, impact.getAnalysis().getId().toString());
            var impactsPerAnalysisResult = impactsPerAnalysisQuery.getResultList();
            var impactsPerAnalysis = impactsPerAnalysisResult.isEmpty() ? 0 : impactsPerAnalysisResult.get(0);
            System.out.println("Existing impacts: " + impactsPerAnalysis);

            if (impactsPerAnalysis == 0) { // First impact for that analysis
                var insertQuery = session.createNativeQuery("insert into IMP_IMPACTS_PER_ANALYSIS (ANALYSIS_ID, IMPACTS_PER_ANALYSIS) values (?1, ?2)");
                System.out.println("Insert query: " + insertQuery.getQueryString());
                insertQuery.setParameter(1, impact.getAnalysis().getId().toString());
                insertQuery.setParameter(2, 1);
                var insertResult = insertQuery.executeUpdate();
                System.out.println("Insert Result: " + insertResult);

                System.out.println(session.createQuery("select a.impactsPerAnalysis from IMP_IMPACTS_PER_ANALYSIS a where a.analysisId='" + impact.getAnalysis().getId() + "'", Integer.class).getResultList());
            } else {
                var updateQuery = session.createQuery("update impactsPerAnalysis set IMPACTS_PER_ANALYSIS=?1 where analysisId=?2");
                System.out.println("Update query: " + updateQuery.getQueryString());
                updateQuery.setParameter(1, impactsPerAnalysis + 1);
                updateQuery.setParameter(2, impact.getAnalysis().getId());
                var updateResult = updateQuery.executeUpdate();
                System.out.println("Update Result: " + updateResult);
            }

            var query = session.createQuery("select MAX(a.numericId) from IMP_IMPACT a where a.analysis.id=(?1)", Integer.class);
            query.setParameter(1, impact.getAnalysis().getId());
            var max = query.getResultList().get(0);
            if (max == null) {
                impact.setNumericId(1);
            } else {
                impact.setNumericId(max + 1);
            }
        }

        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
