package com.linbingcheng.example.activemq.dao;

import com.linbingcheng.example.activemq.model.ActiveMQMapping;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActiveMQMappingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActiveMQMapping record);

    int insertSelective(ActiveMQMapping record);

    ActiveMQMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActiveMQMapping record);

    int updateByPrimaryKey(ActiveMQMapping record);

    List<Map<String,Object>> getConfigMap(String queueName);

    String[] getAllQueueName();
}