package com.tenx.ms.retail.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "stock", description = "Stock API")
@RestController("stockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stock")
public class StockController {

    @ApiOperation(value = "Adds/Update product quantity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quantity successfully updated."),
            @ApiResponse(code = 412, message = "Unable to update quantity. Validation errors."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.POST)
    public ResourceCreated<Long> update(
            @ApiParam(name = "storeId", value = "The store id") @PathVariable() long storeId,
            @ApiParam(name = "productId", value = "The product id") @PathVariable() long productId,
            @Validated @RequestBody Stock stock) {
        stock.setStoreId(storeId);
        stock.setProductId(productId);

        // TODO: Implement.
        throw new NotImplementedException();
    }

    @ApiOperation(value = "Adds/Update product quantity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quantity successfully updated."),
            @ApiResponse(code = 412, message = "Unable to update quantity. Validation errors."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}/{count:\\d+}"}, method = RequestMethod.POST)
    public ResourceCreated<Long> update(
            @ApiParam(name = "storeId",   value = "The store id.")   @PathVariable() long storeId,
            @ApiParam(name = "productId", value = "The product id.") @PathVariable() long productId,
            @ApiParam(name = "count",     value = "The amount.")     @PathVariable() long count) {
        Stock stock = new Stock(storeId, productId, count);

        // TODO: Implement.
        throw new NotImplementedException();
    }
}
