package org.intensive.collections.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ListGroupByUtilsTest {
    @Test
    void groupBy() {
        final List<String> list = List.of("apple", "banana", "apricot", "blueberry", "cherry");
        final Map<Integer, List<String>> groupedList = ListGroupByUtils.groupBy(list, String::length);

        List<String> checkList = List.of("apple");

        assertEquals(4, groupedList.size());
        assertEquals(checkList, groupedList.get(5));
    }
}
