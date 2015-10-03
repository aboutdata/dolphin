package com.sabsari.dolphin.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sabsari.dolphin.scheduler.config.SchedulerConfig;;

public class SchedulerApp {
	
    private final static Logger logger = LoggerFactory.getLogger(SchedulerApp.class);
    
    public static void main( String[] args ) {
        if (!isValidArgs(args)) {
            logger.info("Illegal argument at MainApp.main.");
            throw new IllegalArgumentException("The args of the main must be [dev|awsdev|real].");
        }        
        String profile = args[0];
        
        logger.info("TaskScheduler start!");
        @SuppressWarnings("resource")
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(profile);
        context.register(SchedulerConfig.class);
        context.refresh();        
    }
    
    private static boolean isValidArgs(String[] args) {
        if (args == null || args.length != 1) {
            return false;
        }
        
        if (args[0].equals("dev")) {
            return true;
        } 
        else if (args[0].equals("awsdev")) {
            return true;
        } 
        else if (args[0].equals("real")) {
            return true;
        } 
        else {
            return false;
        }
    }
}
