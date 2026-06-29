package org.intensive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordWrapTest {
    @Test
    public void testWrap() {
        final WordWrap wordWrap = new WordWrap(20, "This is a test string for word wrapping.");
        assertDoesNotThrow(wordWrap::wrap);
    }

    @Test
    public void testWrapException() {
        final WordWrap wordWrap = new WordWrap(5, "hjdhfjhsdgbjhdbajhfsa");
        assertThrows(TooShortLengthException.class, wordWrap::wrap);
    }
}

