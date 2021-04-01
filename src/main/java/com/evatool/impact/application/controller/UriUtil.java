package com.evatool.impact.application.controller;

public class UriUtil {

    private UriUtil() {
    }

    public static final String IMPACTS = "/impacts";
    public static final String IMPACTS_ID = "/impacts/{id}";

    public static final String VALUES = "/values";
    public static final String VALUE_ID = "/values/{id}";
    public static final String VALUE_TYPES = "/values/types";
    public static final String VALUE_NAME = "value";

    public static final String IMPACT_VALUES = "/impacts/values";
    public static final String IMPACT_VALUE_ID = "/impacts/values/{id}";
    public static final String VALUE_VALUE_NAME = "impact value";

    public static final String STAKEHOLDERS = "/stakeholders";
    public static final String STAKEHOLDER_NAME = "stakeholder";

    public static final String ANALYSES = "/analysis";
    public static final String ANALYSIS_NAME = "analysis";
}
