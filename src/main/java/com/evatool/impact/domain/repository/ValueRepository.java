package com.evatool.impact.domain.repository;

import com.evatool.impact.common.ValueType;
import com.evatool.impact.domain.entity.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ValueRepository extends CrudRepository<Value, UUID> {

    List<Value> findAllByType(ValueType type);

}
