package com.evatool.application.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ImportExportAnalysesDto {

    private String importExportVersion;

    private ImportExportAnalysisDto[] analyses;

    public ImportExportAnalysesDto(String importExportVersion, ImportExportAnalysisDto[] importExportAnalysisJson) {
        this.importExportVersion = importExportVersion;
        this.analyses = importExportAnalysisJson;
    }
}
