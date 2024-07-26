package com.aem.training.site.core.servlets;

import com.aem.training.site.core.beans.Product;
import com.aem.training.site.core.services.ProductListingService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(
        service = Servlet.class,
        property = {
                "service.description=Product Servlet",
                "sling.servlet.methods=GET",
                "sling.servlet.paths=/bin/products"
        }
)
public class ProductServlet extends SlingAllMethodsServlet {

    @Reference
    private ProductListingService productListingService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> productList = productListingService.getProductsFromApi();
            response.getWriter().write(new Gson().toJson(productList));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
