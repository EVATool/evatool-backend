package com.evatool.domain.repository;

import com.evatool.domain.entity.Analysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnalysisRepository extends CrudRepository<Analysis, UUID> {

}
