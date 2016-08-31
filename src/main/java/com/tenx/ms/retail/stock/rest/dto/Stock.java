package com.tenx.ms.retail.stock.rest.dto;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.NumberFormat;

@ApiModel("Stock model.")
public class Stock {

    @ApiModelProperty(value = "The stock ID", readOnly = true)
    @NotNull
    private long productId;
    @ApiModelProperty(value = "The product ID", readOnly = true)
    @NotNull
    private long storeId;
    @ApiModelProperty(value = "Product quantity", required = true)
    @NotNull
    @NumberFormat
    private long count;


    public Stock() {
    }

    public Stock(long storeId, long productId, long count) {
        this.productId = productId;
        this.storeId   = storeId;
        this.count     = count;
    }


    public long getProductId() { return this.productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public long getStoreId() { return this.storeId; }
    public void setStoreId(long storeId) { this.storeId = storeId; }

    public long getCount() { return this.count; }
    public void setCount(long count) { this.count = count; }
}
