package com.linbingcheng.example.activemq.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by bingchenglin on 2016/12/28.
 */
@Service
public interface IActiveMQMappingSV {


    Map<String,Map<String,Object>> getAllConfig();

}
