package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.RtgsUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.Utils;
import org.springframework.stereotype.Service;

@Service
public class RtgsService {

    public String validateRtgs(String rtgsMessageText) throws Exception {
        RtgsUtils.parseAndValidateRtgsMessage(rtgsMessageText);

        return Utils.convertXmlToJson(rtgsMessageText, "Document");
    }

}
