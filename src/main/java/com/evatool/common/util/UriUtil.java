package com.evatool.common.util;

public class UriUtil {

  private UriUtil() {
  }

  // CRUD.
  public static final String ANALYSES = "/analyses";
  public static final String ANALYSES_ID = "/analyses/{id}";
  public static final String ANALYSES_DEEP_COPY = "/analyses/deep-copy/{templateAnalysisId}";

  public static final String IMPACTS = "/impacts";
  public static final String IMPACTS_ID = "/impacts/{id}";

  public static final String REQUIREMENTS = "/requirements";
  public static final String REQUIREMENTS_ID = "/requirements/{id}";

  public static final String REQUIREMENTS_DELTA = "/requirement-deltas";
  public static final String REQUIREMENTS_DELTA_ID = "/requirement-deltas/{id}";

  public static final String STAKEHOLDERS = "/stakeholders";
  public static final String STAKEHOLDERS_ID = "/stakeholders/{id}";
  public static final String STAKEHOLDERS_LEVELS = "/stakeholders/levels";
  public static final String STAKEHOLDERS_PRIORITIES = "/stakeholders/priorities";

  public static final String USERS = "/users";
  public static final String USERS_ID = "/users/{id}";

  public static final String VALUES = "/values";
  public static final String VALUES_ID = "/values/{id}";

  public static final String VALUE_TYPES = "/value-types";
  public static final String VALUE_TYPES_ID = "/value-types/{id}";

  public static final String VARIANTS = "/variants";
  public static final String VARIANTS_ID = "/variants/{id}";

  public static final String VARIANT_TYPES = "/variant-types";
  public static final String VARIANT_TYPES_ID = "/variant-types/{id}";

  // Auth.
  public static final String AUTH_LOGIN = "/auth/login/token";
  public static final String AUTH_REFRESH_LOGIN = "/auth/login/refresh-token";
  public static final String AUTH_REGISTER_USER = "/auth/register/user";
  public static final String AUTH_REGISTER_REALM = "/auth/register/realm";

  // Import-Export.
  public static final String IMPORT_ANALYSES = "/import/analyses";
  public static final String EXPORT_ANALYSES = "/export/analyses";
}
