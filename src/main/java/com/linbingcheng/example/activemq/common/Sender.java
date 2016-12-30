package com.linbingcheng.example.activemq.common;

import com.linbingcheng.example.activemq.service.interfaces.IActiveMQMappingSV;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Map;

/**
 * Created by bingchenglin on 2016/12/26.
 */
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Resource
    private ActiveMQConnectionFactory connectionFactory;
    @Resource
    private IActiveMQMappingSV service;
    private Connection connection;
    private Destination senderDest;
    private Session session;
    private MessageProducer producer;
    private Map<String,Object> config;

    private Boolean isTransaction = true;
    private String queueName;

    public Sender(String queueName) {
        this.queueName = queueName;
       }

    private void init(){
        try {
            config = service.getQueueConfig(queueName);
            isTransaction = Boolean.valueOf((String) config.get(ActiveMQContext.SESSION_TRANSACTION_SWITCH));
            connection = connectionFactory.createConnection();
            connection.start();
            senderDest = new ActiveMQQueue((String) config.get(ActiveMQContext.QUEUE_DESTINATION));
            session = connection.createSession(isTransaction, (Integer) config.get(ActiveMQContext.SESSION_TYPE));
            producer = session.createProducer(senderDest);
        } catch (Exception e) {
            logger.error("ActiveMqProducer init fail:"+e.getMessage(),e);
        }
    }


    public void sendMessage(String msg) {
        init();
        try{
            Message message = session.createTextMessage(msg);
            producer.send(message);
            if(isTransaction){
                session.commit();
            }
        }catch(Exception e){
            logger.error("发送消息至"+queueName+"失败:"+ e.getMessage(),e);
            try {
                session.rollback();
            } catch (Exception ex) {
                logger.error("重新发送消息至"+queueName+"失败:"+ ex.getMessage(),ex);
            }
        }
        destroy();
    }



    private void destroy(){
        try{
            producer.close();
            producer = null;
            session.close();
            session = null;
            connection.stop();
            connection.close();
        }catch (Exception e){
            logger.error("Producer destroy fail:"+ e.getMessage(),e);
        }
    }



}
