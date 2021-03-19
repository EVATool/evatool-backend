package com.evatool.impact.domain.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Serializable;

@Configurable
public class UniqueImpactAnalysisIdGenerator implements IdentifierGenerator {

    private static final Logger logger = LoggerFactory.getLogger(UniqueImpactAnalysisIdGenerator.class);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        logger.debug("Generating new AnalysisImpactId");

        var max = session.createQuery("select MAX(a.numericId) from IMP_NUMERIC_ID a", Integer.class).getResultList().get(0);

        if (max == null) { // First entity.
            return 1;
        } else {
            return max + 1;
        }
    }
}
