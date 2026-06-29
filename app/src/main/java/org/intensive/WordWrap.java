package org.intensive;

import java.util.ArrayList;
import java.util.Arrays;

public class WordWrap {
    private final int length;
    private final String content;

    public WordWrap(int length, String content) {
        this.length = length;
        this.content = content;
    }

    public void wrap () {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(content.split(" ")));

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
