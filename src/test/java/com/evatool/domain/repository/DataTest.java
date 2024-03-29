package com.evatool.domain.repository;

import com.evatool.application.dto.*;
import com.evatool.application.mapper.*;
import com.evatool.application.service.impl.*;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.ParameterizedType;

@SpringBootTest
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
        } else if (type == Value.class) {
            return valueRepository;
        } else if (type == Variant.class) {
            return variantRepository;
        } else if (type == VariantType.class) {
            return variantTypeRepository;
        } else if (type == ValueType.class) {
            return valueTypeRepository;
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
        } else if (type == ValueDto.class) {
            return valueMapper;
        } else if (type == VariantDto.class) {
            return variantMapper;
        } else if (type == VariantTypeDto.class) {
            return variantTypeMapper;
        } else if (type == ValueTypeDto.class) {
            return valueTypeMapper;
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
    protected ValueTypeMapper valueTypeMapper;

    @Autowired
    protected ValueMapper valueMapper;

    @Autowired
    protected VariantTypeMapper variantTypeMapper;

    @Autowired
    protected VariantMapper variantMapper;

    public T getPersistedDto() {
        var type = getDtoClass();
        if (type == AnalysisDto.class) {
            return (T) getPersistedAnalysisDto();
        } else if (type == ImpactDto.class) {
            return (T) getPersistedImpactDto();
        } else if (type == RequirementDto.class) {
            return (T) getPersistedRequirementDto();
        } else if (type == RequirementDeltaDto.class) {
            return (T) getPersistedRequirementDeltaDto();
        } else if (type == StakeholderDto.class) {
            return (T) getPersistedStakeholderDto();
        } else if (type == ValueDto.class) {
            return (T) getPersistedValueDto();
        } else if (type == VariantDto.class) {
            return (T) getPersistedVariantDto();
        } else if (type == VariantTypeDto.class) {
            return (T) getPersistedVariantTypeDto();
        } else if (type == ValueTypeDto.class) {
            return (T) getPersistedValueTypeDto();
        } else {
            throw new IllegalArgumentException("No method found for type " + type.getSimpleName());
        }
    }

    public T getFloatingDto() {
        var type = getDtoClass();
        if (type == AnalysisDto.class) {
            return (T) getFloatingAnalysisDto();
        } else if (type == ImpactDto.class) {
            return (T) getFloatingImpactDto();
        } else if (type == RequirementDto.class) {
            return (T) getFloatingRequirementDto();
        } else if (type == RequirementDeltaDto.class) {
            return (T) getFloatingRequirementDeltaDto();
        } else if (type == StakeholderDto.class) {
            return (T) getFloatingStakeholderDto();
        } else if (type == ValueDto.class) {
            return (T) getFloatingValueDto();
        } else if (type == VariantDto.class) {
            return (T) getFloatingVariantDto();
        } else if (type == VariantTypeDto.class) {
            return (T) getFloatingVariantTypeDto();
        } else if (type == ValueTypeDto.class) {
            return (T) getFloatingValueTypeDto();
        } else {
            throw new IllegalArgumentException("No method found for type " + type.getSimpleName());
        }
    }

    public void changeEntity(S _entity) {
        var type = getEntityClass();
        if (type == Analysis.class) {
            var dto = (Analysis) _entity;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setImageUrl("updated");
            dto.setIsTemplate(true);
        } else if (type == Impact.class) {
            var dto = (Impact) _entity;
            dto.setMerit(0.6f);
            dto.setDescription("updated description");
        } else if (type == Requirement.class) {
            var dto = (Requirement) _entity;
            dto.setDescription("updated");
        } else if (type == RequirementDelta.class) {
            var dto = (RequirementDelta) _entity;
            dto.setOverwriteMerit(0.3f);
        } else if (type == Stakeholder.class) {
            var dto = (Stakeholder) _entity;
            dto.setName("updated");
            dto.setPriority(StakeholderPriority.TWO);
            dto.setLevel(StakeholderLevel.ORGANIZATION);
        } else if (type == Value.class) {
            var dto = (Value) _entity;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setArchived(true);
        } else if (type == Variant.class) {
            var dto = (Variant) _entity;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setArchived(true);
        } else if (type == VariantType.class) {
            var dto = (VariantType) _entity;
            dto.setName("updated");
            dto.setDescription("updated");
        } else if (type == ValueType.class) {
            var dto = (ValueType) _entity;
            dto.setName("updated");
            dto.setDescription("updated");
        } else {
            throw new IllegalArgumentException("No method found for type " + type.getSimpleName());
        }
    }

    public void changeDto(T _dto) {
        var type = getDtoClass();
        if (type == AnalysisDto.class) {
            var dto = (AnalysisDto) _dto;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setImageUrl("updated");
            dto.setIsTemplate(true);
        } else if (type == ImpactDto.class) {
            var dto = (ImpactDto) _dto;
            dto.setMerit(0.6f);
            dto.setDescription("updated description");
        } else if (type == RequirementDto.class) {
            var dto = (RequirementDto) _dto;
            dto.setDescription("updated");
        } else if (type == RequirementDeltaDto.class) {
            var dto = (RequirementDeltaDto) _dto;
            dto.setOverwriteMerit(0.3f);
        } else if (type == StakeholderDto.class) {
            var dto = (StakeholderDto) _dto;
            dto.setName("updated");
            dto.setPriority(StakeholderPriority.TWO);
            dto.setLevel(StakeholderLevel.ORGANIZATION);
        } else if (type == ValueDto.class) {
            var dto = (ValueDto) _dto;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setArchived(true);
        } else if (type == VariantDto.class) {
            var dto = (VariantDto) _dto;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setArchived(true);
        } else if (type == VariantTypeDto.class) {
            var dto = (VariantTypeDto) _dto;
            dto.setName("updated");
            dto.setDescription("updated");
        } else if (type == ValueTypeDto.class) {
            var dto = (ValueTypeDto) _dto;
            dto.setName("updated");
            dto.setDescription("updated");
        } else {
            throw new IllegalArgumentException("No method found for type " + type.getSimpleName());
        }
    }

    public CrudServiceImpl getService() {
        var type = getDtoClass();
        if (type == AnalysisDto.class) {
            return analysisService;
        } else if (type == ImpactDto.class) {
            return impactService;
        } else if (type == RequirementDto.class) {
            return requirementService;
        } else if (type == RequirementDeltaDto.class) {
            return requirementDeltaService;
        } else if (type == StakeholderDto.class) {
            return stakeholderService;
        } else if (type == ValueDto.class) {
            return valueService;
        } else if (type == VariantDto.class) {
            return variantService;
        } else if (type == VariantTypeDto.class) {
            return variantTypeService;
        } else if (type == ValueTypeDto.class) {
            return valueTypeService;
        } else {
            throw new IllegalArgumentException("No service found for type " + type.getSimpleName());
        }
    }

    @Autowired
    private AnalysisServiceImpl analysisService;

    @Autowired
    private ImpactServiceImpl impactService;

    @Autowired
    private RequirementServiceImpl requirementService;

    @Autowired
    private RequirementDeltaServiceImpl requirementDeltaService;

    @Autowired
    private StakeholderServiceImpl stakeholderService;

    @Autowired
    private ValueTypeServiceImpl valueTypeService;

    @Autowired
    private ValueServiceImpl valueService;

    @Autowired
    private VariantTypeServiceImpl variantTypeService;

    @Autowired
    private VariantServiceImpl variantService;

    protected AnalysisDto getFloatingAnalysisDto() {
        return analysisMapper.toDto(getFloatingAnalysis());
    }

    protected AnalysisDto getPersistedAnalysisDto() {
        return analysisMapper.toDto(getPersistedAnalysis());
    }

    protected ImpactDto getFloatingImpactDto() {
        return impactMapper.toDto(getFloatingImpact());
    }

    protected ImpactDto getPersistedImpactDto() {
        return impactMapper.toDto(getPersistedImpact());
    }

    protected RequirementDto getFloatingRequirementDto() {
        return requirementMapper.toDto(getFloatingRequirement());
    }

    protected RequirementDto getPersistedRequirementDto() {
        return requirementMapper.toDto(getPersistedRequirement());
    }

    protected RequirementDeltaDto getFloatingRequirementDeltaDto() {
        return requirementDeltaMapper.toDto(getFloatingRequirementDelta());
    }

    protected RequirementDeltaDto getPersistedRequirementDeltaDto() {
        return requirementDeltaMapper.toDto(getPersistedRequirementDelta());
    }

    protected StakeholderDto getFloatingStakeholderDto() {
        return stakeholderMapper.toDto(getFloatingStakeholder());
    }

    protected StakeholderDto getPersistedStakeholderDto() {
        return stakeholderMapper.toDto(getPersistedStakeholder());
    }

    protected ValueTypeDto getFloatingValueTypeDto() {
        return valueTypeMapper.toDto(getFloatingValueType());
    }

    protected ValueTypeDto getPersistedValueTypeDto() {
        return valueTypeMapper.toDto(getPersistedValueType());
    }

    protected ValueDto getFloatingValueDto() {
        return valueMapper.toDto(getFloatingValue());
    }

    protected ValueDto getPersistedValueDto() {
        return valueMapper.toDto(getPersistedValue());
    }

    protected VariantTypeDto getFloatingVariantTypeDto() {
        return variantTypeMapper.toDto(getFloatingVariantType());
    }

    protected VariantTypeDto getPersistedVariantTypeDto() {
        return variantTypeMapper.toDto(getPersistedVariantType());
    }

    protected VariantDto getFloatingVariantDto() {
        return variantMapper.toDto(getFloatingVariant());
    }

    protected VariantDto getPersistedVariantDto() {
        return variantMapper.toDto(getPersistedVariant());
    }
}
