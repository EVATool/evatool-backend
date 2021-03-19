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
            //var max = session.createQuery("select MAX(a.numericId) from IMP_IMPACT a where a.id='" + impact.getAnalysis().getId()+"'", Integer.class).getResultList().get(0);
            var query = session.createQuery("select MAX(a.numericId) from IMP_IMPACT a where a.analysis.id=(?1)", Integer.class);
            query.setParameter(1, impact.getAnalysis().getId());
            var max = query.getResultList().get(0);
            System.out.println("MAXIMUM NUMERIC ID FOR ANALYSIS ID=" + impact.getAnalysis().getId() + ": " + max);
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
