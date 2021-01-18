package com.paymentcomponents.libraries.rest.sdk.wrapper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

public class InvalidMessageException extends HttpClientErrorException {
    public InvalidMessageException(String responseBody) {
        super(HttpStatus.BAD_REQUEST, "Bad Request", responseBody.getBytes(), Charset.forName("UTF-8"));
    }
}
