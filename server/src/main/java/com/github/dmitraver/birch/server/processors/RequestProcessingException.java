package com.github.dmitraver.birch.server.processors;

public final class RequestProcessingException extends Exception {

    public RequestProcessingException() {
    }

    public RequestProcessingException(String message) {
        super(message);
    }

    public RequestProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
