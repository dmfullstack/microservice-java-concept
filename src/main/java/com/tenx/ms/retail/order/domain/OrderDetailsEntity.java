package com.tenx.ms.retail.order.domain;

import com.tenx.ms.retail.common.util.AllowConverterAccess;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "order_details")
@Access(AccessType.FIELD)
public class OrderDetailsEntity {

    @Id
    @GeneratedValue
    @AllowConverterAccess
    private Long detailsId;
    @ManyToOne
    @JoinColumn(name = "order_id", updatable = false)
    private OrderEntity order;
    private Long productId;
    private Long count;


    public Long getDetailsId() { return this.detailsId; }
    public Long getOrderId() { return this.order.getOrderId(); }

    public OrderEntity getOrder() { return this.order; }
    public void setOrder(OrderEntity order) { this.order = order; }

    public Long getProductId() { return this.productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getCount() { return this.count; }
    public void setCount(Long count) { this.count = count; }
}
