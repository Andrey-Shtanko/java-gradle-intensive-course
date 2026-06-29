package org.intensive;

import java.util.Arrays;
import java.util.List;

public class WordWrap {
    private final int length;
    private final String content;

    public WordWrap(int length, String content) {
        this.length = length;
        this.content = content;
    }

    public void wrap () {
        List<String> words = Arrays.asList(content.split(" "));

        for (String word : words) {
            if (isLongWord(word)) {
                throw new TooShortLengthException("Word " + word + " is too long ");
            }
        }

        String splitContent = String.join("\n", words);
        System.out.println(splitContent);
    }

    private boolean isLongWord (String word) {
        return word.length() > this.length;
    }
}
