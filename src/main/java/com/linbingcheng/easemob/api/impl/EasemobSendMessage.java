package com.linbingcheng.easemob.api.impl;

import com.linbingcheng.easemob.api.SendMessageAPI;
import com.linbingcheng.easemob.common.constant.HTTPMethod;
import com.linbingcheng.easemob.common.helper.HeaderHelper;
import com.linbingcheng.easemob.common.wrapper.BodyWrapper;
import com.linbingcheng.easemob.common.wrapper.HeaderWrapper;

public class EasemobSendMessage extends EasemobRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
