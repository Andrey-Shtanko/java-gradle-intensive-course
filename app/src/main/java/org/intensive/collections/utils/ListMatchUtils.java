package org.intensive.collections.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ListMatchUtils {

    private ListMatchUtils() {
    }

    public static <T> boolean allMatch(List<T> elements, Predicate<T> predicate) {
        if (elements.isEmpty()) {
            return false;
        }

        List<Integer> result = new ArrayList<>();
        for (T element : elements) {
            if (predicate.test(element)) {
                result.add(1);
            }
        }

        return result.size() == elements.size();
    }

    public static <T> boolean anyMatch(List<T> elements, Predicate<T> predicate) {
        if (elements.isEmpty()) {
            return false;
        }

        for (T element : elements) {
            if (predicate.test(element)) {
                return true;
            }
        }

        return false;
    }

    public static <T> boolean noneMatch(List<T> elements, Predicate<T> predicate) {
        if (elements.isEmpty()) {
            return false;
        }

        List<Integer> result = new ArrayList<>();
        for (T element : elements) {
            if (predicate.test(element)) {
                result.add(1);
            }
        }

        return result.isEmpty();
    }
}
