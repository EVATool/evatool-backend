package com.evatool.application.service.api;

import com.evatool.application.dto.AnalysisDto;

import java.util.UUID;

public interface ImportExportService {

    public static String newestImportExportVersion = "0.0.1";

    Iterable<AnalysisDto> importAnalyses(String importAnalyses);

    String exportAnalyses(Iterable<UUID> analysisIdList);

}
