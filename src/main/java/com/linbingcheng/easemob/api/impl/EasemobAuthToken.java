package com.linbingcheng.easemob.api.impl;

import com.linbingcheng.easemob.api.AuthTokenAPI;
import com.linbingcheng.easemob.common.body.AuthTokenBody;
import com.linbingcheng.easemob.common.constant.HTTPMethod;
import com.linbingcheng.easemob.common.helper.HeaderHelper;
import com.linbingcheng.easemob.common.wrapper.BodyWrapper;
import com.linbingcheng.easemob.common.wrapper.HeaderWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasemobAuthToken extends EasemobRestAPI implements AuthTokenAPI {

    public static final String ROOT_URI = "/token";

    private static final Logger log = LoggerFactory.getLogger(EasemobAuthToken.class);

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object getAuthToken(String clientId, String clientSecret) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        BodyWrapper body = new AuthTokenBody(clientId, clientSecret);
        HeaderWrapper header = HeaderHelper.getDefaultHeader();
        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
