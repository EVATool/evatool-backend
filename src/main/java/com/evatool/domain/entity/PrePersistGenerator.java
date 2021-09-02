package com.evatool.domain.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class PrePersistGenerator extends UUIDGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PrePersistGenerator.class);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        logger.trace("Generating new SuperEntityUuid");

        if (PrefixIdEntity.class.isAssignableFrom(object.getClass())) {
            logger.debug("PrefixIdEntity is assignable from object");
            var child = (PrefixIdEntity) object;
            var parentId = child.getParentId();
            var parentClass = child.getParentClass();
            var childClass = child.getChildClass();

            // Check existing children for given parent.
            var selectQuery = session.createQuery(
                    "select a.counter from parent_child_counter a where a.parentId=?1 and a.parentClass=?2 and a.childClass=?3", Integer.class);
            selectQuery.setParameter(1, parentId);
            selectQuery.setParameter(2, parentClass);
            selectQuery.setParameter(3, childClass);
            var selectQueryResult = selectQuery.getResultList();
            var counter = selectQueryResult.isEmpty() ? 0 : selectQueryResult.get(0);

            if (counter == 0) {
                logger.debug("First child of that parent");
                counter = 1;
                var insertQuery = session.createNativeQuery(
                        "insert into parent_child_counter (parent_id, parent_class, child_class, counter) values (?1, ?2, ?3, ?4)");
                insertQuery.setParameter(1, parentId);
                insertQuery.setParameter(2, parentClass);
                insertQuery.setParameter(3, childClass);
                insertQuery.setParameter(4, counter);
                insertQuery.executeUpdate();
            } else {
                logger.debug("Children with that parent already exist");
                counter += 1;
                var updateQuery = session.createQuery(
                        "update parent_child_counter set counter=?1 where parentId=?2 and parentClass=?3 and childClass=?4");
                updateQuery.setParameter(1, counter);
                updateQuery.setParameter(2, parentId);
                updateQuery.setParameter(3, parentClass);
                updateQuery.setParameter(4, childClass);
                updateQuery.executeUpdate();
            }

            child.setSequenceId(counter);
        }

        return super.generate(session, object);
    }
}
