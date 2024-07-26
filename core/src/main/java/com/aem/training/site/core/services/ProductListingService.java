package com.aem.training.site.core.services;

import com.aem.training.site.core.beans.Product;
import org.json.JSONException;

import java.util.List;

public interface ProductListingService {
    List<Product> getProductsFromApi() throws JSONException;
}
