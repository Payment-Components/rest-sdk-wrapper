package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.mx.CbprMessage;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;


public class CbprUtils {

    public static CbprMessage parseAndValidateCbprMessage(String cbprXml) throws Exception {
        CbprMessage cbprMessage = new CbprMessage();

        ValidationErrorList validationErrorList = cbprMessage.validateXml(new ByteArrayInputStream(cbprXml.getBytes()));
        if (!validationErrorList.isEmpty()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(validationErrorList));
        }

        cbprMessage.autoParseXml(cbprXml);
        validationErrorList = cbprMessage.autoValidate();

        if (!validationErrorList.isEmpty()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(validationErrorList));
        }

        return cbprMessage;
    }

}
