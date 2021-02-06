package com.evatool.impact.application.controller.uri;

import com.evatool.impact.common.ModuleSettings;

public class StakeholderRestUri {
    public static final String IMPACT_STAKEHOLDER_REST_CONTROLLER_MAPPING = ModuleSettings.BASE_URI;

    public static final String GET_STAKEHOLDER = "stakeholder";
    public static final String GET_STAKEHOLDERS = "stakeholders";
    public static final String POST_STAKEHOLDER = "stakeholder";
    public static final String PUT_STAKEHOLDER = "stakeholder";
    public static final String DELETE_STAKEHOLDER = "stakeholder";

    public static final String GET_STAKEHOLDER_MAPPING = "/" + GET_STAKEHOLDER + "/{id}";
    public static final String GET_STAKEHOLDERS_MAPPING = "/" + GET_STAKEHOLDERS;
    public static final String POST_STAKEHOLDER_MAPPING = "/" + POST_STAKEHOLDER;
    public static final String PUT_STAKEHOLDER_MAPPING = "/" + PUT_STAKEHOLDER + "/{id}";
    public static final String DELETE_STAKEHOLDER_MAPPING = "/" + DELETE_STAKEHOLDER + "/{id}";

    public static String buildGetStakeholderUri(String id) {
        return IMPACT_STAKEHOLDER_REST_CONTROLLER_MAPPING + GET_STAKEHOLDER_MAPPING.replace("{id}", id);
    }

    public static String buildGetStakeholdersUri() {
        return IMPACT_STAKEHOLDER_REST_CONTROLLER_MAPPING + GET_STAKEHOLDERS_MAPPING;
    }

    public static String buildPostStakeholderUri() {
        return IMPACT_STAKEHOLDER_REST_CONTROLLER_MAPPING + POST_STAKEHOLDER_MAPPING;
    }

    public static String buildPutStakeholderUri(String id) {
        return IMPACT_STAKEHOLDER_REST_CONTROLLER_MAPPING + PUT_STAKEHOLDER_MAPPING.replace("{id}", id);
    }

    public static String buildDeleteStakeholderUri(String id) {
        return IMPACT_STAKEHOLDER_REST_CONTROLLER_MAPPING + DELETE_STAKEHOLDER_MAPPING.replace("{id}", id);
    }
}