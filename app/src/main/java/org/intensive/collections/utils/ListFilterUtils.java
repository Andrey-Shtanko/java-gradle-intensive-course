package org.intensive.collections.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ListFilterUtils {

    private ListFilterUtils() {
    }

    public static <T> List<T> filter(List<T> elements, Predicate<T> filter) {
        if (elements.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> filteredElements = new ArrayList<>();
        for (T element : elements) {
            if (filter.test(element)) {
                filteredElements.add(element);
            }
        }

        return filteredElements;
    }
}
