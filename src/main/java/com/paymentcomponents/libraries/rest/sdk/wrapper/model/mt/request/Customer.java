package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class Customer {
    @Schema(description = "Account of Customer",
            example = "123456789")
    private String account;
    @Schema(description = "Part of Name and Address of Customer",
            example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "Part of Name and Address of Customer",
            example = "Chatziantoniou 14")
    private String addressLine1;
    @Schema(description = "Part of Name and Address of Customer",
            example = "15124, Marousi")
    private String addressLine2;
    @Schema(description = "Part of Name and Address of Customer",
            example = "Attika, Greece")
    private String addressLine3;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

}