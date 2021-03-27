package com.evatool.impact.domain.event.json;

import com.evatool.impact.domain.entity.Value;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    public boolean equalsEntity(Value that) {
        if (that == null) return false;
        return Objects.equals(this.id, that.getId().toString())
                && Objects.equals(this.name, that.getName())
                && Objects.equals(this.type, that.getType().toString())
                && Objects.equals(this.description, that.getDescription());
    }
}
