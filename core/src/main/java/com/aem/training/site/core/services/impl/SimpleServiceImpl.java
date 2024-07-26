package com.aem.training.site.core.services.impl;

import com.aem.training.site.core.services.ProductListingService;
import com.aem.training.site.core.services.SimpleService;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = SimpleService.class, immediate = true)
@Designate(ocd = SimpleServiceImpl.WeatherConfig.class)
public class SimpleServiceImpl implements SimpleService {

    @Reference
    ProductListingService productListingService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    String apiUrl = "" ;
    @Override
    public String getWeather(String city) {
        /**
         *
          */
        return "temperature is 20 degrees";
    }

    @Activate
    @Modified
    protected void activate(WeatherConfig weatherConfig) {
        apiUrl = weatherConfig.api_url();
    }

    @Deactivate
    protected void deactivate() {
        logger.info("Simple service is deactivated");
    }

    @ObjectClassDefinition(name = "Simple Service Config")
    @interface WeatherConfig {

        @AttributeDefinition(name = "API URL", description = "This field is meant to provide api url for weather api")
        String api_url() default StringUtils.EMPTY;

    }

}
