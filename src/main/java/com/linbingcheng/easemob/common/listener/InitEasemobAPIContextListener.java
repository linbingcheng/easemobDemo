package com.linbingcheng.easemob.common.listener;
/**
 * Created by bingchenglin on 2016/11/28.
 */

import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.utils.RestAPIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitEasemobAPIContextListener implements ServletContextListener{

    private Logger logger = LoggerFactory.getLogger(InitEasemobAPIContextListener.class);
    private String EASEMOB_API_CONTEXT_KEY = "EASEMOB_API_CONTEXT";
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("easemob API context init begin");
        String contextFile = sce.getServletContext().getInitParameter(EASEMOB_API_CONTEXT_KEY);
        EasemobContext.init(contextFile);
        if (!EasemobContext.initialized){
            logger.error("easemob API context init fail");
        }
        logger.debug("easemob API context init end");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        RestAPIUtils.getConncetionMonitor().cancel();
        RestAPIUtils.getConnectionManager().shutdown();
        logger.debug("easemob API context destroy succes");
    }

}
