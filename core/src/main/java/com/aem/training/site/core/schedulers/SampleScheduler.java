package com.aem.training.site.core.schedulers;

import org.osgi.service.component.annotations.Component;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component(service=Runnable.class)
//@Designate(ocd = SampleScheduler.Config.class)
//public class SampleScheduler implements Runnable {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @ObjectClassDefinition(name="Sample scheduled task",
//                           description = "Simple demo for cron-job like task with properties")
//    public static @interface Config {
//
//        @AttributeDefinition(name = "Cron-job expression")
//        String scheduler_expression() default "0 0 0/1 1/1 * ? *";
//    }
//
//    @Override
//    public void run() {
//        logger.info("**** Scheduler run achieved for Sample Scheduler *****");
//    }
//}
