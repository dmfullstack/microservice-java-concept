package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


@ApiModel("Store model")
public class Store {
    @ApiModelProperty(value = "The Id of the Store", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "The name of the Store", required = true)
    @NotNull
    private String name;


    public Store() {
    }

    public Store(Long storeId) {
        this.storeId = storeId;
    }


    public Long getStoreId() { return this.storeId; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
}
