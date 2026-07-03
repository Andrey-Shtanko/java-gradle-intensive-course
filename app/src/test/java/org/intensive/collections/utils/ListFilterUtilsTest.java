package org.intensive.collections.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListFilterUtilsTest {

    @Test
    void filter() {
        final List<Integer> filteredList = ListFilterUtils.filter(List.of(1, 2, 3, 4, 5), x -> x > 3);
        assertEquals(2, filteredList.size());
    }
}
