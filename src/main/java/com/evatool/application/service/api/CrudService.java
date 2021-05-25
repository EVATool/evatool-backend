package com.evatool.application.service.api;

import com.evatool.application.dto.SuperDto;

import java.util.List;
import java.util.UUID;

public interface CrudService<T extends SuperDto> {

    T findById(UUID id);

    Iterable<T> findAll();

    T create(T dto);

    T update(T dto);

    void deleteById(UUID id);

    void deleteAll();
}
