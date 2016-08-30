package com.tenx.ms.retail.orders.rest.dto;


import com.tenx.ms.retail.orders.domain.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("Order Model")
public class Order {
    @ApiModelProperty(value = "The order Id.", readOnly = true)
    private long orderId;
    @ApiModelProperty(value = "The store Id.", readOnly = true)
    private long storeId;
    private Date orderDate;
    private OrderStatus status;
    private List<OrderDetails> products;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;


    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderDetails> getProducts() {
        return this.products;
    }

    public void setProducts(List<OrderDetails> products) {
        this.products = products;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
