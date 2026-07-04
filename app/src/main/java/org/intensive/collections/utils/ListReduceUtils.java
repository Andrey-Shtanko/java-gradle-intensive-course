package org.intensive.collections.utils;

import java.util.List;
import java.util.function.BinaryOperator;

public class ListReduceUtils {

    private ListReduceUtils() {
    }

    public static <T> T reduce(List<T> elements, T seed, BinaryOperator<T> accumulator) {
        if (elements.isEmpty()) {
            return seed;
        }

        T result = seed;

        for (T element : elements) {
            result = accumulator.apply(result, element);
        }

        return result;
    }
}
