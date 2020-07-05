package com.example.demo;

import com.example.demo.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;

import static com.example.demo.constants.Beans.MANAGEMENT_SERVICE_ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(com.example.demo.CustomExceptionHandler.class);

    private final MessageSource messages;

    public CustomExceptionHandler(@Qualifier("messageSource") final MessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(final Exception e, final WebRequest request) {
        log.error("Exception error has occurred: ", e);
        final String endpointResponse = this.createMessage(MANAGEMENT_SERVICE_ID, e.getMessage());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(endpointResponse);
    }

    @ExceptionHandler({ConversionFailedException.class})
    public final ResponseEntity<Object> handleHttpMessageConversionException(final ConversionFailedException e, final WebRequest request) {
        log.error("ConversionFailedException error has occurred: ", e);

        final String endpointResponse = this.createMessage(MANAGEMENT_SERVICE_ID, e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).body(endpointResponse);
    }

    @ExceptionHandler({BadRequestException.class})
    public final ResponseEntity<Object> handleBadRequestException(final BadRequestException e, final WebRequest request) {
        log.error("BadRequestException error has occurred: ", e);

        final String endpointResponse = this.createMessage(e.getIdentifier(), e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).body(endpointResponse);
    }

    private String createMessage(final String identifier, final String errorMessage) {
        return String.join(": ", identifier, errorMessage);
    }

    private String msg(final String key, final Object... phVal) {
        return this.messages.getMessage(key, phVal, Locale.getDefault());
    }
}
