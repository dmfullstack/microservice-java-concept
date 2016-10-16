package com.tenx.ms.retail.product.rest.dto;

import com.tenx.ms.commons.validation.constraints.DollarAmount;
import com.tenx.ms.retail.common.util.AllowConverterAccess;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@ApiModel("Product Model")
public class Product {
    @ApiModelProperty(value = "The Id of the product.", readOnly = true)
    @AllowConverterAccess
    private Long productId;

    @ApiModelProperty(value = "The Id of the store the product belongs to.", required = true)
    @NotNull
    private Long storeId;

    @ApiModelProperty(value = "The name of the product.", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "The product description.", required = true)
    @NotNull
    private String description;

    @ApiModelProperty(value = "The Stock Keeping Unit.", required = true)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    @Size(min = 5, max = 10)
    @NotNull
    private String sku;

    @ApiModelProperty(value = "The product price.", required = true)
    @DollarAmount
    @NotNull
    private BigDecimal price;


    public Long getProductId() { return this.productId; }

    public Long getStoreId() { return this.storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getSku() { return this.sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPrice() { return this.price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
