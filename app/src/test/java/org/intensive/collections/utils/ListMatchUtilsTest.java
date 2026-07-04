package org.intensive.collections.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ListMatchUtilsTest {

    @Test
    void allMatch() {
        final boolean result = ListMatchUtils.allMatch(List.of(2, 4, 6, 8), x -> x % 2 == 0);
        assertTrue(result);
    }

    @Test
    void anyMatch() {
        final boolean result = ListMatchUtils.anyMatch(List.of(1, 3, 5, 7), x -> x % 2 == 0);
        assertFalse(result);
    }

    @Test
    void noneMatch() {
        final boolean result = ListMatchUtils.noneMatch(List.of(1, 3, 5, 7), x -> x % 2 == 0);
        assertTrue(result);
    }
}
