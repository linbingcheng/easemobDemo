package com.linbingcheng.example.activemq.common;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Map;

/**
 * Created by bingchenglin on 2016/12/29.
 */
public class Receiver implements Runnable,MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Resource
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Destination receiverDest;
    private Session session;
    private MessageConsumer receiver;
    private Map<String,Object> config;
    private IReceiverProcessor processor;

    private Boolean isTransaction = true;
    private String queueName;


    public Receiver(String queueName) {
        this.queueName = queueName;
    }

    private void receive(){
        try{
            config = ActiveMQContext.MAPPINGS.get(queueName);
            isTransaction = Boolean.valueOf((String) config.get(ActiveMQContext.SESSION_TRANSACTION_SWITCH));
            connection = connectionFactory.createConnection();
            connection.start();
            receiverDest = new ActiveMQQueue((String) config.get(ActiveMQContext.QUEUE_DESTINATION));
            session = connection.createSession(isTransaction, (Integer) config.get(ActiveMQContext.SESSION_TYPE));
            receiver = session.createConsumer(receiverDest);
            processor = (IReceiverProcessor) Class.forName((String) config.get(ActiveMQContext.RECEIVER_PROCESSOR)).newInstance();
            receiver.setMessageListener(this);
        }catch (Exception e){
            logger.error("ConsumerThread init fail!"+e.getMessage(),e);
        }
    }


    public void run() {
        receive();
    }

    public void onMessage(Message message) {
        try {
            TextMessage tm = (TextMessage) message;
            String msg = tm.getText();
            processor.process(msg);
            if(isTransaction){
                session.commit();
            }
        } catch (Exception e) {
            logger.error("ConsumerThread process fail!" + e.getMessage(), e);
        }
    }
}
