package com.example.dbbrowser.exceptions;

import com.example.dbbrowser.dto.ConnectionProperties;
import org.slf4j.helpers.MessageFormatter;

public class UnableConnectionException extends RuntimeException {

    public UnableConnectionException(ConnectionProperties properties) {
        super("Failed to connect to database with properties: " + properties.toString());
    }

    public UnableConnectionException(String message, Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }

    public static void UnableConnectionException(ConnectionProperties properties, Object... args) {
        throw new PropertyNotFoundException("Failed to connect to database with properties: "
            + properties.toString(), args);
    }
}
