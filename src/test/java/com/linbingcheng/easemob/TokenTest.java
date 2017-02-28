package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.AuthTokenAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bingchenglin on 2017/2/28.
 */
public class TokenTest {

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }

    @Test
    public void getToken(){
        AuthTokenAPI authToken = (AuthTokenAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.TOKEN_CLASS);
        ResponseWrapper wrapper = (ResponseWrapper) authToken.getAuthToken(EasemobContext.getInstance().getClientId(), EasemobContext.getInstance().getClientSecret());
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }


}
