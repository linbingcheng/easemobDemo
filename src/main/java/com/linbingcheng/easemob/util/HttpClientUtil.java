package com.linbingcheng.easemob.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by bingchenglin on 2016/11/18.
 */
public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    // httpclient 连接池
    private static PoolingHttpClientConnectionManager connectionManager;
    // 是否输出连接池状态
    private boolean isLogPoolStats = true;
    // 连接管理监视器
    private Timer conncetionMonitor;
    // 关闭N秒内不活动的连接
    private int idleTimeOut = Contents.HttpProtocol.CONNECTION_IDLE_TIMEOUT;
    // 报文编码，默认UTF-8
    private String charset = Contents.HttpProtocol.DEFAULT_HTTP_CHATSET;
    // 连接超时时间(毫秒)
    private int connectionTimeout = Contents.HttpProtocol.CONNECTION_TIMEOUT;
    // 连接请求超时时间(毫秒)
    private int connectionRequestTimeout =  Contents.HttpProtocol.CONNECTION_REQUEST_TIME;
    // 等待数据超时时间(毫秒)
    private int socketTimeout = Contents.HttpProtocol.SOCKET_TIMEOUT;

    // 每个路由最大连接数
    private int maxPerRoute = Contents.HttpProtocol.MAX_PER_ROUTE_CONNECTION;
    // 最大连接数
    private int maxTotal = Contents.HttpProtocol.Max_TOTAL_CONNECTION;

    private static HttpClientUtil instance;

    private HttpClientUtil() {
        init();
    }

    private void init(){
        // 初始化连接池
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(this.maxTotal);
        connectionManager.setDefaultMaxPerRoute(this.maxPerRoute);
        conncetionMonitor = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 关闭失效的连接
                connectionManager.closeExpiredConnections();
                // 关闭N秒内不活动的连接
                connectionManager.closeIdleConnections(idleTimeOut,
                        TimeUnit.SECONDS);
            }
        };
        // 每5秒检测一次
        conncetionMonitor.schedule(task, 10000, 5000);
    }


    public HttpAccessResult doGet(String url) throws Exception {
        return doGet(url, null, getCharset());
    }

    public HttpAccessResult doGet(String url, String charset)
            throws Exception {
        return this.doGet(url, null, charset);
    }

    public HttpAccessResult doGet(String url,
                                        Map<String, String> params) throws Exception {
        return this.doGet(url, params, getCharset());
    }

    public HttpAccessResult doGet(String url,
                                        Map<String, String> params, String charset) throws Exception {
        return this.doGet(url, params, charset, null, getConnectionTimeout(),
                getSocketTimeout());
    }

    public HttpAccessResult doGet(String url,
                                  Map<String, String> params, Map<String, String> reqHeader) throws Exception {
        return this.doGet(url, params, null, reqHeader, getConnectionTimeout(),
                getSocketTimeout());
    }

    public HttpAccessResult doGet(String url, Map<String, String> params,String charset,
                        Map<String, String> reqHeader, int connectTimeout, int socketTimeout) throws Exception {
        // 打印请求参数
        if (log.isInfoEnabled()) {
            log.info("服务请求URL：" + url);
            log.info("服务请求参数：" + params);
            log.info("服务请求header：" + reqHeader);
            log.info("服务请求charset：" + charset);
            log.info("服务请求connectTimeout：" + connectTimeout);
            log.info("服务请求socketTimeout：" + socketTimeout);
        }
        if (StringUtils.isBlank(charset)) {
            setCharset(charset);
        }
        String httpUrl = null;
        CloseableHttpClient httpclient = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        HttpAccessResult result = new HttpAccessResult();
        try{
            httpUrl = buildUrl(url, params);
            httpclient =  getHttpClient();
            httpGet = new HttpGet(httpUrl);
            HttpEntity entity = null;
            setCongfig(httpGet, reqHeader,connectTimeout,socketTimeout);
            response = httpclient.execute(httpGet);
            entity = response.getEntity();
            int respHttpStatus = response.getStatusLine().getStatusCode();
            String respString = EntityUtils.toString(entity, getCharset());
            if (log.isInfoEnabled()) {
                log.info("http状态码：" + respHttpStatus);
                log.info("服务响应报文：" + respString);
            }
            result.setStatusCode(respHttpStatus);
            result.setResponseBody(respString);
            httpGet.abort();
        }catch (Exception e) {
            log.error("http调用失败，调用地址[" + httpUrl + "], 出错信息：", e);
            httpGet.abort();
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("response.close异常:", e);
            }
            httpclient.close();
        }
        return result;
    }



    public HttpAccessResult doPost(String url, String content)
            throws Exception {
        return this.doPost(url, content, null, getCharset());
    }

    public HttpAccessResult doPost(String url, String content,
                                         String charset) throws Exception {
        return this.doPost(url, content, null, null, charset,
                getConnectionTimeout(), getSocketTimeout());
    }

    public HttpAccessResult doPost(String url,
                                         Map<String, String> busiParams) throws Exception {
        return this.doPost(url, null, busiParams, getCharset());
    }

    public HttpAccessResult doPost(String url,
                                         Map<String, String> busiParams, String charset) throws Exception {
        return this.doPost(url, null, busiParams, null, charset,
                getConnectionTimeout(), getSocketTimeout());
    }

    public HttpAccessResult doPost(String url, String content,
                                         Map<String, String> busiParams, String charset) throws Exception {
        return this.doPost(url, content, busiParams, null, charset,
                getConnectionTimeout(), getSocketTimeout());
    }

    public HttpAccessResult doPost(String url, String content,
                                         Map<String, String> busiParams, Map<String, String> reqHeader,
                                         String charset, int connectTimeout, int socketTimeout)
            throws Exception {
        // 打印请求参数
        if (log.isInfoEnabled()) {
            log.info("服务请求URL：" + url);
            log.info("服务请求content：" + content);
            log.info("服务请求参数：" + busiParams);
            log.info("服务请求header：" + reqHeader);
            log.info("服务请求charset：" + charset);
            log.info("服务请求connectTimeout：" + connectTimeout);
            log.info("服务请求socketTimeout：" + socketTimeout);
        }

        String httpUrl = url;
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        HttpAccessResult accessResult = new HttpAccessResult();
        try {

            // 如果content和params都不为空，则将params设置到url中。
            if (!StringUtils.isBlank(content) && busiParams != null
                    && busiParams.size() > 0) {
                httpUrl = buildUrl(url, busiParams);
            }
            httpClient = getHttpClient();
            httpPost = new HttpPost(httpUrl);
            setCongfig(httpPost,reqHeader,connectTimeout,socketTimeout);
            // 设置请求参数
            HttpEntity reqEntity = null;
            if (!StringUtils.isBlank(content)) {
                reqEntity = new StringEntity(content, charset);
            } else {
                reqEntity = createEntity(busiParams, charset);
            }
            httpPost.setEntity(reqEntity);

            // 发送请求获取结果
            response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            int respHttpStatus = response.getStatusLine().getStatusCode();
            String respString = EntityUtils.toString(respEntity, charset);
            if (log.isInfoEnabled()) {
                log.info("http状态码：" + respHttpStatus);
                log.info("服务响应报文：" + respString);
            }
            accessResult.setStatusCode(respHttpStatus);
            accessResult.setResponseBody(respString);
        } catch (Exception e) {
            log.error("http调用失败，调用地址[" + httpUrl + "], 出错信息：", e);
            httpPost.abort();
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("response.close异常:", e);
            }
        }
        return accessResult;
    }

    /**
     * 获取httpclient
     * @param
     * @return CloseableHttpClient
     */
    private static CloseableHttpClient getHttpClient() {
        //请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
        return HttpClients.custom().setConnectionManager(connectionManager)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
    }

    private void setCongfig(HttpRequestBase request, Map<String, String> reqHeader,int connectTimeout, int socketTimeout) {
        request.setHeader("User-Agent", "Mozilla/5.0");
        request.setHeader("Accept","text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");//"en-US,en;q=0.5");
        request.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
        request.setHeader("Content-Type", "text/plain");

        // 设置请求头
        if (reqHeader != null && !reqHeader.isEmpty()) {
            for (Iterator<String> iterator = reqHeader.keySet().iterator(); iterator
                    .hasNext();) {
                String headerKey =  iterator.next();
                request.setHeader(headerKey, reqHeader.get(headerKey));
            }
        }

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(1000)
                .setSocketTimeout(socketTimeout)
                .setContentCompressionEnabled(true).build();
        request.setConfig(config);
    }

    /**
     * 根据参数创建URL。
     * @param
     * @return String
     */
    private static String buildUrl(String url, Map<String, String> params)
            throws URISyntaxException {
        if (params == null || params.size() == 0) {
            return url;
        }
        String result = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                for (Iterator<String> iterator = params.keySet().iterator(); iterator
                        .hasNext();) {
                    String key =  iterator.next();
                    builder.setParameter(key, params.get(key));
                }
            }
            result = builder.build().toString();
        } catch (URISyntaxException e) {
            log.error("拼接请求参数失败:", e);
            throw e;
        }
        return result;
    }

    /**
     * 创建请求实体，设置参数
     *
     * @param
     * @return HttpEntity
     * @throws UnsupportedEncodingException
     */
    private static HttpEntity createEntity(Map<String, String> params,
                                           String charset) throws UnsupportedEncodingException {
        if (params == null || params.size() == 0) {
            return null;
        }
        List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
        for (String key : params.keySet()) {
            formParams.add(new BasicNameValuePair(key, params.get(key)));
        }
        return new UrlEncodedFormEntity(formParams, charset);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
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

    public static PoolingHttpClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public static void setConnectionManager(PoolingHttpClientConnectionManager connectionManager) {
        HttpClientUtil.connectionManager = connectionManager;
    }

    public boolean isLogPoolStats() {
        return isLogPoolStats;
    }

    public void setLogPoolStats(boolean isLogPoolStats) {
        this.isLogPoolStats = isLogPoolStats;
    }

    public int getIdleTimeOut() {
        return idleTimeOut;
    }

    public void setIdleTimeOut(int idleTimeOut) {
        this.idleTimeOut = idleTimeOut;
    }

    public Timer getConncetionMonitor() {
        return conncetionMonitor;
    }

    public void setConncetionMonitor(Timer conncetionMonitor) {
        this.conncetionMonitor = conncetionMonitor;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }


}
