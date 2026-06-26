package org.intensive;

import java.util.ArrayList;

public class WordWrap {
    private final int length;
    private final String content;

    public WordWrap(int length, String content) {
        this.length = length;
        this.content = content;
    }

    private Boolean isLongWord(String word) {
        return word.length() > this.length;
    }

    public void wrap () {
        ArrayList<String> words = new ArrayList<>(java.util.Arrays.asList(content.split(" ")));

        for (String word : words) {
            if (isLongWord(word)) {
                throw new TooShortLengthException("Word " + word + " is too long ");
            }
        }

        String splittedContent = String.join("\n", words);
        System.out.println(splittedContent);
    }
}
