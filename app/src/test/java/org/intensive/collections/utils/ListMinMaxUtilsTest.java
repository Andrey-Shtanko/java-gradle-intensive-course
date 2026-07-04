package org.intensive.collections.utils;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ListMinMaxUtilsTest {
    @Test
    void min() {
        final Optional<Integer> min = ListMinMaxUtils.min(List.of(5, 3, 8, 1, 4), Comparator.naturalOrder());

        assertEquals(1, min.orElse(-1));
    }

    @Test
    void max() {
        final Optional<Integer> max = ListMinMaxUtils.max(List.of(5, 3, 8, 1, 4), Comparator.naturalOrder());
        assertEquals(8, max.orElse(-1));
    }
}
