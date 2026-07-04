package org.intensive.collections.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListReduceUtilsTest {
    @Test
    void reduce() {
        final Integer result = ListReduceUtils.reduce(List.of(1, 2, 3, 4, 5), 50, (acc, el) -> acc - el);
        assertEquals(35, result);
    }
}
