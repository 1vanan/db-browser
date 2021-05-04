package com.example.dbbrowser.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class PropertyVerificationException extends RuntimeException {

    public PropertyVerificationException(String name, String message) {
        super("The property format of " + name + " is wrong: " + message);
    }

    public PropertyVerificationException(String message, Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }

    public static void PropertyVerificationException(String name, String message, Object... args) {
        throw new PropertyNotFoundException("The property format of " + name + " is wrong: " + message, args);
    }
}
