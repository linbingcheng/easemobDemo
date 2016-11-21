package com.linbingcheng.easemob.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by bingchenglin on 2016/11/18.
 */
public class HttpClientUtil {

    private String chatset = Contents.HttpProtocol.DEFAULT_HTTP_CHATSET;
    private String defauleProtocol = Contents.HttpProtocol.APPLICATION_JSON;
    private int connectionTimeout = Contents.HttpProtocol.CONNECTION_TIMEOUT;
    private int connectionRequestTimeout =  Contents.HttpProtocol.CONNECTION_REQUEST_TIME;
    private int socketTimeout = Contents.HttpProtocol.SOCKET_TIMEOUT;
    private static HttpClientUtil instance;


    public static HttpClientUtil getInstance() {
        return getInstance(Contents.HttpProtocol.DEFAULT_HTTP_CHATSET);
    }

    public static HttpClientUtil getInstance(String chatset) {
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        //设置默认的url编码
        instance.setChatset(chatset);
        return instance;
    }

    public String doGet(String url, Map<String, Objects> params, String... protocols) throws Exception {
        String result = null;
        CloseableHttpClient httpclient =  HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try{
            String httpUrl = createHttpGetUrl(url, params);
            HttpGet httpGet = new HttpGet(httpUrl);
            HttpEntity entity = null;
            setHeader(httpGet, protocols);
            try{
                response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
                    entity = response.getEntity();
                    if (entity !=null){
                        InputStream in = entity.getContent();
                        result = ConverResponseToString(in);
                    }
                }
                httpGet.abort();
            }finally {
                response.close();
            }
        }finally {
            httpclient.close();
        }
        return result;
    }



    public String doPost(String url, Map<String, Objects> params, String protocol) {
        return null;
    }

    private void setHeader(HttpRequestBase request, String... protocols) {
        if (protocols.length == 0) {
            request.setHeader("Accept", defauleProtocol);
            request.setHeader("Content-Type", defauleProtocol);
        } else if (protocols.length == 1) {
            request.setHeader("Accept", protocols[0]);
            request.setHeader("Content-Type", protocols[0]);
        } else {
            request.setHeader("Accept", protocols[0]);
            request.setHeader("Content-Type", protocols[1]);
        }
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(getConnectionTimeout()).setConnectionRequestTimeout(1000)
                .setSocketTimeout(getSocketTimeout()).build();
        request.setConfig(config);
    }

    private String createHttpGetUrl(String url, Map<String, Objects> params) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer(url);
        if (params != null && !params.isEmpty()) {
            sb.append("?");
            Iterator<Map.Entry<String, Objects>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Objects> entry = it.next();
                sb.append(URLEncoder.encode(entry.getKey(), Contents.HttpProtocol.DEFAULT_HTTP_CHATSET))
                        .append("=")
                        .append(URLEncoder.encode(String.valueOf(entry.getValue()), Contents.HttpProtocol.DEFAULT_HTTP_CHATSET));
            }
        }
        return sb.toString();
    }

    private String ConverResponseToString(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return  sb.toString();
    }


    public String getChatset() {
        return chatset;
    }

    public void setChatset(String chatset) {
        this.chatset = chatset;
    }


    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
