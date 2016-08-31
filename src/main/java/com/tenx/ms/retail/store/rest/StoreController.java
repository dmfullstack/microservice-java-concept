package com.tenx.ms.retail.store.rest;

import com.sun.tools.javac.util.List;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
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


@Api(value = "stores", description = "Stores API")
@RestController("storesControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {

    @ApiOperation(value = "Creates a new store.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Store successfully created."),
            @ApiResponse(code = 412, message = "Unable to create store. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> create(
            @ApiParam(name = "store", value = "The store.") @RequestBody Store store) {
        // TODO: Implement.
        throw new NotImplementedException();
    }

    @ApiOperation(value = "Gets all stores.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all stores."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(method = RequestMethod.GET)
    public List<Store> getAll() {
        // TODO: Implement.
        throw new NotImplementedException();
    }

    @ApiOperation(value = "Gets the store matching the given id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved store."),
            @ApiResponse(code = 404, message = "Store not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public Store get(@ApiParam(name = "storeId", value = "The store id.") @PathVariable long storeId) {
        // TODO: Implement.
        throw new NotImplementedException();
    }
}
