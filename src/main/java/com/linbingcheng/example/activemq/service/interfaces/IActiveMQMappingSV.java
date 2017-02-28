package com.linbingcheng.example.activemq.service.interfaces;

import java.util.Map;

/**
 * Created by bingchenglin on 2016/12/28.
 */
public interface IActiveMQMappingSV {

    Map<String,Map<String,Object>> getAllConfig();

    Map<String,Object> getQueueConfig(String queueName);

}
