package com.tenx.ms.retail.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue
    private long productId;
    private long storeId;
    private String name;
    private String description;
    private String sku;
    private double price;


    public long getProductId() { return this.productId; }

    public void setProductId(long productId) { this.productId = productId; }

    public long getStoreId() { return this.storeId; }

    public void setStoreId(long storeId) { this.storeId = storeId; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public String getSku() { return this.sku; }

    public void setSku(String sku) { this.sku = sku; }

    public double getPrice() { return this.price; }

    public void setPrice(double price) { this.price = price; }
}
