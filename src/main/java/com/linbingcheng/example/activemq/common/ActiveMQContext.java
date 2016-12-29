package com.linbingcheng.example.activemq.common;

import com.linbingcheng.example.activemq.service.impl.ActiveMQMappingSVImpl;
import com.linbingcheng.example.activemq.service.interfaces.IActiveMQMappingSV;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bingchenglin on 2016/12/26.
 */
public class ActiveMQContext {


    public final static Map<String,Map<String,Object>> MAPPINGS = new HashMap<String, Map<String,Object>>();
    public static final String QUEUE_DESTINATION = "queue.destination";
    public static final String SESSION_TRANSACTION_SWITCH="transaction.switch";
    public static final String SESSION_TYPE="transaction.type";
    public static final String RECEIVER_PROCESSOR = "receiver.processor.class";

    static {
        IActiveMQMappingSV service = new ActiveMQMappingSVImpl();
        MAPPINGS.putAll(service.getAllConfig());
    }

}
