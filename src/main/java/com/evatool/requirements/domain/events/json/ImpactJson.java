package com.evatool.requirements.domain.events.json;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;


public class ImpactJson {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private double value;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String dimensionId;

    public static ImpactJson fromJson(String json){
        return  new Gson().fromJson(json, ImpactJson.class);

    }

    @Override
    public String toString() {
        return "ImpactJson{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", dimensionId='" + dimensionId + '\'' +
                '}';
    }
}
