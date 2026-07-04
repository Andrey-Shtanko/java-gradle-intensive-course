package org.intensive.collections.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;



public class ListGroupByUtils {

    private ListGroupByUtils() {
    }

    public static <T, K> Map<K, List<T>> groupBy(List<T> elements, Function<T, K> keyExtractor) {
        if (elements.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<K, List<T>> result = new HashMap<>();
        for (T element : elements) {
            K key = keyExtractor.apply(element);

        result.computeIfAbsent(key, k -> new ArrayList<>()).add(element);}

        return result;
    }
}
