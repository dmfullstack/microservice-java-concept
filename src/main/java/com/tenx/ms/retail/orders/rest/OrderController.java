package com.tenx.ms.retail.orders.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.common.rest.AbstractAPIController;
import com.tenx.ms.retail.orders.rest.dto.Order;
import com.tenx.ms.retail.orders.services.OrderService;
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


@Api(value = "orders", description = "Orders API")
@RestController("ordersControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController extends AbstractAPIController{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService service;


    @ApiOperation(value = "Places a new order.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order successfully placed."),
            @ApiResponse(code = 412, message = "Unable to place order. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.POST)
    public Order create(
            @ApiParam(name = "storeId",   value = "The store id.") @PathVariable()         long  storeId,
            @ApiParam(name = "order",     value = "The order.")    @Validated @RequestBody Order order) {
        LOGGER.info("Creating order {} in store {}", order, storeId);
        order.setStoreId(storeId);
        return this.service.create(order);
    }

    /*
     *  Optional method not in the original requirement. Gives the ability to check for an order status.
     */
    @ApiOperation(value = "Retrieves a given order by orderId.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order successfully placed."),
            @ApiResponse(code = 412, message = "Unable to place order. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{orderId:\\d+}"}, method = RequestMethod.GET)
    public Order get(
            @ApiParam(name = "storeId", value = "The store id.") @PathVariable() long storeId,
            @ApiParam(name = "orderId", value = "The order id.") @PathVariable() long orderId) {
        return this.service.getByStoreIdAndOrderId(storeId, orderId).get();
    }
}
