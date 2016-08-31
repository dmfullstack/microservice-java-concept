package com.tenx.ms.retail.stock.rest.dto;


public class Stock {

    private long productId;
    private long storeId;
    private long count;


    public Stock() {

    }

    public Stock(long storeId, long productId, long count) {
        this.productId = productId;
        this.storeId = storeId;
        this.count = count;
    }


    public long getProductId() { return this.productId; }

    public void setProductId(long productId) { this.productId = productId; }

    public long getStoreId() { return this.storeId; }

    public void setStoreId(long storeId) { this.storeId = storeId; }

    public long getCount() { return this.count; }

    public void setCount(long count) { this.count = count; }
}
