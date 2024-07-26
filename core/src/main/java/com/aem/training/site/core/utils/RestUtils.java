package com.aem.training.site.core.utils;

import com.aem.training.site.core.beans.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestUtils {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    /**
     * Executes a GET request to the specified API URL.
     *
     * @param apiUrl API URL for the GET request
     * @return {@link ClientResponse} containing the API response
     */
    public static ClientResponse executeGetRequest(final String apiUrl) {
        final HttpGet httpGet = new HttpGet(apiUrl);
        log.debug("GET Request URL :: {}", apiUrl);
        httpGet.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        return executeRequest(httpGet);
    }

    /**
     * Executes the HTTP request using {@link HttpClients}.
     *
     * @param request HTTP request to execute
     * @return {@link ClientResponse} containing the API response
     */
    private static ClientResponse executeRequest(final HttpUriRequest request) {
        final ClientResponse clientResponse = new ClientResponse();
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            final HttpEntity httpEntity = httpResponse.getEntity();
            clientResponse.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            if (httpEntity != null) {
                final String result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                clientResponse.setData(result);
                log.debug("Response from execute request {}", result);
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("CommonUtils :: executeRequest() :: IO Exception while getting the client response.");
            }
        }
        return clientResponse;
    }
}
