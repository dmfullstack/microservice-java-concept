package com.tenx.ms.retail.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.common.rest.AbstractAPIController;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.services.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "stock", description = "Stock API")
@RestController("stockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stock")
public class StockController extends AbstractAPIController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService service;

    @ApiOperation(value = "Adds/Update product quantity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quantity successfully updated."),
            @ApiResponse(code = 412, message = "Unable to update quantity. Validation errors."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.POST)
    public void createOrUpdate(
            @ApiParam(name = "storeId",   value = "The store id.")   @PathVariable()            long  storeId,
            @ApiParam(name = "productId", value = "The product id.") @PathVariable()            long  productId,
            @ApiParam(name = "stock",     value = "The stock.")      @Validated @RequestBody    Stock stock) {
        LOGGER.info("Creating or updating stock {}", stock);
        stock.setStoreId(storeId);
        stock.setProductId(productId);
        this.service.createOrUpdate(stock);
    }

    /*
     *  Implemented this optional method to be able to set or update count with an extra parameter in the URL.
     */
    @ApiOperation(value = "Adds/Update product quantity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quantity successfully updated."),
            @ApiResponse(code = 412, message = "Unable to update quantity. Validation errors."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}/{count:\\d+}"}, method = RequestMethod.POST)
    public void createOrUpdate(
            @ApiParam(name = "storeId",   value = "The store id.")   @PathVariable() long storeId,
            @ApiParam(name = "productId", value = "The product id.") @PathVariable() long productId,
            @ApiParam(name = "count",     value = "The amount.")     @PathVariable() long count) {
        LOGGER.info("Creating or updating stock for store {}, product {} and quantity {}", storeId, productId, count);
        Stock stock = new Stock(storeId, productId, count);
        this.service.createOrUpdate(stock);
    }

    /*
     *  Not a part of the requirement, added this method to be able to consult the current stock of a product through the API.
     */
    @ApiOperation(value = "Adds/Update product quantity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quantity successfully updated."),
            @ApiResponse(code = 404, message = "Stock not found"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.GET)
    public Stock get(@ApiParam(name = "storeId",   value = "The store id.")   @PathVariable() long storeId,
                     @ApiParam(name = "productId", value = "The product id.") @PathVariable() long productId) {
        LOGGER.info("Retrieving stock for store {} and product {}", storeId, productId);
        return this.service.getByStoreIdAndProductId(storeId, productId).get();
    }
}
