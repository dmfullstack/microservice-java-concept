package com.tenx.ms.retail.stock.domain;

import java.io.Serializable;

public class StockEntityPK implements Serializable{
    private long productId;
    private long storeId;

    public long getProductId() { return this.productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public long getStoreId() { return this.storeId; }
    public void setStoreId(long storeId) { this.storeId = storeId; }

    @Override
    public boolean equals(Object obj) {
        if (!StockEntityPK.class.isInstance(obj))
            return false;

        StockEntityPK o = (StockEntityPK) obj;

        return o.productId == this.productId && o.storeId == this.storeId;
    }
}
