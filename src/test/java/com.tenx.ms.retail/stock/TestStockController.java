package com.tenx.ms.retail.stock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.retail.AbstractRetailTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class TestStockController extends AbstractRetailTest {
    private static final long AMOUNT_OF_UPDATES = 5;

    @Value("classpath:stock/success/no-store.json")
    private File createSuccessNoStore;
    @Value("classpath:stock/success/no-product.json")
    private File createSuccessNoProduct;
    @Value("classpath:stock/success/only-count.json")
    private File createSuccessOnlyCount;
    @Value("classpath:stock/fail/no-count.json")
    private File createFailNoCount;

    @Test
    @FlywayTest
    public void testCreateGetAndUpdateSuccess() {
        Long storeId   = createStore();
        Long productId = createProduct(storeId);

        for (long i = 0; i < AMOUNT_OF_UPDATES; i++) {
            String payload = generateJsonRequest(storeId, productId, i);
            Stock  stock   = createStock(storeId, productId, payload);
            assertEquals("The stock amounts don't match", i, stock.getCount());
        }
    }

    @Test
    @FlywayTest
    public void testCreateByOnlyParams() {
        Long storeId   = createStore();
        Long productId = createProduct(storeId);
        createStockByOnlyParams(storeId, productId, 10);
    }

    @Test
    @FlywayTest
    public void testCreateNoStore() {
        // This request is successful (HttpStatus.OK) even without a store because the store is sent out as a parameter in the URL.
        Long storeId   = createStore();
        Long productId = createProduct(storeId);
        createStock(storeId, productId, createSuccessNoStore, HttpStatus.OK);
    }

    @Test
    @FlywayTest
    public void testCreateNoProduct() {
        // This request is successful (HttpStatus.OK) even without a product because the store is sent out as a parameter in the URL.
        Long storeId   = createStore();
        Long productId = createProduct(storeId);
        createStock(storeId, productId, createSuccessNoProduct, HttpStatus.OK);
    }

    @Test
    @FlywayTest
    public void testCreateOnlyCount() {
        // This request is successful (HttpStatus.OK) only by sending the stock count because both the storeId and the productId are
        // provided in the URL.
        Long storeId   = createStore();
        Long productId = createProduct(storeId);
        createStock(storeId, productId, createSuccessOnlyCount, HttpStatus.OK);
    }

    @Test
    @FlywayTest
    public void testCreateNoCount() {
        Long storeId   = createStore();
        Long productId = createProduct(storeId);
        createStock(storeId, productId, createFailNoCount, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testGetNotFound() {
        getStock(INVALID_ID, INVALID_ID, HttpStatus.NOT_FOUND);
    }



    private Stock createStock(long storeId, long productId, String payload) {
        createStock(storeId, productId, payload, HttpStatus.OK);
        return getStock(storeId, productId);
    }

    private Stock createStockByOnlyParams(long storeId, long productId, long count) {
        createStock(storeId, productId, count, HttpStatus.OK);
        return getStock(storeId, productId);
    }

    private Stock getStock (long storeId, long productId) {
        Stock stock = getStock(storeId, productId, HttpStatus.OK);
        assertNotNull("Product cannot be null", stock);
        assertEquals("Store ids don't match",   stock.getStoreId(),   storeId);
        assertEquals("Product ids don't match", stock.getProductId(), productId);

        return stock;
    }

    private void createStock(long storeId, long productId, long count, HttpStatus expectedStatus) {
        request(String.format(STOCK_REQUEST_URI, basePath()) + storeId + "/" + productId + "/" + count, (String)null, HttpMethod.POST, expectedStatus, null);
    }

    private void createStock(long storeId, long productId, String data, HttpStatus expectedStatus) {
        request(String.format(STOCK_REQUEST_URI, basePath()) + storeId + "/" + productId, data, HttpMethod.POST, expectedStatus, null);
    }

    private void createStock(long storeId, long productId, File data, HttpStatus expectedStatus) {
        request(String.format(STOCK_REQUEST_URI, basePath()) + storeId + "/" + productId, data, HttpMethod.POST, expectedStatus, null);
    }

    private Stock getStock(long storeId, long productId, HttpStatus expectedStatus) {
        return request(String.format(STOCK_REQUEST_URI, basePath()) + storeId + "/" + productId, (String) null, HttpMethod.GET, expectedStatus, new TypeReference<Stock>() {});
    }

    /**
     *  Serializes the a map with the same stock structure to make a Json request. Done this way to be able to send dynamic data.
     * @param storeId The Store Id
     * @param productId The Product Id
     * @param count The stock count
     * @return Serialized Json representing the data of a stock.
     */
    private String generateJsonRequest(long storeId, long productId, long count) {
        Map<String, Long> stock = new HashMap<>();
        stock.put("store_id",   storeId);
        stock.put("product_id", productId);
        stock.put("count",      count);

        JSONObject json = new JSONObject(stock);
        return json.toString();
    }
}
