package com.tenx.ms.retail.store;

import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.retail.AbstractRetailTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.store.rest.dto.Store;
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
public class TestStoreController extends AbstractRetailTest {

    @Value("classpath:store/fail/create.json")
    private File createFail;


    @Test
    @FlywayTest
    public void testGetAll() {
        List<Long> responseIds = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            responseIds.add(createStore());

        List<Store> stores   = getAllStores();
        List<Long>  storeIds = new ArrayList<>();

        assertEquals("getAll count does not match the amount of stores created", stores.size(), responseIds.size());
        stores.stream().forEach(x -> storeIds.add(x.getStoreId()));
        responseIds.stream().forEach(x -> assertTrue("Unable to find one of the created stores", storeIds.contains(x)));
    }

    @Test
    @FlywayTest
    public void testCreateSuccessAndGetSuccess() {
        createStore();
    }

    @Test
    @FlywayTest
    public void testCreateInvalidData() {
        createStore(createFail, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testGetNotFound() {
        getStore(1234567890, HttpStatus.NOT_FOUND);
    }
}
