package com.tenx.ms.retail.orders.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.orders.rest.dto.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "orders", description = "Orders API")
@RestController("ordersControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController {

    @ApiOperation(value = "Places a new order.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order successfully placed."),
            @ApiResponse(code = 412, message = "Unable to place order. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.POST)
    public ResourceCreated<Long> create(
            @ApiParam(name = "storeId",   value = "The store id.") @PathVariable() long  storeId,
            @ApiParam(name = "order",     value = "The order.")    @RequestBody    Order order) {
        // TODO: Implement.
        throw new NotImplementedException();
    }
}
