package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.CbprUtils;
import gr.datamation.mx.CbprMessage;
import org.springframework.stereotype.Service;

@Service
public class CbprService {

    public void validateCbpr(String cbprMessageText) throws Exception {
        CbprUtils.parseAndValidateCbprMessage(cbprMessageText);
    }

    public String envelopeCbpr(String cbprMessageText) throws Exception {
        CbprMessage<?, ?> cbprMessage = CbprUtils.parseAndValidateCbprMessage(cbprMessageText);
        return cbprMessage.encloseCbprMessage("RequestPayload");
    }

}
