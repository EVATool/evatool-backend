package com.evatool.analysis.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AnalysisDTO {

    private UUID rootEntityID;
    private String analysisName;
    private String analysisDescription;
    private String image;
    private Date lastUpdate;
    private Boolean isTemplate;
    private String uniqueString;
}
