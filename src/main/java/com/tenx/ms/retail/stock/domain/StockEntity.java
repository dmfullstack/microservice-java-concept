package com.tenx.ms.retail.stock.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "stock")
@Access(AccessType.FIELD)
@IdClass(StockEntityPK.class)
public class StockEntity {
    @Id
    private Long productId;
    @Id
    private Long storeId;
    private Long count;


    public StockEntity() {

    }

    public StockEntity(Long productId, Long storeId, Long count) {
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
