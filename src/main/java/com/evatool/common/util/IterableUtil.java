package com.evatool.common.util;

public class IterableUtil {

    public static int iterableSize(Iterable iterable) {
        int counter = 0;
        for (Object i : iterable) {
            counter++;
        }
        return counter;
    }

}
