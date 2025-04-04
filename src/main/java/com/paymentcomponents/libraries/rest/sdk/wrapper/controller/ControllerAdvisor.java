package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.mt.common.InvalidMessageFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.Constants.REQUEST_LOG_ID;

@ControllerAdvice
public class ControllerAdvisor {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ExceptionHandler(InvalidMessageFormatException.class)
    public ResponseEntity<String> invalidMessageFormatHandler(HttpServletRequest req,
                                                              InvalidMessageFormatException imfh) {
        logger.error(getRequestId(req), imfh);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(imfh.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMessageException.class)
    public ResponseEntity<String> invalidMessageHandler(HttpServletRequest req,
                                                              InvalidMessageException ex) {
        logger.error(getRequestId(req), ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getResponseBodyAsString(), httpHeaders, ex.getStatusCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(HttpServletRequest req,
                                                              NoHandlerFoundException ex) {
        logger.error(getRequestId(req), ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>("No endpoint " + ex.getHttpMethod() + " " + ex.getRequestURL(),
                                   httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> invalidMessageHandler(HttpServletRequest req,
                                                              Exception ex) {
        logger.error(getRequestId(req), ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(ex.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getRequestId(HttpServletRequest req) {
        return req.getAttribute(REQUEST_LOG_ID) != null ?
                req.getAttribute(REQUEST_LOG_ID).toString() : "No request ID";
    }

}
