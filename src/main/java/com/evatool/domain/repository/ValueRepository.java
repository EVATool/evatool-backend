package com.evatool.domain.repository;

import com.evatool.domain.entity.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValueRepository extends CrudRepository<Value, UUID>, FindByAnalysisRepository<Value> {

}
