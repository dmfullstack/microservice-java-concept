package com.tenx.ms.retail.orders.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("Order Details Model")
public class OrderDetails {
    @ApiModelProperty(value = "The Order Id.", readOnly = true)
    private Long orderId;
    @ApiModelProperty(value = "The Product Id.")
    private Long productId;
    @ApiModelProperty(value = "The Product Count.")
    private Long count;


    public Long getOrderId() { return this.orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return this.productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getCount() { return this.count; }
    public void setCount(Long count) { this.count = count; }
}
