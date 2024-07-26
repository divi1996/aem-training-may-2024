package com.aem.training.site.core.workflow;

import com.aem.training.site.core.services.ProductListingService;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.json.JSONException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = WorkflowProcess.class,
        property = {"process.label= Sample Workflow"})
public class SampleProcess implements WorkflowProcess{

    @Reference
    ProductListingService productListingService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        String payload =  (String)workItem.getWorkflowData().getPayload();

        try {
            productListingService.getProductsFromApi();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
