package com.evatool.common.exception.functional.http404;

import com.evatool.common.util.FunctionalErrorCodesUtil;
import com.evatool.domain.entity.Analysis;

import java.util.UUID;

public class AnalysisNotFoundException extends EntityNotFoundException {
    public AnalysisNotFoundException(UUID id) {
        super(Analysis.class.getSimpleName(), id, FunctionalErrorCodesUtil.ANALYSIS_NOT_FOUND, new EntityNotFoundTag(id));
    }
}
