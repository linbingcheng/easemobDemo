package com.linbingcheng.easemob.api.impl;

import com.linbingcheng.easemob.api.ChatMessageAPI;
import com.linbingcheng.easemob.common.constant.HTTPMethod;
import com.linbingcheng.easemob.common.helper.HeaderHelper;
import com.linbingcheng.easemob.common.wrapper.HeaderWrapper;
import com.linbingcheng.easemob.common.wrapper.QueryWrapper;

public class EasemobChatMessage extends EasemobRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "/chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_GET, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
