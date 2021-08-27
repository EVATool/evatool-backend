package com.evatool.common.exception.functional.http400;

import com.evatool.common.util.FunctionalErrorCodesUtil;
import org.json.JSONException;

public class ImportJsonException extends BadRequestException {
    public ImportJsonException(JSONException jsonException) {
        this(jsonException.getMessage());
    }

    public ImportJsonException(String message) {
        super(message, FunctionalErrorCodesUtil.IMPORT_EXPORT_JSON_INVALID, null);
    }
}
