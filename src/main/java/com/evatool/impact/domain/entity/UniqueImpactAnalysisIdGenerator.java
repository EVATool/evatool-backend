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

        System.out.println(object);
        var persistor = session.getEntityPersister(NumericId.class.getSimpleName(), object);
        System.out.println(persistor);

        var entities = session.createQuery("select a from IMP_NUMERIC_ID a", NumericId.class).getResultList();
        System.out.println(entities);


        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
