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
    private Long details_id;
    private Long orderId;
    private Long productId;
    private Long count;


    public Long getDetailsId() { return this.details_id; }
    public void getDetailsId (Long id) { this.details_id = id; }

    public Long getOrderId() { return this.orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return this.productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public Long getCount() { return this.count; }
    public void setCount(Long count) { this.count = count; }
}
