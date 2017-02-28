package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.IMUserAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.body.IMUserBody;
import com.linbingcheng.easemob.common.body.IMUsersBody;
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

    @Test
    public void deleteIMUserBatch(){
        long t = 2;
        IMUserAPI imUserAPI =  (IMUserAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.USER_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) imUserAPI.deleteIMUserBatch(t,"0");
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

}
