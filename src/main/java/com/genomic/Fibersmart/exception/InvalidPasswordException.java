package com.genomic.Fibersmart.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(final String message) {
        super(message);
    }
}
