package com.evatool.domain.repository;

import com.evatool.domain.entity.Impact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImpactRepository extends CrudRepository<Impact, UUID>, FindByAnalysisRepository<Impact> {

    Iterable<Impact> findAllByValueId(UUID valueId);

}
