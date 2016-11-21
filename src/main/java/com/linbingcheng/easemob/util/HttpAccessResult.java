package com.linbingcheng.easemob.util;

/**
 * Created by bingchenglin on 2016/11/21.
 */
public class HttpAccessResult {

    private int statusCode;
    private String responseBody;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

}
