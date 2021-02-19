package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.Impact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImpactRepository extends CrudRepository<Impact, UUID> {

}