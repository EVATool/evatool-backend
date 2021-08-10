package com.evatool.application.service.impl;

import com.evatool.application.service.api.ImportExportService;
import com.evatool.domain.entity.Analysis;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImportExportServiceImpl implements ImportExportService {

    // TODO repos...

    // TODO return ImportReport object with information (success or not, file version and current version..., error causes..., default values used...)
    @SneakyThrows // TODO remove...
    @Override
    @Transactional
    public void importAnalyses(String json) {


        var mapper = new ObjectMapper();
        var analysis = mapper.readValue(json, Analysis.class);

    }

    @SneakyThrows // TODO remove...
    @Override
    public String exportAnalyses() {

        var analysis = new Analysis("test", "lul", false);
        var mapper = new ObjectMapper();
        var analysisJson = mapper.writeValueAsString(analysis);

        return analysisJson;
    }

    private void importAnalysis() {

    }

    private String exportAnalysis() {
        return null;
    }
}
