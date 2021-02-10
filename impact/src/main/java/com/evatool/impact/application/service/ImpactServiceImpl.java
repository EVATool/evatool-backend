package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.mapper.ImpactMapper;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.common.exception.PropertyViolationException;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.repository.DimensionRepository;
import com.evatool.impact.domain.repository.ImpactRepository;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpactServiceImpl implements ImpactService {

    @Autowired
    private ImpactRepository impactRepository;

    @Autowired
    private ImpactStakeholderRepository impactStakeholderRepository;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Override
    public ImpactDto findImpactById(String id) throws EntityNotFoundException {
        if (id == null) {
            throw new EntityNotFoundException(Impact.class, "null");
        }
        var impact = impactRepository.findById(id);
        if (impact.isEmpty()) {
            throw new EntityNotFoundException(Impact.class, id);
        }
        return ImpactMapper.toDto(impact.get());
    }

    @Override
    public List<ImpactDto> getAllImpacts() {
        var impacts = impactRepository.findAll();
        var impactDtoList = new ArrayList<ImpactDto>();
        impacts.forEach(impact -> impactDtoList.add(ImpactMapper.toDto(impact)));
        return impactDtoList;
    }

    @Override
    public ImpactDto createImpact(ImpactDto impactDto) {
        if (impactDto.getId() != null) {
            throw new PropertyViolationException(String.format("A newly created '%s' must have null id.", Impact.class.getSimpleName()));
        }
        var impact = ImpactMapper.fromDto(impactDto, dimensionRepository, impactStakeholderRepository);
        return ImpactMapper.toDto(impactRepository.save(impact));
    }

    @Override
    public ImpactDto updateImpact(ImpactDto impactDto) throws EntityNotFoundException {
        this.findImpactById(impactDto.getId());
        var impact = ImpactMapper.fromDto(impactDto, dimensionRepository, impactStakeholderRepository);
        return ImpactMapper.toDto(impactRepository.save(impact));
    }

    @Override
    public void deleteImpactById(String id) {
        impactRepository.deleteById(id);
    }
}
