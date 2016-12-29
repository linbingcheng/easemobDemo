package com.linbingcheng.util.activemq;

import com.linbingcheng.example.activemq.common.IReceiverProcessor;

/**
 * Created by bingchenglin on 2016/12/29.
 */
public class TestReceiverProcessor implements IReceiverProcessor {
    @Override
    public void process(String message) throws Exception {
        System.out.println(message);
    }
}
