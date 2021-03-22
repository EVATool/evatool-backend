package com.evatool.analysis.domain.repository;

import com.evatool.analysis.common.error.ValueType;
import com.evatool.analysis.domain.model.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ValueRepository extends CrudRepository<Value, UUID> {

    List<Value> findAllByType(ValueType type);

}
