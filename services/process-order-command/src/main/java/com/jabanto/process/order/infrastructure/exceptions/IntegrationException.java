package com.jabanto.process.order.infrastructure.exceptions;

public class IntegrationException extends Exception {
    public IntegrationException(Throwable cause) {
        super(cause);
    }

    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
