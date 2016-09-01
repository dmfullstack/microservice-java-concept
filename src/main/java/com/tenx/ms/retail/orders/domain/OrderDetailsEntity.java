package com.tenx.ms.retail.orders.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "order_details")
@Access(AccessType.FIELD)
public class OrderDetailsEntity {

    @Id
    @GeneratedValue
    private long details_id;
    private long orderId;
    private long productId;
    private long count;


    public long getDetailsId() { return this.details_id; }
    public void getDetailsId (long id) { this.details_id = id; }

    public long getOrderId() { return this.orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }

    public long getProductId() { return this.productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public long getCount() { return this.count; }
    public void setCount(long count) { this.count = count; }
}
