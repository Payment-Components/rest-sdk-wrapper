package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class Institution {
    @Schema(description = "Party Identifier",
            example = "123456789")
    private String id;
    @Schema(description = "Identifier Code",
            example = "TESTBICA", required = true)
    private String bic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}