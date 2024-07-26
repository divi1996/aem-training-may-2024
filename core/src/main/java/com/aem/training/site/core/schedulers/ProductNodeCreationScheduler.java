package com.aem.training.site.core.schedulers;

import com.aem.training.site.core.beans.Product;
import com.aem.training.site.core.services.ProductListingService;
import com.aem.training.site.core.utils.CommonUtils;
import com.day.cq.commons.jcr.JcrUtil;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;
import java.util.Objects;

@Component(service=Runnable.class)
@Designate(ocd = ProductNodeCreationScheduler.Config.class)
public class ProductNodeCreationScheduler implements Runnable{

    @Reference
    ProductListingService productListingService;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ObjectClassDefinition(name="A scheduled task for products")
    public static @interface Config {

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "*/30 * * * * ?";
    }

    @Override
    public void run() {

        try(ResourceResolver resourceResolver = CommonUtils.getServiceResolver("aemSystemUser",resourceResolverFactory)) {
            List<Product> productList = productListingService.getProductsFromApi();
            Session session = resourceResolver.adaptTo(Session.class);

            Node contentNode = session.getNode("/var/fakeProducts");

            for (Product product : productList) {
                if (Objects.nonNull(contentNode)) {
                    Node node = JcrUtils.getOrAddNode(contentNode, String.valueOf(product.getId()));
                    node.setProperty("title",product.getTitle());
                    session.save();
                }
            }



        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }


//        try {
//
//
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }

    }


}
