package com.tenx.ms.retail.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.apache.commons.io.FileUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class TestStoreController extends AbstractIntegrationTest {

    private final static String API_VERSION = RestConstants.VERSION_ONE;
    private final static String REQUEST_URI = "%s" + API_VERSION + "/stores/";

    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    private ObjectMapper mapper;
    @Value("classpath:storeTests/create-success.json")
    private File createSuccess;

    @Test
    @FlywayTest
    public void getById() {
    }

    @Test
    @FlywayTest
    public void testGetAll() {
        long store1 = createAsset(createSuccess);
        long store2 = createAsset(createSuccess);
        long store3 = createAsset(createSuccess);

        List<Store> stores = getAll();

    }

    @Test
    @FlywayTest
    public void testCreateSuccess() {
        long storeId = createAsset(createSuccess);
        Store store = getStore(storeId);
        assertNotNull("Store cannot be null", store);
        assertEquals("Store ids don' match", store.getStoreId(), storeId);
    }

    // Utility Methods

    private long createAsset(File data) {
        ResourceCreated<Integer> response = request(String.format(REQUEST_URI, basePath()), data, HttpMethod.POST, ResourceCreated.class);
        assert response != null;
        System.out.println(response.getId().getClass());
        return response.getId().longValue();
    }

    private Store getStore(long storeId) {
        return request(String.format(REQUEST_URI, basePath()) + storeId, null, HttpMethod.GET, Store.class);
    }

    private List<Store> getAll() {
        return request(String.format(REQUEST_URI, basePath()), null, HttpMethod.GET, new ArrayList<Store>().getClass());
    }

    private <T> T request(String url, File file, HttpMethod method, Class<T> returnClass) {
        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    url,
                    file != null ? FileUtils.readFileToString(file) : null,
                    method);

            String received = response.getBody();
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            return mapper.readValue(received, returnClass);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }
}
