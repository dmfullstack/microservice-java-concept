package com.tenx.ms.retail.orders.rest.dto;

import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.common.util.DenyConverterAccess;
import com.tenx.ms.retail.orders.domain.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;


@ApiModel("Order Model")
public class Order {
    @ApiModelProperty(value = "The order Id.", readOnly = true)
    private Long orderId;
    @ApiModelProperty(value = "The store Id.", readOnly = true)
    private Long storeId;
    @ApiModelProperty(value = "The order date.")
    @DateTimeFormat
    @NotNull
    private Date orderDate;
    @ApiModelProperty(value = "The order status.")
    @NotNull
    private OrderStatus status;
    @ApiModelProperty(value = "The products in the order.")
    @NotNull
    private List<OrderDetails> products;
    @ApiModelProperty(value = "Buyer's first name.")
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    @NotNull
    private String firstName;
    @ApiModelProperty(value = "Buyer's last name.")
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    @NotNull
    private String lastName;
    @ApiModelProperty(value = "Buyer's email.")
    @Email
    @NotNull
    private String email;
    @ApiModelProperty(value = "Buyer's phone number.")
    @PhoneNumber
    @NotNull
    private String phone;


    public Long getOrderId() { return this.orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getStoreId() { return this.storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public Date getOrderDate() { return this.orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return this.status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    @DenyConverterAccess
    public List<OrderDetails> getProducts() { return this.products; }
    public void setProducts(List<OrderDetails> products) { this.products = products; }

    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return this.phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
