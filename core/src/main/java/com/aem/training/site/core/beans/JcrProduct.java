package com.aem.training.site.core.beans;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Named;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class JcrProduct {

    @Self
    private Resource resource;

    @ValueMapValue
    @Named(value = "jcr:title")
    private String title;

    @ValueMapValue
    private String price;

    @ValueMapValue
    private String reviews;

    @ValueMapValue
    private String summary;

    @ValueMapValue
    private String features;

    @ValueMapValue
    private String rating;

    private String pagePath;


}
