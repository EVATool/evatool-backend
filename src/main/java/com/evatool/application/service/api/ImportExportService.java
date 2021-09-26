package com.evatool.application.service.api;

import com.evatool.application.dto.AnalysisDto;

import java.util.UUID;

public interface ImportExportService {

    String NEWEST_IMPORT_EXPORT_VERSION = "0.0.2";

    Iterable<AnalysisDto> importAnalyses(String importAnalyses);

    String exportAnalyses(Iterable<UUID> analysisIdList);

}
