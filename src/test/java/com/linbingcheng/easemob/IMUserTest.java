package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.IMUserAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.body.*;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bingchenglin on 2017/2/28.
 */
public class IMUserTest {

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }

    /**
     * 添加用户接口测试
     */
    @Test
    public void addIMUser(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        IMUserBody user = new IMUserBody("yonghuming1","123456","nicheng1");
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.createNewIMUserSingle(user);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 批量添加用户接口测试
     */
    @Test
    public void addIMUsersBatch(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        List<IMUserBody> users = new ArrayList<IMUserBody>();
        for(int i = 2;i<22;i++){
            IMUserBody user = new IMUserBody("yonghuming"+i,"123456","nicheng"+i);
            users.add(user);
        }
        IMUsersBody usesBody = new IMUsersBody(users);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.createNewIMUserBatch(usesBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 查询用户信息接口测试
     */
    @Test
    public void getIMUserByUserName(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getIMUserByUserName("yonghuming1");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");

    }

    /**
     * 批量查询用户信息接口测试
     */
    @Test
    public void getIMUsersBatch(){
        long t = 20;
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getIMUsersBatch(t, "0");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");

    }

    /**
     * 删除用户信息接口测试
     */
    @Test
    public void deleteIMUserByUserName(){
        long t = 2;
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.deleteIMUserByUserName("yonghuming1");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 批量删除用户信息接口测试
     */
    @Test
    public void deleteIMUserBatch(){
        long t = 2;
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.deleteIMUserBatch(t, "0");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 修改用户密码接口测试
     */
    @Test
    public void modifyIMUserPasswordWithAdminToken(){
        ResetPasswordBody newPassword = new ResetPasswordBody("123456");
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.modifyIMUserPasswordWithAdminToken("yonghuming5", newPassword);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 重置用户昵称接口测试
     */
    @Test
    public void modifyIMUserNickNameWithAdminToken(){
        ModifyNicknameBody newNickname = new ModifyNicknameBody("昵称1111");
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.modifyIMUserNickNameWithAdminToken("yonghuming5", newNickname);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 给用户的添加好友接口测试
     */
    @Test
    public void addFriendSingle(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.addFriendSingle("yonghuming5", "yonghuming7");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 解除用户的好友关系接口测试
     */
    @Test
    public void deleteFriendSingle(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.deleteFriendSingle("yonghuming5", "yonghuming6");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 查看某个IM用户的好友信息接口测试
     */
    @Test
    public void getFriends(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getFriends("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 往IM用户的黑名单中加人接口测试
     */
    @Test
    public void addToBlackList(){
        String[] userNames = {"yonghuming6","yonghuming7"};
        UserNamesBody userNamesBody = new UserNamesBody(userNames);
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.addToBlackList("yonghuming5",userNamesBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 从IM用户的黑名单中减人接口测试
     */
    @Test
    public void removeFromBlackList(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.removeFromBlackList("yonghuming5", "yonghuming6");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }


    /**
     * 获取IM用户的黑名单接口测试
     */
    @Test
    public void getBlackList(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getBlackList("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 查看用户在线状态接口测试
     */
    @Test
    public void getIMUserStatus(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getIMUserStatus("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 查询离线消息数接口测试
     */
    @Test
    public void getOfflineMsgCount(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getOfflineMsgCount("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 用户账号禁用接口测试
     */
    @Test
    public void deactivateIMUser(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.deactivateIMUser("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }




    /**
     * 用户账号解禁接口测试
     */
    @Test
    public void activateIMUser(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.activateIMUser("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 强制用户下线接口测试
     */
    @Test
    public void disconnectIMUser(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.disconnectIMUser("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 获取用户参与的群组接口测试
     */
    @Test
    public void getIMUserAllChatGroups(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getIMUserAllChatGroups("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 获取用户所有参与的聊天室接口测试
     */
    @Test
    public void getIMUserAllChatRooms(){
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.getIMUserAllChatRooms("yonghuming5");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }


}
