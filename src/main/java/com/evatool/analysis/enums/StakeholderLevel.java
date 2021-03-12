package com.evatool.analysis.enums;


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

    private String stakeholderLevel;

    StakeholderLevel(String stakeholderLevel) {
        this.stakeholderLevel = stakeholderLevel;
    }

    public String getStakeholderLevel() {
        return this.stakeholderLevel;
    }
}

