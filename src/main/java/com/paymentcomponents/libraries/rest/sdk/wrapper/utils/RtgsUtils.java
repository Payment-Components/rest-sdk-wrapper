package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import gr.datamation.mx.Message;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

import static gr.datamation.iso20022.target2.RtgsUtils.autoParseXML;
import static gr.datamation.iso20022.target2.RtgsUtils.autoValidateXML;

public class RtgsUtils {

    public static Message parseAndValidateRtgsMessage(String rtgsXml) throws Exception {
        ValidationErrorList validationErrorList = autoValidateXML(new ByteArrayInputStream(rtgsXml.getBytes()));
        MxUtils.throwMxValidationError(validationErrorList);

        Message rtgsMessage = autoParseXML(rtgsXml);

        validationErrorList.addAll(rtgsMessage.validate());
        MxUtils.throwMxValidationError(validationErrorList);

        return rtgsMessage;
    }

}
