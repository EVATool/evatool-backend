package com.evatool.common.util;

import com.evatool.domain.entity.SuperEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IterableUtil {

    private IterableUtil() {
    }

    public static int iterableSize(Iterable iterable) {
        int counter = 0;
        for (Object i : iterable) {
            counter++;
        }
        return counter;
    }

    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        var list = new ArrayList<T>();
        for (T element : iterable) {
            list.add(element);
        }
        return list;
    }

    public static <O extends SuperEntity> UUID[] entityIterableToIdArray(Iterable<O> entitySet) { // TODO Tests
        var ids = new ArrayList<UUID>();
        var index = 0;
        for (var o : entitySet) {
            ids.add(o.getId());
            index++;
        }
        var idArray = new UUID[index];
        index = 0;
        for (var id : ids) {
            idArray[index] = id;
            index++;
        }
        return idArray;
    }
}
