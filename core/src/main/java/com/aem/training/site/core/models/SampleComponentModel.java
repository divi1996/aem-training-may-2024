package com.aem.training.site.core.models;

import com.aem.training.site.core.services.SimpleService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.json.JSONException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class)
public class SampleComponentModel {

   @OSGiService
   ResourceResolverFactory resourceResolverFactory;

   @OSGiService
   private SimpleService simpleService;

   @ValueMapValue
   private String city;

    @PostConstruct
    protected void init() throws PersistenceException {
        ResourceResolver resourceResolver = getResourceResolver();
    }

    private ResourceResolver getResourceResolver() {
        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE,"aemuser");
        ResourceResolver resourceResolver = null;

        try {
            resourceResolver =  resourceResolverFactory.getServiceResourceResolver(authInfo);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        return  resourceResolver;
    }

    public String getWeather(){
        return simpleService.getWeather(city);
    }

}
