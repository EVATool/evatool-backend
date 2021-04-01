package com.evatool.impact.domain.event.json;

import com.evatool.impact.domain.entity.ImpactStakeholder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@ToString
public class ImpactStakeholderJson {

    @Getter
    @Setter
    private UUID stakeholderId;

    @Getter
    @Setter
    private String stakeholderName;

    @Getter
    @Setter
    private String stakeholderLevel;

    public boolean equalsEntity(ImpactStakeholder that) {
        if (that == null) return false;
        return Objects.equals(this.stakeholderId, that.getId().toString())
                && Objects.equals(this.stakeholderName, that.getName())
                && Objects.equals(this.stakeholderLevel, that.getLevel());
    }
}
