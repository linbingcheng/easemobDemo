package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.ChatGroupAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.body.ChatGroupBody;
import com.linbingcheng.easemob.common.body.GroupOwnerTransferBody;
import com.linbingcheng.easemob.common.body.ModifyChatGroupBody;
import com.linbingcheng.easemob.common.body.UserNamesBody;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bingchenglin on 2017/3/1.
 */
public class ChatGroupTest {

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }

    /**
     * 获取群组接口测试
     */
    @Test
    public void  getChatGroups(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.getChatGroups((long) 2,"1");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");

    }

    /**
     * 获取一个或者多个群组的详情接口测试
     */
    @Test
    public void  getChatGroupDetails(){
        String[] groupIds = new String[]{"9397403189250","9398104686593"};
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.getChatGroupDetails(groupIds);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 创建一个群组接口测试
     */
    @Test
    public void  createChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ChatGroupBody chatGroupBody = new ChatGroupBody("群组名字1","群组描述1",true,(long)50,true,"yonghuming5",new String[]{"yonghuming8","yonghuming9"});
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.createChatGroup(chatGroupBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
        //返回ID记下，后面很多地方用到
        // 9397403189250
        // 9398104686593
    }

    /**
     * 修改群组信息接口测试
     */
    @Test
    public void  modifyChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ModifyChatGroupBody chatGroupBody = new ModifyChatGroupBody("群组名字2","群组描述2",(long)100);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.modifyChatGroup("9397403189250", chatGroupBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 删除群组接口测试
     */
    @Test
    public void  deleteChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.deleteChatGroup("9397403189250");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 获取群组所有用户接口测试
     */
    @Test
    public void  getChatGroupUsers(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.getChatGroupUsers("9397403189250");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组加人[单个]接口测试
     */
    @Test
    public void  addSingleUserToChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.addSingleUserToChatGroup("9397403189250","yonghuming15");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组加人[批量]接口测试
     */
    @Test
    public void  addBatchUsersToChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        String[] userNames = new String[]{"yonghuming11","yonghuming12"};
        UserNamesBody userNamesBody = new UserNamesBody(userNames);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.addBatchUsersToChatGroup("9398104686593",userNamesBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组减人[单个]接口测试
     */
    @Test
    public void  removeSingleUserFromChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.removeSingleUserFromChatGroup("9398104686593", "yonghuming11");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组减人[批量]接口测试
     */
    @Test
    public void  removeBatchUsersFromChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        String[] userNames = new String[]{"yonghuming12"};
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.removeBatchUsersFromChatGroup("9398104686593", userNames);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组转让接口测试
     */
    @Test
    public void  transferChatGroupOwner(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        GroupOwnerTransferBody groupOwnerTransferBody = new GroupOwnerTransferBody("yonghuming12");
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.transferChatGroupOwner("9398104686593", groupOwnerTransferBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 查询群组黑名单接口测试
     */
    @Test
    public void  getChatGroupBlockUsers(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.getChatGroupBlockUsers("9398104686593");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组黑名单个添加接口测试
     */
    @Test
    public void  addSingleBlockUserToChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.addSingleBlockUserToChatGroup("9398104686593", "yonghuming11");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组黑名单批量添加接口测试
     */
    @Test
    public void  addBatchBlockUsersToChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        String[] userNames = new String[]{"yonghuming8"};
        UserNamesBody userNamesBody = new UserNamesBody(userNames);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.addBatchBlockUsersToChatGroup("9398104686593", userNamesBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组黑名单单个删除接口测试
     */
    @Test
    public void  removeSingleBlockUserFromChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.removeSingleBlockUserFromChatGroup("9398104686593", "yonghuming11");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 群组黑名单批量删除接口测试
     */
    @Test
    public void  removeBatchBlockUsersFromChatGroup(){
        ChatGroupAPI chatGroupAPI = (ChatGroupAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
        String[] userNames = new String[]{"yonghuming8"};
        ResponseWrapper wrapper = (ResponseWrapper) chatGroupAPI.removeBatchBlockUsersFromChatGroup("9398104686593", userNames);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }
}
