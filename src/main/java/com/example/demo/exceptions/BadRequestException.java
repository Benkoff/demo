package com.example.demo.exceptions;

public class BadRequestException extends DemoServiceException{

    public BadRequestException(final String identifier, final String message) {
        super(identifier, message);
    }
}
