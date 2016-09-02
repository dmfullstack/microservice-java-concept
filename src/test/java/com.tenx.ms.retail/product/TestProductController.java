package com.tenx.ms.retail.product;

import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.retail.AbstractRetailTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class TestProductController extends AbstractRetailTest {

    @Value("classpath:product/fail/no-storeId.json")
    private File createNoStoreId;
    @Value("classpath:product/fail/no-name.json")
    private File createNoName;
    @Value("classpath:product/fail/no-description.json")
    private File createNoDescription;
    @Value("classpath:product/fail/no-sku.json")
    private File createNoSku;
    @Value("classpath:product/fail/no-price.json")
    private File createNoPrice;
    @Value("classpath:product/fail/invalid-price.json")
    private File createInvalidPrice;
    @Value("classpath:product/fail/invalid-sku.json")
    private File createInvalidSku;
    @Value("classpath:product/fail/out-of-range-sku.json")
    private File createOutOfRangeSku;

    @Test
    @FlywayTest
    public void testGetAll() {
        // First create a valid store.
        Long storeId = createStore();

        List<Long> responseIds = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            responseIds.add(createProduct(storeId));

        List<Product> products   = getAllProducts(storeId);
        List<Long>    productIds = new ArrayList<>();

        assertEquals("getAll count does not match the amount of stores created", products.size(), responseIds.size());
        products.stream().forEach(x -> productIds.add(x.getProductId()));
        responseIds.stream().forEach(x -> assertTrue("Unable to find one of the created stores", productIds.contains(x)));
    }

    @Test
    @FlywayTest
    public void testCreateSuccessAndGetSuccess() {
        Long storeId = createStore();
        createProduct(storeId);
    }

    @Test
    @FlywayTest
    public void testCreateNoStoreId() {
        Long storeId = createStore();
        createProduct(storeId, createNoStoreId, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoName() {
        Long storeId = createStore();
        createProduct(storeId, createNoName, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoDescription() {
        Long storeId = createStore();
        createProduct(storeId, createNoDescription, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoSku() {
        Long storeId = createStore();
        createProduct(storeId, createNoSku, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoPrice() {
        Long storeId = createStore();
        createProduct(storeId, createNoPrice, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidPrice() {
        Long storeId = createStore();
        createProduct(storeId, createInvalidPrice, HttpStatus.BAD_REQUEST);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidSku() {
        Long storeId = createStore();
        createProduct(storeId, createInvalidSku, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateOutOfRangeSku() {
        Long storeId = createStore();
        createProduct(storeId, createOutOfRangeSku, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidStore() {
        createProduct(123456789, createProductSuccess, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testGetNotFound() {
        Long storeId = createStore();
        getProduct(storeId, 1234567890, HttpStatus.NOT_FOUND);
    }
}
