package com.evatool.application.service;

import com.evatool.application.dto.*;
import com.evatool.application.service.impl.*;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.common.enums.ValueType;
import com.evatool.domain.entity.SuperEntity;
import com.evatool.domain.repository.CrudRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class CrudServiceTest<S extends SuperEntity, T extends SuperDto> extends CrudRepositoryTest<S> {

    @Test
    @Override
    public void testFindById() {
        // given
        var dto = getPersistedDto();

        // when
        var dtoFound = getService().findById(dto.getId());

        // then
        assertThat(dtoFound).isEqualTo(dto);
    }

//    @Test
//    void testFindAll() {
//        // given
//        var dto1 = getPersistedDto();
//        var dto2 = getPersistedDto();
//
//        // when
//        var dtoListFound = getService().findAll();
//
//        // then
//        assertThat(dtoListFound).isEqualTo(Arrays.asList(dto1, dto2));
//    }

    @Test
    @Override
    public void create() {
        // given
        var dto = getFloatingDto();

        // when
        var dtoCreated = getService().create(dto);
        var dtoFound = getService().findById(dtoCreated.getId());

        // then
        assertThat(dtoFound).isEqualTo(dtoCreated);
    }

    @Test
    @Override
    public void update() {
        // given
        var dto = getPersistedDto();

        // when
        changeDto(dto);
        var dtoUpdated = getService().update(dto);
        var dtoFound = getService().findById(dto.getId());

        // then
        assertThat(dtoFound).isEqualTo(dtoUpdated);
    }

    @Test
    @Override
    public void deleteById() {
        // given
        var dto = getPersistedDto();

        // when
        getService().deleteById(dto.getId());
        var dtoListFound = getService().findAll();

        // then
        assertThat(dtoListFound).isEmpty();
    }

//    @Test
//    void deleteAll() {
//        // given
//        var dto1 = getPersistedDto();
//        var dto2 = getPersistedDto();
//
//        // when
//        getService().deleteAll();
//        var dtoListFound = getService().findAll();
//
//        // then
//        assertThat(dtoListFound).isEmpty();
//    }

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
        } else if (type == UserDto.class) {
            return (T) getPersistedUserDto();
        } else if (type == ValueDto.class) {
            return (T) getPersistedValueDto();
        } else if (type == VariantDto.class) {
            return (T) getPersistedVariantDto();
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
        } else if (type == UserDto.class) {
            return (T) getFloatingUserDto();
        } else if (type == ValueDto.class) {
            return (T) getFloatingValueDto();
        } else if (type == VariantDto.class) {
            return (T) getFloatingVariantDto();
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
        } else if (type == UserDto.class) {
            var dto = (UserDto) _dto;
            dto.setExternalUserId("updated");
        } else if (type == ValueDto.class) {
            var dto = (ValueDto) _dto;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setType(ValueType.ECONOMIC);
            dto.setArchived(true);
        } else if (type == VariantDto.class) {
            var dto = (VariantDto) _dto;
            dto.setName("updated");
            dto.setDescription("updated");
            dto.setArchived(true);
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
        } else if (type == UserDto.class) {
            return userService;
        } else if (type == ValueDto.class) {
            return valueService;
        } else if (type == VariantDto.class) {
            return variantService;
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
    private UserServiceImpl userService;

    @Autowired
    private ValueServiceImpl valueService;

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

    protected UserDto getFloatingUserDto() {
        return userMapper.toDto(getFloatingUser());
    }

    protected UserDto getPersistedUserDto() {
        return userMapper.toDto(getPersistedUser());
    }

    protected ValueDto getFloatingValueDto() {
        return valueMapper.toDto(getFloatingValue());
    }

    protected ValueDto getPersistedValueDto() {
        return valueMapper.toDto(getPersistedValue());
    }

    protected VariantDto getFloatingVariantDto() {
        return variantMapper.toDto(getFloatingVariant());
    }

    protected VariantDto getPersistedVariantDto() {
        return variantMapper.toDto(getPersistedVariant());
    }
}