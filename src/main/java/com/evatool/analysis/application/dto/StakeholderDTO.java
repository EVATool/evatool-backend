package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.google.gson.Gson;
import lombok.Getter;

import java.util.*;

@Getter
public class StakeholderDTO {
    private UUID rootEntityID;
    private String stakeholderName;
    private StakeholderLevel stakeholderLevel;
    private Integer priority;
    private Float value;
    private String guiId;

    public void setRootEntityID(UUID rootEntityID) {
        this.rootEntityID = rootEntityID;
    }

    public void setStakeholderName(String stakeholderName) {
        if (stakeholderName == null) {
            throw new IllegalArgumentException("stakeholder Name cannot be null.");
        }
        this.stakeholderName = stakeholderName;
    }

    public void setStakeholderLevel(StakeholderLevel stakeholderLevel) {
        if (stakeholderLevel == null) {
            throw new IllegalArgumentException("stakeholder Level description cannot be null.");
        }
        this.stakeholderLevel = stakeholderLevel;
    }

    public void setPriority(Integer priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        this.priority = priority;
    }

    public void setValue(Float value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        this.value = value;
    }

    public void setGuiId(String guiId) {
        if (guiId == null) {
            throw new IllegalArgumentException("GuiId cannot be null.");
        }
        this.guiId = guiId;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
