package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import gr.datamation.replies.common.ChargesInformation;
import gr.datamation.replies.common.MsgReplyInfo;
import gr.datamation.replies.common.ReasonInformation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

public class AutoReplyUtils {

    private AutoReplyUtils() {
        throw new IllegalStateException("AutoReplyUtils class");
    }

    public static MsgReplyInfo buildMsgReplyInfoAccordingToParams(MsgReplyInfoRequest msgReplyInfoRequest, String replyId) {
        return buildMsgReplyInfoAccordingToParams(null, null, msgReplyInfoRequest.getIndicator(), replyId,
                null, null, null, null,
                msgReplyInfoRequest.getReasonCode() != null ?
                        msgReplyInfoRequest.getReasonCode() : msgReplyInfoRequest.getReasonPrtry(), msgReplyInfoRequest.getAdditionalInformation());
    }

    public static MsgReplyInfo buildMsgReplyInfoAccordingToParams(String orgnlMsgId, String orgnlInstrId, String orgnlTxId,
                                                             String replyId, String intrBkSttlmDt, String chargesAmount,
                                                             String chargesAgentBic, String chargeBearer,
                                                             String reasonInformationValue, String reasonInformationAdditionalInf) {
        MsgReplyInfo msgReplyInfo = new MsgReplyInfo();

        ReasonInformation reasonInformation = null;
        if (reasonInformationValue != null) {
            reasonInformation = new ReasonInformation();
            reasonInformation.setType(ReasonInformation.Type.CD);
            reasonInformation.setValue(reasonInformationValue);

            if (reasonInformationAdditionalInf != null) {
                reasonInformation.setAddtlInf(Collections.singletonList(reasonInformationAdditionalInf));
            }
        }

        msgReplyInfo.setRsnInf(reasonInformation);

        msgReplyInfo.setOrgnlMsgId(orgnlMsgId);
        msgReplyInfo.setOrgnlInstrId(orgnlInstrId);
        msgReplyInfo.setOrgnlTxId(orgnlTxId);
        msgReplyInfo.setReplyId(replyId);

        if (intrBkSttlmDt != null) {
            msgReplyInfo.setIntrBkSttlmDt(LocalDate.parse(intrBkSttlmDt));
        }

        if (chargesAmount != null || chargesAgentBic != null) {
            ChargesInformation chargesInformation = new ChargesInformation();

            if (chargesAmount != null) {
                chargesInformation.setAmount(new BigDecimal(chargesAmount));
            }

            if (chargesAgentBic != null) {
                chargesInformation.setAgentBic(chargesAgentBic);
            }

            msgReplyInfo.setChargesInformation(Collections.singletonList(chargesInformation));
        }

        msgReplyInfo.setChargeBearer(chargeBearer);

        return msgReplyInfo;
    }

}
