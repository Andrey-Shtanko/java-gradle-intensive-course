package org.intensive.collections.utils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ListMinMaxUtils {

    private ListMinMaxUtils() {
    }

    public static <T> Optional<T> min(List<T> elements, Comparator<T> comparator) {
        if (elements.isEmpty()) {
            return Optional.empty();
        }

        Optional<T> min = Optional.empty();
        for (T element : elements) {
            if (min.isEmpty() || comparator.compare(element, min.get()) < 0) {
                min = Optional.of(element);
            }
        }

        return min;
    }

    public static <T> Optional<T> max(List<T> elements, Comparator<T> comparator) {
        if (elements.isEmpty()) {
            return Optional.empty();
        }

        Optional<T> max = Optional.empty();
        for (T element : elements) {
            if (max.isEmpty() || comparator.compare(element, max.get()) > 0) {
                max = Optional.of(element);
            }
        }

        return max;
    }
}
