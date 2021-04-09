package com.evatool.analysis.domain.repository;

import com.evatool.analysis.domain.enums.ValueType;
import com.evatool.analysis.domain.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ValueRepository extends JpaRepository<Value, UUID> {

    List<Value> findAllByType(ValueType type);

    List<Value> findAllByAnalysisAnalysisId(UUID analysisId);

}
