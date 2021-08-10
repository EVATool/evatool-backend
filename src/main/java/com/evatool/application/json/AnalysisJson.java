package com.evatool.application.json;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class AnalysisJson extends SuperJson {
    private String name;
    private String description;
    private boolean isTemplate;
    private String imageUrl;

    public AnalysisJson(String name, String description, boolean isTemplate, String imageUrl) {
        this.name = name;
        this.description = description;
        this.isTemplate = isTemplate;
        this.imageUrl = imageUrl;
    }
}
