package com.evatool.application.service.api;

import java.util.UUID;

public interface ImportExportService {

    void importAnalyses(String importAnalyses);

    String exportAnalyses(Iterable<UUID> analysisIdList);

}
