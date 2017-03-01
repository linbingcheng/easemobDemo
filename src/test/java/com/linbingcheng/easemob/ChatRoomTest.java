package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.ChatRoomAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.body.ChatRoomBody;
import com.linbingcheng.easemob.common.body.ModifyChatRoomBody;
import com.linbingcheng.easemob.common.body.UserNamesBody;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bingchenglin on 2017/3/1.
 */
public class ChatRoomTest{

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }


    /**
     * 创建聊天室接口测试
     */
    @Test
    public void createChatRoom() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        String[] members = new String[]{"yonghuming6", "yonghuming7"};
        long maxs = 50;
        ChatRoomBody chatRoomBody = new ChatRoomBody("chartRoomName","CharRoomDesc",maxs,"yonghuming5",members);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.createChatRoom(chatRoomBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
        //放回的接口里面有id 9390557036549，其他接口要使用
        //9390485733377
    }

    /**
     *  修改聊天室信息接口测试
     */
    @Test
    public void modifyChatRoom() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        String[] members = new String[]{"yonghuming6", "yonghuming7"};
        long maxs = 50;
        ModifyChatRoomBody modifyChatRoomBody = new ModifyChatRoomBody("NewChartRoomName","CharRoomDesc1",maxs);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.modifyChatRoom("9390557036549",modifyChatRoomBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 删除聊天室接口测试
     */
    @Test
    public void deleteChatRoom() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.deleteChatRoom("9390557036549");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 获取app中所有的聊天室接口测试
     */
    @Test
    public void getAllChatRooms() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.getAllChatRooms();
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 获取一个聊天室详情接口测试
     */
    @Test
    public void getChatRoomDetail() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.getChatRoomDetail("9390557036549");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 聊天室成员添加[单个]接口测试
     */
    @Test
    public void addSingleUserToChatRoom() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.addSingleUserToChatRoom("9390557036549","yonghuming7");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 聊天室成员添加[批量]接口测试
     */
    @Test
    public void addBatchUsersToChatRoom() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        String[] userNames = new String[]{"yonghuming11","yonghuming9"};
        UserNamesBody userNamesBody = new UserNamesBody(userNames);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.addBatchUsersToChatRoom("9390557036549", userNamesBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 聊天室成员删除[单个]接口测试
     */
    @Test
    public void removeSingleUserFromChatRoom() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.removeSingleUserFromChatRoom("9390557036549", "yonghuming9");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 聊天室成员删除[批量]接口测试
     */
    @Test
    public void removeBatchUsersFromChatRoom() {
        ChatRoomAPI chatRoomAPI = (ChatRoomAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
        String[] userNames = new String[]{"yonghuming11"};
        ResponseWrapper wrapper = (ResponseWrapper) chatRoomAPI.removeBatchUsersFromChatRoom("9390557036549", userNames);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }
}
