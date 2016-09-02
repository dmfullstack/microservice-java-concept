package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Abstract class with utilities more specific to this project.
 */
public abstract class AbstractRetailTest extends AbstractTest {

    /*
     * The URLs for all APIs in the project.
     */

    protected static String STORE_REQUEST_URI   = "%s" + API_VERSION + "/stores/";
    protected static String PRODUCT_REQUEST_URI = "%s" + API_VERSION + "/products/";

    /*
     * The payloads of all successful creation of entities which are the ones that can be reused in all tests.
     */

    @Value("classpath:store/success/create.json")
    protected File createStoreSuccess;
    @Value("classpath:product/success/create.json")
    protected File createProductSuccess;

    // Store Helpers

    protected Long createStore() {
        Long  storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Store store   = getStore(storeId, HttpStatus.OK);
        assertNotNull("Store cannot be null", store);
        assertEquals("Store ids don't match", store.getStoreId(), storeId);
        return storeId;
    }

    protected Long createStore(File data, HttpStatus expectedStatus) {
        ResourceCreated<Long> response = request(String.format(STORE_REQUEST_URI, basePath()), data, HttpMethod.POST, expectedStatus, new TypeReference<ResourceCreated<Long>>() {});
        assert response != null;
        return response.getId();
    }

    protected Store getStore(long storeId, HttpStatus expectedStatus) {
        return request(String.format(STORE_REQUEST_URI, basePath()) + storeId, null, HttpMethod.GET, expectedStatus, new TypeReference<Store>() {});
    }

    protected List<Store> getAllStores() {
        return request(String.format(STORE_REQUEST_URI, basePath()), null, HttpMethod.GET, HttpStatus.OK, new TypeReference<List<Store>>() {});
    }


    // Product Helpers

    protected Long createProduct(long storeId) {
        Long    productId = createProduct(storeId, createProductSuccess, HttpStatus.OK);
        Product product   = getProduct(storeId, productId, HttpStatus.OK);
        assertNotNull("Product cannot be null", product);
        assertEquals("Product ids don't match", product.getProductId(), productId);
        return productId;
    }

    protected Long createProduct(long storeId, File data, HttpStatus expectedStatus) {
        ResourceCreated<Long> response = request(String.format(PRODUCT_REQUEST_URI, basePath()) + storeId, data, HttpMethod.POST, expectedStatus, new TypeReference<ResourceCreated<Long>>() {});
        assert response != null;
        return response.getId();
    }

    protected Product getProduct(long storeId, long productId, HttpStatus expectedStatus) {
        return request(String.format(PRODUCT_REQUEST_URI, basePath()) + storeId + "/" + productId, null, HttpMethod.GET, expectedStatus, new TypeReference<Product>() {});
    }

    protected List<Product> getAllProducts(long storeId) {
        return request(String.format(PRODUCT_REQUEST_URI, basePath()) + storeId, null, HttpMethod.GET, HttpStatus.OK, new TypeReference<List<Product>>() {});
    }
}
