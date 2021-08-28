package com.evatool.common.exception.functional.http404;

import com.evatool.common.exception.functional.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public abstract class EntityNotFoundException extends NotFoundException {

    protected EntityNotFoundException(String message, int functionalErrorCode, Tag tag) {
        super(message, functionalErrorCode, tag);
    }

    @Getter
    @RequiredArgsConstructor
    public static class EntityNotFoundTag extends Tag {
        private final UUID id;
    }
}
