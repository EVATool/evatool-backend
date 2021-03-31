package com.evatool.analysis.domain.events.json;

import com.evatool.analysis.domain.model.Value;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

public class ValueJson {

    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String guiId;

    public boolean equalsEntity(Value that) {
        if (that == null) return false;
        return Objects.equals(this.id, that.getId().toString())
                && Objects.equals(this.name, that.getName())
                && Objects.equals(this.type, that.getType().toString())
                && Objects.equals(this.description, that.getDescription())
                && Objects.equals(this.guiId, that.getGuiId());
    }
}
