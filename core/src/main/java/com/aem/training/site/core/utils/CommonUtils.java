package com.aem.training.site.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);


    public static ResourceResolver getServiceResolver(final String subService,
                                                      final ResourceResolverFactory resolverFactory) {
        ResourceResolver resourceResolver = null;
        if (null != resolverFactory && StringUtils.isNotBlank(subService)) {
            try {
                final Map<String, Object> authMap = new HashMap<>();
                authMap.put(ResourceResolverFactory.SUBSERVICE, subService);
                resourceResolver = resolverFactory.getServiceResourceResolver(authMap);
            } catch (LoginException e) {
                if (log.isErrorEnabled()) {
                    log.error("Utils :: getResolver :: Login Exception while getting the resource resolver "
                            + "from resourceResolverFactory");
                }
            }
        }
        return resourceResolver;
    }

    public static Session getServiceSession(final String subService, final ResourceResolverFactory resourceResolverFactory) {
        ResourceResolver resourceResolver = getServiceResolver(subService, resourceResolverFactory);
        if (resourceResolver != null) {
            return resourceResolver.adaptTo(Session.class);
        } else {
            return null;
        }
    }

}
