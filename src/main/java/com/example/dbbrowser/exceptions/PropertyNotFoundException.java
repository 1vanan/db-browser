package com.example.dbbrowser.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException(String message) {
        super("Such property does not exist: " + message);
    }

    public PropertyNotFoundException(String message, Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }

    public static void PropertyNotFoundException(String message, Object... args) {
        throw new PropertyNotFoundException("Such brand does not exist: " + message, args);
    }
}
