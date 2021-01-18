package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class RemittanceInformation {
    @Schema(description = "Line 1",
            example = "/INV/abc/SDF-96//1234-234///ROC/98I")
    private String line1;
    @Schema(description = "Line 2",
            example = "U87")
    private String line2;
    private String line3;
    private String line4;

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
}