package com.evatool.analysis.domain.enums;


/**
 * This Enum represents the Stakeholder Level
 *
 * @author fobaidi
 * @author MHallweg
 */
public enum StakeholderLevel {

    NATURAL_PERSON("natural person"),
    ORGANIZATION("organization"),
    SOCIETY("society");

    private final String level;

    StakeholderLevel(String stakeholderLevel) {
        this.level = stakeholderLevel;
    }

    public String getStakeholderLevel() {
        return this.level;
    }
}

