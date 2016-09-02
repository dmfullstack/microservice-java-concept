package com.tenx.ms.retail.store.rest.dto;

import com.tenx.ms.retail.common.util.AllowConverterAccess;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


@ApiModel("Store model")
public class Store {
    @ApiModelProperty(value = "The Id of the Store", readOnly = true)
    @AllowConverterAccess
    private Long storeId;

    @ApiModelProperty(value = "The name of the Store", required = true)
    @NotNull
    private String name;


    public Long getStoreId() { return this.storeId; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
}
