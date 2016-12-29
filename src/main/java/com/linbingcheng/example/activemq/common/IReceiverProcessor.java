package com.linbingcheng.example.activemq.common;

/**
 * Created by bingchenglin on 2016/12/29.
 */
public interface IReceiverProcessor {

    public void process(String message) throws Exception;

}
