package com.evatool.common.util;

import lombok.SneakyThrows;
import org.json.JSONObject;

public class PrintUtil {

    @SneakyThrows
    public static void prettyPrintJson(String json) {
        var jsonObject = new JSONObject(json);
        System.out.println(jsonObject.toString(4));
    }

}
