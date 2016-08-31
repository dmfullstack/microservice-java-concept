package com.tenx.ms.retail.orders.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class OrderEntity {
    @Id
    @GeneratedValue
    private long orderId;
    private long storeId;
    private Date orderDate;
    private int status;
    private List<OrderDetailsEntity> products;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;


    /**
     * Creates a new instance of the OrderEntity class.
     * @param storeId The Store Id the order belongs to.
     */
    public OrderEntity(long storeId) {
        this.storeId = storeId;
    }


    public long getOrderId() { return orderId; }

    public long getStoreId() { return storeId; }

    public Date getOrderDate() { return orderDate; }

    public void setOrderDate(Date orderDate) {  }

    public OrderStatus getStatus() { return OrderStatus.fromValue(this.status); }

    public void setStatus(OrderStatus status) { this.status = status.getValue(); }

    public List<OrderDetailsEntity> getProducts() { return products; }

    public void setProducts(List<OrderDetailsEntity> products) { this.products = products; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}
