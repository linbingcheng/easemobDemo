package com.linbingcheng.easemob.common.listener; /**
 * Created by bingchenglin on 2016/11/28.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitEasemobAPIContextListener implements ServletContextListener{

    private Logger logger = LoggerFactory.getLogger(InitEasemobAPIContextListener.class);
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {

    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
