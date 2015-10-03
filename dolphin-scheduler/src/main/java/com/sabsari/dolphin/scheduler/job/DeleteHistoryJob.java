package com.sabsari.dolphin.scheduler.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sabsari.dolphin.core.history.service.HistoryService;

@Component
public class DeleteHistoryJob {
    
    @Autowired
    private HistoryService historyService;
    
    private final static Logger logger = LoggerFactory.getLogger(DeleteHistoryJob.class);
    
    @Scheduled(fixedRate=1000)        // every 02:00 AM
    public void delete_AuthenticationHistory() {
    	logger.info("delete_AuthenticationHistory start!");        
        int count = 0;//historyService.deleteAuthenticationHistory();
        logger.info("{} entries of AuthenticationHistory deleted.", count);
        logger.info("delete_AuthenticationHistory end!");
    }
    
//    @Scheduled(cron="0 30 2 * * ?")        // every 02:30 AM
    public void delete_DeleteUserHistory() {
        logger.info("delete_DeleteUserHistory start!");
        int count = historyService.deleteDeleteUserHistory();
        logger.info("{} entries of DeleteUserHistory deleted.", count);        
        logger.info("delete_DeleteUserHistory end!");
    }
    
//    @Scheduled(cron="0 0 3 * * ?")        // every 03:00 AM
    public void delete_TokenHistory() {
        logger.info("delete_TokenHistory start!");
        int count = historyService.deleteTokenHistory();
        logger.info("{} entries of TokenHistory deleted.", count);        
        logger.info("delete_TokenHistory end!");
    }
    
//    @Scheduled(cron="0 30 3 * * ?")        // every 03:30 AM
    public void delete_UserHistory() {
        logger.info("delete_UserHistory start!");
        int count = historyService.deleteUserHistory();
        logger.info("{} entries of UserHistory deleted.", count);        
        logger.info("delete_UserHistory end!");
    }
}
