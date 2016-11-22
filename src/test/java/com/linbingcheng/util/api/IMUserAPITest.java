package com.linbingcheng.util.api;

import com.linbingcheng.easemob.api.IMUserAPI;
import com.linbingcheng.easemob.common.ClientContext;
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
 * Created by bingchenglin on 2016/11/22.
 */
public class IMUserAPITest {

    private EasemobRestAPIFactory factory = null;
    private ClientContext context = null;
    private IMUserAPI imUserAPI = null;

    @Before
    public void before(){
         factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
         context = factory.getContext();
         imUserAPI = (IMUserAPI) factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
    }

    @Test
    public void createNewIMUserSingle(){
        IMUserBody user = new IMUserBody("linbingcheng","123456","林炳城");
        ResponseWrapper responseWrapper = (ResponseWrapper) imUserAPI.createNewIMUserSingle(user);;
        Assert.assertNotNull(responseWrapper);
        System.out.println("+++");
        System.out.println(responseWrapper.getResponseStatus());
        System.out.println(responseWrapper.getResponseBody());
        System.out.println(responseWrapper.getMessages());
        System.out.println("+++");
         /*
        200
        {"action":"post","application":"630e0a40-acc8-11e6-8cb1-b5f2074da744","path":"/users","uri":"https://a1.easemob.com/1105161117178528/linbingchengtest1/users","entities":[{"uuid":"0c947cf0-b07c-11e6-a209-fd7af5a9c549","type":"user","created":1479795751487,"modified":1479795751487,"username":"linbingcheng","activated":true,"nickname":"林炳城"}],"timestamp":1479795751497,"duration":0,"organization":"1105161117178528","applicationName":"linbingchengtest1"}
        []
        */
    }

    @Test
    public void createNewIMUserBatch(){
        List<IMUserBody> userList = new ArrayList<IMUserBody>();
        IMUserBody user1 = new IMUserBody("linbingcheng1","123456","林炳城1");
        IMUserBody user2 = new IMUserBody("linbingcheng2","123456","林炳城2");
        userList.add(user1);
        userList.add(user2);
        IMUsersBody users = new IMUsersBody(userList);
        ResponseWrapper responseWrapper = (ResponseWrapper) imUserAPI.createNewIMUserBatch(users);
        Assert.assertNotNull(responseWrapper);
        System.out.println("+++");
        System.out.println(responseWrapper.getResponseStatus());
        System.out.println(responseWrapper.getResponseBody());
        System.out.println(responseWrapper.getMessages());
        System.out.println("+++");
        /*
        200
{"action":"post","application":"630e0a40-acc8-11e6-8cb1-b5f2074da744","path":"/users","uri":"https://a1.easemob.com/1105161117178528/linbingchengtest1/users","entities":[{"uuid":"aa977e20-b086-11e6-af98-7bc63bcc30b6","type":"user","created":1479800311554,"modified":1479800311554,"username":"linbingcheng1","activated":true,"nickname":"林炳城1"},{"uuid":"aa98ddb0-b086-11e6-ace7-336de987a101","type":"user","created":1479800311563,"modified":1479800311563,"username":"linbingcheng2","activated":true,"nickname":"林炳城2"}],"timestamp":1479800311572,"duration":0,"organization":"1105161117178528","applicationName":"linbingchengtest1"}
[]
         */
    }


}
