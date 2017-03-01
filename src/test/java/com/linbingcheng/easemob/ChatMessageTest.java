package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.ChatMessageAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by linbingchneg on 2017/3/2.
 */
public class ChatMessageTest {

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }
    /**
     * 导出聊天记录
     */
    @Test
    public void exportChatMessages(){
        ChatMessageAPI chatMessageAPI = (ChatMessageAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.MESSAGE_CLASS);
        Long limit = 2l;
        ResponseWrapper wrapper = (ResponseWrapper) chatMessageAPI.exportChatMessages(limit,"1","1403164734226");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

}
