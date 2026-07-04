package org.intensive.collections.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ListGenerateUtils {

    private ListGenerateUtils() {
    }

    public static <T> List<T> generate(int count, Supplier<T> valueFactory) {
        if (count <= 0) {
            return Collections.emptyList();
        }

        List<T> elements = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            elements.add(valueFactory.get());
        }

        return elements;
    }
}
