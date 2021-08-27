package com.evatool.application.mapper;

import com.evatool.application.dto.PrefixIdDto;
import com.evatool.common.exception.prevent.http422.PropertyIsInvalidException;
import com.evatool.domain.entity.PrefixIdEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PrefixIdMapper<S extends PrefixIdEntity, T extends PrefixIdDto> extends SuperMapper<S, T> {

    private static final Logger logger = LoggerFactory.getLogger(PrefixIdMapper.class);

    @Override
    public void amendToDto(S entity, T dto) {
        logger.debug("Amend To Dto");
        dto.setPrefixSequenceId(entity.getPrefixSequenceId());
        super.amendToDto(entity, dto);
    }

    @Override
    public void amendFromDto(S entity, T dto) {
        logger.debug("Amend From Dto");
        if (dto.getPrefixSequenceId() != null) {
            try {
                entity.setSequenceId(Integer.valueOf(dto.getPrefixSequenceId().replaceAll("[^\\d.]", "")));
            } catch (NumberFormatException ex) {
                throw new PropertyIsInvalidException(String.format("Error extracting numeric value from string after removing letters [input: %s]", dto.getPrefixSequenceId()));
            }
        }
        super.amendFromDto(entity, dto);
    }
}
