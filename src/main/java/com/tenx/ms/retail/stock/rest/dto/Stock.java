package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;


@ApiModel("Stock model.")
public class Stock {

    @ApiModelProperty(value = "The stock ID", readOnly = true)
    private Long productId;
    @ApiModelProperty(value = "The product ID", readOnly = true)
    private Long storeId;
    @ApiModelProperty(value = "Product quantity", required = true)
    @NotNull
    @NumberFormat
    private Long count;


    public Stock() {
    }

    public Stock(Long storeId, Long productId, Long count) {
        this.productId = productId;
        this.storeId   = storeId;
        this.count     = count;
    }


    public Long getProductId() { return this.productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getStoreId() { return this.storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public Long getCount() { return this.count; }
    public void setCount(Long count) { this.count = count; }
}
