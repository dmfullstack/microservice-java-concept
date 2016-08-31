package com.tenx.ms.retail.product.rest;

import com.sun.tools.javac.util.List;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "products", description = "Products API")
@RestController("productsControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController {

    @ApiOperation(value = "Creates a new product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product successfully created."),
            @ApiResponse(code = 412, message = "Unable to create product. Validation error."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> create(Product product) {
        // TODO: Implement.
        throw new NotImplementedException();
    }

    @ApiOperation(value = "Gets all products in a given store.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all products in the specified store."),
            @ApiResponse(code = 404, message = "Store not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public List<Product> getAll(@ApiParam(name = "storeId", value = "The store id.") @PathVariable long storeId) {
        // TODO: Implement.
        throw new NotImplementedException();
    }

    @ApiOperation(value = "Gets the product matching the given id in a given store.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the product."),
            @ApiResponse(code = 404, message = "Product or Store not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:^[0-9]*$}"}, method = RequestMethod.GET)
    public Product get(
            @ApiParam(name = "storeId",   value = "The store id.")   @PathVariable long storeId,
            @ApiParam(name = "productId", value = "The product id.") @PathVariable long productId) {
        // TODO: Implement.
        throw new NotImplementedException();
    }
}
