package com.evatool.impact.application.controller;

public class UriUtil {

    private UriUtil() {
    }

    public static final String IMPACTS = "/impacts";
    public static final String IMPACTS_ID = "/impacts/{id}";
    public static final String IMPACT_NAME = "impact";
    public static final String UPDATE_IMPACT = "update " + IMPACT_NAME;
    public static final String DELETE_IMPACT = "delete " + IMPACT_NAME;

    public static final String DIMENSIONS = "/dimensions";
    public static final String DIMENSIONS_ID = "/dimensions/{id}";
    public static final String DIMENSION_TYPES = "/dimensions/types";
    private static final String DIMENSION_NAME = "dimension";
    public static final String GET_DIMENSION = "get " + DIMENSION_NAME;
    public static final String UPDATE_DIMENSION = "update " + DIMENSION_NAME;
    public static final String DELETE_DIMENSION = "delete " + DIMENSION_NAME;

    public static final String STAKEHOLDERS = "/stakeholders";
    public static final String GET_STAKEHOLDER = "get stakeholder";

    public static final String ANALYSES = "/analysis";
    public static final String GET_ANALYSIS = "get analysis";
}
