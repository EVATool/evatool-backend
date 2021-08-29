package com.evatool.common.util;

import lombok.SneakyThrows;
import org.json.JSONObject;

public class PrintUtil {

    private PrintUtil() {
    }

    @SneakyThrows
    public static String prettifyJson(String json) {
        var jsonObject = new JSONObject(json);
        return jsonObject.toString(4);
    }
}
