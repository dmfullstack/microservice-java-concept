package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("Store model")
public class Store {
    @ApiModelProperty(value = "The Id of the Store", readOnly = true)
    private long storeId;

    @ApiModelProperty(value = "The name of the Store", required = true)
    private String name;


    public Store() {
    }

    public Store(long storeId) {
        this.storeId = storeId;
    }


    public long getStoreId() { return this.storeId; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }
}
