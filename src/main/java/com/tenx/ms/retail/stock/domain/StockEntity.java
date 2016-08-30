package com.tenx.ms.retail.stock.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "stock")
@Access(AccessType.FIELD)
public class StockEntity {
    @Id
    @GeneratedValue
    private long productId;
    private long storeId;
    private long count;

    public long getProductId() { return this.productId; }

    public void setProductId(long productId) { this.productId = productId; }

    public long getStoreId() { return this.storeId; }

    public void setStoreId(long storeId) { this.storeId = storeId; }

    public long getCount() { return this.count; }

    public void setCount(long count) { this.count = count; }
}
