package com.linbingcheng.easemob.api;

import com.linbingcheng.easemob.common.wrapper.BodyWrapper;
import com.linbingcheng.easemob.common.wrapper.HeaderWrapper;
import com.linbingcheng.easemob.common.wrapper.QueryWrapper;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;

import java.io.File;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header);
}
