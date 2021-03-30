package com.evatool.impact.domain.event.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ImpactValueJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String description;
}
