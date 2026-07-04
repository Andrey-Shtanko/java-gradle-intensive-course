package org.intensive.collections.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ListMapUtils {

    private ListMapUtils() {
    }

    public static <T, R> List<R> map(List<T> elements, Function<T, R> mapper) {
        if (elements.isEmpty()) {
            return Collections.emptyList();
        }

        List<R> result = new ArrayList<>();
        for (T element : elements) {
            result.add(mapper.apply(element));
        }

        return result;
    }
}
