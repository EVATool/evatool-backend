package com.evatool.application.service.api;

import java.util.UUID;

public interface ImportExportService {

    public static String newestImportExportVersion = "0.0.1";

    void importAnalyses(String importAnalyses);

    String exportAnalyses(Iterable<UUID> analysisIdList);

}
