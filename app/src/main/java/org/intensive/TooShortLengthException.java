package org.intensive;

public class TooShortLengthException extends RuntimeException {
    public TooShortLengthException(String message) {
        super(message);
    }
}
