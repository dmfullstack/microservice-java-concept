package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 *  Abstract class with generic useful methods for any test class. This is in a separate class
 *  since it might be something we may want to add in a common library.
 */
public abstract class AbstractTest extends AbstractIntegrationTest {
    protected final static String API_VERSION = RestConstants.VERSION_ONE;

    protected final RestTemplate template = new TestRestTemplate();

    @Autowired
    protected ObjectMapper mapper;

    protected <T> T request(String url, File file, HttpMethod method, HttpStatus expectedResponse, TypeReference<T> mappingInfo) {
        try {
            String payload = file != null ? FileUtils.readFileToString(file) : null;
            return request(url, payload, method, expectedResponse, mappingInfo);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }

    protected <T> T request(String url, String payload, HttpMethod method, HttpStatus expectedResponse, TypeReference<T> mappingInfo) {
        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    url,
                    payload,
                    method);

            String received = response.getBody();
            assertEquals("HTTP Status code incorrect", expectedResponse, response.getStatusCode());

            if (response.getStatusCode() != HttpStatus.OK)
                return null;

            return mappingInfo == null ? null : mapper.readValue(received, mappingInfo);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }
}
