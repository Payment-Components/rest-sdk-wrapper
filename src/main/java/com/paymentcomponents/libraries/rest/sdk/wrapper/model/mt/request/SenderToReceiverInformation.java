package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class SenderToReceiverInformation {
    @Schema(description = "Line 1",
            example = "/INS/ABNANL2A")
    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String line5;
    private String line6;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getLine5() {
        return line5;
    }

    public void setLine5(String line5) {
        this.line5 = line5;
    }

    public String getLine6() {
        return line6;
    }

    public void setLine6(String line6) {
        this.line6 = line6;
    }
}