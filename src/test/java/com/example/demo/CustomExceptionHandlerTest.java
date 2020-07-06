package com.example.demo;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.Student;
import com.example.demo.models.requests.StudentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static com.example.demo.constants.Beans.MANAGEMENT_SERVICE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class CustomExceptionHandlerTest {
    public static final String TEST_ERROR_MESSAGE = "Test Error Message";

    private CustomExceptionHandler handler;

    private MessageSource messages;
    private WebRequest request;

    @BeforeEach
    void setUp() {
        messages = mock(MessageSource.class);
        handler = new CustomExceptionHandler(messages);
        MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET", "/");
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
        request = new ServletWebRequest(servletRequest, servletResponse);
    }

    @Test
    void handleException() {
        String message = TEST_ERROR_MESSAGE;
        RuntimeException e = new RuntimeException(message);
        ResponseEntity<String> expectedResponse = ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(String.join(": ", MANAGEMENT_SERVICE_ID, message));

        ResponseEntity<Object> actualResponse = handler.handleException(e, request);

        assertThat(actualResponse).isEqualToComparingFieldByField(expectedResponse);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void handleHttpMessageConversionException() {
        String message = TEST_ERROR_MESSAGE;
        ConversionFailedException e = new ConversionFailedException(
                TypeDescriptor.valueOf(StudentRequest.class),
                TypeDescriptor.valueOf(Student.class),
                message,
                null);
        String responseMessage = "Failed to convert from type [" + StudentRequest.class.getName() + "] "
                + "to type [" + Student.class.getName() + "] for value '" + message + "'";
        ResponseEntity<String> expectedResponse = ResponseEntity.status(BAD_REQUEST)
                .body(String.join(": ", MANAGEMENT_SERVICE_ID, responseMessage));

        ResponseEntity<Object> actualResponse = handler.handleHttpMessageConversionException(e, request);

        assertThat(actualResponse).isEqualToComparingFieldByField(expectedResponse);
    }

    @Test
    void handleBadRequestException() {
        String message = TEST_ERROR_MESSAGE;
        BadRequestException e = new BadRequestException(MANAGEMENT_SERVICE_ID, message);
        ResponseEntity<String> expectedResponse = ResponseEntity.status(BAD_REQUEST)
                .body(String.join(": ", MANAGEMENT_SERVICE_ID, message));

        ResponseEntity<Object> actualResponse = handler.handleBadRequestException(e, request);

        assertThat(actualResponse).isEqualToComparingFieldByField(expectedResponse);
    }
}