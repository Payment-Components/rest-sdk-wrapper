package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class MtCreateGeneralRequest {

    @Schema(description = "Category of the MT message",
            example = "MT103", required = true)
    private String category;
    @Schema(description = "Used in Header Block 1",
            example = "MT103", required = true)
    private String sender;
    @Schema(description = "Used in Header Block 2",
            example = "MT103", required = true)
    private String receiver;
    @Schema(description = "Optional UETR of the message",
            example = "2412527e-aefa-455d-8307-851118e145fe")
    private String uetr;
    @Schema(required = true)
    private List<MtTagGeneralRequest> tags;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getUetr() {
        return uetr;
    }

    public void setUetr(String uetr) {
        this.uetr = uetr;
    }

    public List<MtTagGeneralRequest> getTags() {
        if(this.tags == null)
            this.tags = new ArrayList<>();
        return this.tags;
    }

    public void setTags(List<MtTagGeneralRequest> tags) {
        this.tags = tags;
    }
}

