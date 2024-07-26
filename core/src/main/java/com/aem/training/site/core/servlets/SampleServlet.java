package com.aem.training.site.core.servlets;

import com.aem.training.site.core.beans.JcrProduct;
import com.aem.training.site.core.beans.Student;
import com.aem.training.site.core.services.SimpleService;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@Component(
        service = Servlet.class,
        property = {
                "service.description=Sample Servlet",
                "sling.servlet.methods=GET",
                "sling.servlet.paths=/bin/sample"
        }
)
public class SampleServlet extends SlingSafeMethodsServlet {

    @Reference
    private QueryBuilder queryBuilder;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        List<JcrProduct> resultedProducts = new ArrayList<>();

        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

        Query query = queryBuilder.createQuery(PredicateGroup.create(getQuery()),session);
        Iterator<Resource> resultedResources = query.getResult().getResources();

        for (Iterator<Resource> it = resultedResources; it.hasNext(); ) {
            Resource resource = it.next();
            Page page = pageManager.getPage(resource.getPath());
            ValueMap valueMap = page.getProperties();
            String productMaster = valueMap.get("cq:productMaster", String.class);

            Resource productResource = resourceResolver.getResource(productMaster);
            JcrProduct jcrProduct = productResource.adaptTo(JcrProduct.class);
            resultedProducts.add(jcrProduct);
        }

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(resultedProducts));
    }

    private Map<String,String> getQuery() {
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("path","/content/we-retail/us/en/products");
        queryMap.put("type","cq:Page");
        queryMap.put("1_property","jcr:content/cq:tags");
        queryMap.put("1_property.value","we-retail:gender/men");
        queryMap.put("2_property","jcr:content/jcr:language");
        queryMap.put("2_property.value","en");
        queryMap.put("p.limit","100");

        return queryMap;
    }
}
