package com.tenx.ms.retail.orders.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Order Details Model")
public class OrderDetails {
    @ApiModelProperty(value = "The Order Id.", readOnly = true)
    private long orderId;
    @ApiModelProperty(value = "The Product Id.")
    private long productId;
    @ApiModelProperty(value = "The Product Count.")
    private long count;


    public long getOrderId() { return this.orderId; }

    public void setOrderId(long orderId) { this.orderId = orderId; }

    public long getProductId() { return this.productId; }

    public void setProductId(long productId) { this.productId = productId; }

    public long getCount() { return this.count; }

    public void setCount(long count) { this.count = count; }
}
