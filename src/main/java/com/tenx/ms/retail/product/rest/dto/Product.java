package com.tenx.ms.retail.product.rest.dto;

import com.tenx.ms.commons.validation.constraints.DollarAmount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@ApiModel("Product Model")
public class Product {
    @ApiModelProperty(value = "The Id of the product.", readOnly = true)
    private long productId;

    @ApiModelProperty("The Id of the store the product belongs to.")
    private long storeId;

    @ApiModelProperty("The name of the product.")
    private String name;

    @ApiModelProperty("The product description.")
    private String description;

    @ApiModelProperty("The Stock Keeping Unit.")
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    @Size(min = 5, max = 10)
    private String sku;

    @ApiModelProperty("The product price.")
    @DollarAmount
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
