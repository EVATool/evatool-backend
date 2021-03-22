package com.evatool.analysis.domain.events.json;

import com.evatool.analysis.domain.model.Value;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class ValueJson {

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
