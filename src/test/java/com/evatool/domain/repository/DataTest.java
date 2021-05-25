package com.evatool.domain.repository;

import com.evatool.application.dto.*;
import com.evatool.application.mapper.*;
import com.evatool.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.ParameterizedType;

public abstract class DataTest<S extends SuperEntity, T extends SuperDto> extends SuperEntityTest {

    public Class<T> getDtoClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public CrudRepository getRepository(){
        var type = getEntityClass();
        if (type == Analysis.class) {
            return analysisRepository;
        } else if (type == Impact.class) {
            return impactRepository;
        } else if (type == Requirement.class) {
            return requirementRepository;
        } else if (type == RequirementDelta.class) {
            return requirementDeltaRepository;
        } else if (type == Stakeholder.class) {
            return stakeholderRepository;
        } else if (type == User.class) {
            return userRepository;
        } else if (type == Value.class) {
            return valueRepository;
        } else if (type == Variant.class) {
            return variantRepository;
        } else {
            throw new IllegalArgumentException("No repository found for type " + type.getSimpleName());
        }
    }

    protected SuperMapper getMapper() {
        var type = getDtoClass();
        if (type == AnalysisDto.class) {
            return analysisMapper;
        } else if (type == ImpactDto.class) {
            return impactMapper;
        } else if (type == RequirementDto.class) {
            return requirementMapper;
        } else if (type == RequirementDeltaDto.class) {
            return requirementDeltaMapper;
        } else if (type == StakeholderDto.class) {
            return stakeholderMapper;
        } else if (type == UserDto.class) {
            return userMapper;
        } else if (type == ValueDto.class) {
            return valueMapper;
        } else if (type == VariantDto.class) {
            return variantMapper;
        } else {
            throw new IllegalArgumentException("No service found for type " + type.getSimpleName());
        }
    }

    @Autowired
    protected AnalysisMapper analysisMapper;

    @Autowired
    protected ImpactMapper impactMapper;

    @Autowired
    protected RequirementMapper requirementMapper;

    @Autowired
    protected RequirementDeltaMapper requirementDeltaMapper;

    @Autowired
    protected StakeholderMapper stakeholderMapper;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected ValueMapper valueMapper;

    @Autowired
    protected VariantMapper variantMapper;

}
