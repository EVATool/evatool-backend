package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.model.Value;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;
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
    private Date date;
    private Boolean isTemplate;
    private String uniqueString;

}
