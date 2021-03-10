package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.SepaUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.Utils;
import org.springframework.stereotype.Service;

@Service
public class SepaService {

    public String validateSepaMessage(String messageText) throws Exception {

        SepaUtils.parseAndValidateSepaMessage(messageText);

        return Utils.convertXmlToJson(messageText, "Document");

    }
}
