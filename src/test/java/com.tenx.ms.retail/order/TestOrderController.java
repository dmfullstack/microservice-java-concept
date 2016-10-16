package com.tenx.ms.retail.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.retail.AbstractRetailTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.order.domain.OrderStatus;
import com.tenx.ms.retail.order.rest.dto.Order;
import org.apache.commons.io.FileUtils;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class TestOrderController extends AbstractRetailTest {

    private static final int  PRODUCTS_TO_CREATE = 5;
    private static final long PRODUCT_COUNT      = 10;

    @Value("classpath:order/success/create.json")
    private File orderData;
    @Value("classpath:order/fail/no-date.json")
    private File createFailNoDate;
    @Value("classpath:order/fail/no-email.json")
    private File createFailNoEmail;
    @Value("classpath:order/fail/no-firstName.json")
    private File createFailNoFirstName;
    @Value("classpath:order/fail/no-lastName.json")
    private File createFailNoLastName;
    @Value("classpath:order/fail/no-orderDate.json")
    private File createFailNoOrderDate;
    @Value("classpath:order/fail/no-phone.json")
    private File createFailNoPhone;
    @Value("classpath:order/fail/no-products.json")
    private File createFailNoProducts;
    @Value("classpath:order/fail/no-status.json")
    private File createFailNoStatus;
    @Value("classpath:order/fail/no-storeId.json")
    private File createFailNoStoreId;

    @Value("classpath:order/fail/invalid-date.json")
    private File createFailInvalidDate;
    @Value("classpath:order/fail/invalid-email.json")
    private File createFailInvalidEmail;
    @Value("classpath:order/fail/invalid-firstName.json")
    private File createFailInvalidFirstName;
    @Value("classpath:order/fail/invalid-lastName.json")
    private File createFailInvalidLastName;
    @Value("classpath:order/fail/invalid-orderStatus.json")
    private File createFailInvalidOrderStatus;
    @Value("classpath:order/fail/invalid-phoneNumber.json")
    private File createFailInvalidPhoneNumber;

    @Test
    @FlywayTest
    public void testCreateAndGetOrderSuccess() {

        // Test Creation.
        Long       storeId    = createStore();
        List<Long> productIds = new ArrayList<>();

        for (int i = 0; i < PRODUCTS_TO_CREATE; i++) {
            productIds.add(createProduct(storeId));
        }

        String payload = generateJsonRequest(storeId, productIds, PRODUCT_COUNT);
        Order  order   = placeOrder(storeId, payload, HttpStatus.OK);
        validateOrder(order, storeId, productIds);

        // Fetch the created order and validate.
        Order fetched = getOrder(storeId, order.getOrderId(), HttpStatus.OK);
        validateOrder(fetched, storeId, productIds);
    }

    @Test
    @FlywayTest
    public void testCreateNoDate() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoDate, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoEmail() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoEmail, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoFirstName() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoFirstName, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoLastName() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoLastName, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoOrderDate() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoOrderDate, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoPhone() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoPhone, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoStatus() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoStatus, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoStoreId() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoStoreId, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateNoProducts() {
        Long storeId = createStore();
        placeOrder(storeId, createFailNoProducts, HttpStatus.BAD_REQUEST);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidDate() {
        Long storeId = createStore();
        placeOrder(storeId, createFailInvalidDate, HttpStatus.BAD_REQUEST);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidEmail() {
        Long storeId = createStore();
        placeOrder(storeId, createFailInvalidEmail, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidFirstName() {
        Long storeId = createStore();
        placeOrder(storeId, createFailInvalidFirstName, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidLastName() {
        Long storeId = createStore();
        placeOrder(storeId, createFailInvalidLastName, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidOrderStatus() {
        Long storeId = createStore();
        placeOrder(storeId, createFailInvalidOrderStatus, HttpStatus.BAD_REQUEST);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidPhoneNumber() {
        Long storeId = createStore();
        placeOrder(storeId, createFailInvalidPhoneNumber, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testGetNotFound() {
        getStore(INVALID_ID, HttpStatus.NOT_FOUND);
    }

    @Test
    public void testOrderStatus() {
        assertEquals("Strings mismatch", OrderStatus.fromString("Ordered"), OrderStatus.ORDERED);
        assertEquals("Strings mismatch", OrderStatus.fromString("Shipped"), OrderStatus.SHIPPED);
        assertEquals("Strings mismatch", OrderStatus.fromString("Packing"), OrderStatus.PACKING);

        assertEquals("Strings mismatch", "Ordered", OrderStatus.ORDERED.toString());
        assertEquals("Strings mismatch", "Shipped", OrderStatus.SHIPPED.toString());
        assertEquals("Strings mismatch", "Packing", OrderStatus.PACKING.toString());
    }


    private void validateOrder(Order order, Long storeId, List<Long> productIds) {
        assertEquals("Store ids mismatch", storeId, order.getStoreId());
        assertEquals("Product count mismatch", productIds.size(), order.getProducts().size());

        List<Long> obtainedProductIds = new ArrayList<>();

        order.getProducts().stream().forEach(x -> obtainedProductIds.add(x.getProductId()));
        order.getProducts().stream().forEach(x -> assertEquals("Product details count mismatch", x.getCount(), PRODUCT_COUNT));
        obtainedProductIds.stream().forEach(x -> assertTrue("Unable to find one of the created products", productIds.contains(x)));
    }

    private Order placeOrder(long storeId, String data, HttpStatus expectedStatus) {
        return request(String.format(ORDERS_REQUEST_URI, basePath()) + storeId, data, HttpMethod.POST, expectedStatus, new TypeReference<Order>() {});
    }

    private Order placeOrder(long storeId, File data, HttpStatus expectedStatus) {
        return request(String.format(ORDERS_REQUEST_URI, basePath()) + storeId, data, HttpMethod.POST, expectedStatus, new TypeReference<Order>() {});
    }

    protected Order getOrder(long storeId, long orderId, HttpStatus expectedStatus) {
        return request(String.format(ORDERS_REQUEST_URI, basePath()) + storeId + "/" + orderId, (String) null, HttpMethod.GET, expectedStatus, new TypeReference<Order>() {});
    }

    /**
     *  Loads a Json request into a Map, updates the data with the ids obtained by creating Stores and Products
     *  and serializes it back to a String.
     * @param storeId The Store Id
     * @param productIds The list of product ids generated
     * @return Serialized Json representing the data of a stock.
     */
    private String generateJsonRequest(long storeId, List<Long> productIds, long count) {
        Map data = null;

        try {
            String payload = FileUtils.readFileToString(orderData);
            data = mapper.readValue(payload, Map.class);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        List<Map> products = new ArrayList<>();

        for (Long productId : productIds) {
            Map<String, Long> orderDetails = new HashMap<>();
            orderDetails.put("product_id", productId);
            orderDetails.put("count", count);
            products.add(orderDetails);
        }

        data.replace("products", products);
        data.replace("storeId", storeId);

        JSONObject json = new JSONObject(data);
        return json.toString();
    }
}
