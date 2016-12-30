package com.linbingcheng.example.activemq.service.impl;

import com.linbingcheng.example.activemq.dao.ActiveMQMappingMapper;
import com.linbingcheng.example.activemq.service.interfaces.IActiveMQMappingSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bingchenglin on 2016/12/28.
 */
@Service
public class ActiveMQMappingSVImpl implements IActiveMQMappingSV {

    @Autowired
    private ActiveMQMappingMapper activeMQMappingMapper;

    public Map<String, Map<String, Object>> getAllConfig() {
        Map<String, Map<String, Object>> resultMap = new HashMap<String,  Map<String, Object>>();
        String[] allQueueName = activeMQMappingMapper.getAllQueueName();
        for (int i =0;i<allQueueName.length;i++){
            resultMap.put(allQueueName[i],getQueueConfig(allQueueName[i]));
        }
        return resultMap;
    }

    public Map<String,Object> getQueueConfig(String queueName){
        List<Map<String, Object>> regionMap = activeMQMappingMapper.getConfigMap("queueName");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Map<String, Object> map : regionMap) {
            String mapping_name = null;
            Object mapping_value = null;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("mapping_name".equals(entry.getKey())) {
                    mapping_name = (String) entry.getValue();
                } else if ("mapping_value".equals(entry.getKey())) {
                    mapping_value =  entry.getValue();
                }
            }
            resultMap.put(mapping_name, mapping_value);
        }
        return resultMap;
    }
}
