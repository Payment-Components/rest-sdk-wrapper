package com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request;

public class MsgReplyInfoRequest {

    private String reasonCode;
    private String reasonPrtry;
    private String indicator;
    private String additionInformation;

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonPrtry() {
        return reasonPrtry;
    }

    public void setReasonPrtry(String reasonPrtry) {
        this.reasonPrtry = reasonPrtry;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getAdditionInformation() {
        return additionInformation;
    }

    public void setAdditionInformation(String additionInformation) {
        this.additionInformation = additionInformation;
    }
}
