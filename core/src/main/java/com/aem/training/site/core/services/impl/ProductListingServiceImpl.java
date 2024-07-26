package com.aem.training.site.core.services.impl;

import com.aem.training.site.core.beans.ClientResponse;
import com.aem.training.site.core.beans.Product;
import com.aem.training.site.core.services.ProductListingService;
import com.aem.training.site.core.services.SimpleService;
import com.aem.training.site.core.utils.RestUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component(service = ProductListingService.class, immediate = true)
@Designate(ocd = ProductListingServiceImpl.Config.class)
public class ProductListingServiceImpl implements ProductListingService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String apiUrl = StringUtils.EMPTY;
    @Activate
    @Modified
    protected void activate(Config config) {
        apiUrl = config.weather_api_url();
        logger.debug("Search service activated successfully");
    }

    @Deactivate
    protected void deactivate() {logger.debug("search service got deactivated");
    }


    @Override
    public List<Product> getProductsFromApi() throws JSONException {
        ClientResponse clientResponse = RestUtils.executeGetRequest(apiUrl);
        JSONArray jsonArray = new JSONArray(clientResponse.getData());
        List<Product> productList = new ArrayList<>();
        for (int i=0; i< jsonArray.length(); i++) {
            Product product = new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(i)),Product.class);
            productList.add(product);
        }
        return productList;
    }

    /**
     * Interface config
     */
    @ObjectClassDefinition(name = "Weather config")
    @interface Config {

        @AttributeDefinition(name = "api HOST name",
                description = "api HOST name",
                type = AttributeType.STRING)
        String weather_api_url();
    }

}
