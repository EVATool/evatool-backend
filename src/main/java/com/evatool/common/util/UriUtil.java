package com.evatool.common.util;

public class UriUtil {

  private UriUtil() {
    /*

    User register:
    creates user in default keycloak realm
    backend must use username as realm

    Auth:
    add spring validation to request params

    Import Export:
    DemPa-App

    */
  }

  // CRUD.
  public static final String ANALYSES = "/analyses";
  public static final String ANALYSES_ID = "/analyses/{id}";
  public static final String ANALYSES_DEEP_COPY = "/analyses/deep-copy/{templateAnalysisId}";
  public static final String ANALYSIS_REL = "analyses";

  public static final String IMPACTS = "/impacts";
  public static final String IMPACTS_ID = "/impacts/{id}";
  public static final String IMPACTS_REL = "impacts";

  public static final String REQUIREMENTS = "/requirements";
  public static final String REQUIREMENTS_ID = "/requirements/{id}";
  public static final String REQUIREMENTS_REL = "requirements";

  public static final String REQUIREMENTS_DELTA = "/requirement-deltas";
  public static final String REQUIREMENTS_DELTA_ID = "/requirement-deltas/{id}";
  public static final String REQUIREMENTS_DELTA_REL = "requirement deltas";

  public static final String STAKEHOLDERS = "/stakeholders";
  public static final String STAKEHOLDERS_ID = "/stakeholders/{id}";
  public static final String STAKEHOLDERS_LEVELS = "/stakeholders/levels";
  public static final String STAKEHOLDERS_PRIORITIES = "/stakeholders/priorities";
  public static final String STAKEHOLDERS_REL = "stakeholder";

  public static final String USERS = "/users";
  public static final String USERS_ID = "/users/{id}";
  public static final String USERS_REL = "users";

  public static final String VALUES = "/values";
  public static final String VALUES_ID = "/values/{id}";
  public static final String VALUES_TYPES = "/values/types";
  public static final String VALUES_REL = "value";

  public static final String VARIANTS = "/variants";
  public static final String VARIANTS_ID = "/variants/{id}";
  public static final String VARIANTS_REL = "variants";

  // Auth.
  public static final String AUTH_LOGIN = "/auth/login/token";
  public static final String AUTH_REFRESH_LOGIN = "/auth/login/refresh-token";
  public static final String AUTH_REGISTER_USER = "/auth/register/user";
  public static final String AUTH_REGISTER_REALM = "/auth/register/realm";

  // Import-Export.
  public static final String IMPORT_ANALYSES = "/import/analyses";
  public static final String EXPORT_ANALYSES = "/export/analyses";
}
