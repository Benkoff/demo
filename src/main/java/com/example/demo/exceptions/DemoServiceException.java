package com.example.demo.exceptions;

public class DemoServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected String identifier;

    public DemoServiceException(final String identifier, final String message) {
        super(message);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
