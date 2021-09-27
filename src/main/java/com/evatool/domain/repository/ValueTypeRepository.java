package com.evatool.domain.repository;

import com.evatool.domain.entity.ValueType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValueTypeRepository extends CrudRepository<ValueType, UUID>, FindByAnalysisRepository<ValueType> {
}
