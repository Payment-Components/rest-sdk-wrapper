package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.MxUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.Utils;
import org.springframework.stereotype.Service;

@Service
public class MxService {

    public String validateMx(String mxMessage) throws Exception {
        MxUtils.parseAndValidateMxMessage(mxMessage);

        return Utils.convertXmlToJson(mxMessage, "Document");
    }

}
