package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.NumericImpactId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumericIdRepository extends CrudRepository<NumericImpactId, Integer> {
}
