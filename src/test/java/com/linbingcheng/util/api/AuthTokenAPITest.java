package com.linbingcheng.util.api;

import com.linbingcheng.easemob.api.AuthTokenAPI;
import com.linbingcheng.easemob.common.ClientContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bingchenglin on 2016/11/22.
 */
public class AuthTokenAPITest {

    @Test
    public void token(){
        EasemobRestAPIFactory factory = ClientContext.getInstance().init().getAPIFactory();
        ClientContext context = factory.getContext();
        AuthTokenAPI authToken = (AuthTokenAPI) factory.newInstance(EasemobRestAPIFactory.TOKEN_CLASS);
        ResponseWrapper responseWrapper = (ResponseWrapper) authToken.getAuthToken(context.getClientId(), context.getClientSecret());
        Assert.assertNotNull(responseWrapper);
        System.out.println("+++");
        System.out.println(responseWrapper.getResponseStatus());
        System.out.println(responseWrapper.getResponseBody());
        System.out.println(responseWrapper.getMessages());
        System.out.println("+++");
    }


}
