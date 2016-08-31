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
    private long productId;
    @Id
    private long storeId;
    private long count;


    public StockEntity() {

    }

    public StockEntity(long productId, long storeId, long count) {
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
