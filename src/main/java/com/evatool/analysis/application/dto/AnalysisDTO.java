package com.evatool.analysis.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class AnalysisDTO {

    private UUID rootEntityID;
    private String analysisName;
    private String analysisDescription;

}
