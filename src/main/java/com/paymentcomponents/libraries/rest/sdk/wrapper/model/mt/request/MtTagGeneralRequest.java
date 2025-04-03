package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Contains the data for the MT Tag")
public class MtTagGeneralRequest {
    @Schema(description = "The name of the field",
            example = "20", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @ArraySchema(schema = @Schema(description = "The lines for the field",
            example = "line1", requiredMode = Schema.RequiredMode.REQUIRED))
    private List<String> data;

    public MtTagGeneralRequest() {
    }

    public MtTagGeneralRequest(String name, List<String> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getData() {
        if(this.data == null)
            this.data = new ArrayList<>();
        return this.data;    }

    public void setData(List<String> data) {
        this.data = data;
    }
}