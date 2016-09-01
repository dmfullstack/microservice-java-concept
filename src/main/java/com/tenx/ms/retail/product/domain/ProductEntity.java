package com.tenx.ms.retail.product.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;


@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
@Access(AccessType.FIELD)
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long productId;
    private Long storeId;
    private String name;
    private String description;
    private String sku;
    private BigDecimal price;


    public ProductEntity() { }

    public ProductEntity(long storeId) {
        this.storeId = storeId;
    }


    public Long getProductId() { return this.productId; }
    public Long getStoreId() { return this.storeId; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getSku() { return this.sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPrice() { return this.price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
