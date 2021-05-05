package com.example.dbbrowser.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class UnableLoadDbException extends RuntimeException {

    public UnableLoadDbException(String message) {
        super("Failed upload data from database. Error message: " + message);
    }

    public UnableLoadDbException(String message, Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }

    public static void UnableLoadDbException(String message, Object... args) {
        throw new PropertyNotFoundException("Failed upload data from database. Error message: " + message, args);
    }
}
