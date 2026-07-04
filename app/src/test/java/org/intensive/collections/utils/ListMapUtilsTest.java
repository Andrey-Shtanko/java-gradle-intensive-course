package org.intensive.collections.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListMapUtilsTest {
    @Test
    void groupBy() {
        final List<String> list = List.of("apple", "banana", "apricot", "blueberry", "cherry");
        final List<Integer> mappedList = ListMapUtils.map(list, String::length);

        assertEquals(list.size(), mappedList.size());
        assertEquals(33, mappedList.stream().reduce(0, Integer::sum));
    }
}
