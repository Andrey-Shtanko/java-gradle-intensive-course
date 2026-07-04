package org.intensive.collections.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ListGenerateUtilsTest {

    @Test
    void generate() {
        Supplier<Integer> valueFactory = () -> LocalDate.now().hashCode();

        final List<Integer> generatedList = ListGenerateUtils.generate(5, valueFactory);
        System.out.println(generatedList);
        assertTrue(generatedList.stream().allMatch(value -> value.equals(valueFactory.get())));
    }
}
