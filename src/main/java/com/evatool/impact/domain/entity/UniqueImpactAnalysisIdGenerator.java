package com.evatool.impact.domain.entity;

import com.evatool.impact.domain.repository.ImpactRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Serializable;

@Configurable
public class UniqueImpactAnalysisIdGenerator extends SequenceStyleGenerator {
    private static final Logger logger = LoggerFactory.getLogger(UniqueImpactAnalysisIdGenerator.class);

    //@Autowired
    ImpactRepository impactRepository;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        logger.debug("Generating new AnalysisImpactId");

        var entities = session.createQuery("select a.numericId from IMP_NUMERIC_ID a", Integer.class).getResultList();
        System.out.println(entities);

        var max = session.createQuery("select MAX(a.numericId) from IMP_NUMERIC_ID a", Integer.class).getResultList();
        System.out.println(max);

        if (max.get(0) == null) { // First entity.
            return 1;
        } else {
            return max.get(0) + 1;
        }
    }
}
