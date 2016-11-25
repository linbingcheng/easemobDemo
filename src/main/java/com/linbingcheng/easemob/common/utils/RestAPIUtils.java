package com.linbingcheng.easemob.common.utils;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.linbingcheng.easemob.common.MyX509TrustManager;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestAPIUtils {

    // httpclient 连接池
    private static PoolingHttpClientConnectionManager connectionManager;
    // 连接管理监视器
    private static Timer conncetionMonitor;
    // 关闭N秒内不活动的连接
    private static int idleTimeOut = 30;
    // 每个路由最大连接数
    private static int maxPerRoute = 500;
    // 最大连接数
    private static int maxTotal = 200;


    static {
        // 初始化连接池
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(getMaxPerRoute());
        conncetionMonitor = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                connectionManager.setMaxTotal(getMaxTotal());
                connectionManager.setDefaultMaxPerRoute(getMaxPerRoute());
                // 关闭失效的连接
                connectionManager.closeExpiredConnections();
                // 关闭N秒内不活动的连接
                connectionManager.closeIdleConnections(getIdleTimeOut(),
                        TimeUnit.SECONDS);
            }
        };
        // 每5秒检测一次
        conncetionMonitor.schedule(task, 10000, 5000);
    }
    /**
     * Obtain a JerseyClient whit SSL
     *
     * @return Jersey Client
     */
    public static JerseyClient getJerseyClient(boolean isSSL, String CacertFilePath, String CacertFilePassword) {
        ClientBuilder clientBuilder = JerseyClientBuilder.newBuilder().register(MultiPartFeature.class);

        // Create a secure JerseyClient
        if (isSSL) {
            try {
                TrustManager[] tm = new TrustManager[]{new MyX509TrustManager(CacertFilePath, CacertFilePassword)};

                SSLContext sslContext = SSLContext.getInstance("SSL");

                sslContext.init(null, tm, new SecureRandom());

                clientBuilder.sslContext(sslContext).hostnameVerifier(SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return (JerseyClient) clientBuilder.build().register(JacksonJsonProvider.class);
    }

    /**
     * Create a httpClient instance
     * @param isSSL if the request is protected by ssl
     * @return HttpClient instance
     */
    public static HttpClient getHttpClient(boolean isSSL, String CacertFilePath, String CacertFilePassword) {
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

        CloseableHttpClient client = null;

        if (isSSL) {
            try {
                TrustManager[] tm = new TrustManager[]{new MyX509TrustManager(CacertFilePath, CacertFilePassword)};
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, tm, new SecureRandom());
                client = HttpClients.custom().setConnectionManager(connectionManager).setSSLContext(sslContext)
                        .setRetryHandler(httpRequestRetryHandler)
                        .build();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            client =  HttpClients.custom().setConnectionManager(connectionManager)
                    .setRetryHandler(httpRequestRetryHandler)
                    .build();
        }

        return client;
    }

    /**
     * Check illegal String
     *
     * @param regex reg expression
     * @param str   string to be validated
     * @return if matched
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.lookingAt();
    }

    public static PoolingHttpClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public static void setConnectionManager(PoolingHttpClientConnectionManager connectionManager) {
        RestAPIUtils.connectionManager = connectionManager;
    }

    public static Timer getConncetionMonitor() {
        return conncetionMonitor;
    }

    public static void setConncetionMonitor(Timer conncetionMonitor) {
        RestAPIUtils.conncetionMonitor = conncetionMonitor;
    }

    public static int getIdleTimeOut() {
        return idleTimeOut;
    }

    public static void setIdleTimeOut(int idleTimeOut) {
        RestAPIUtils.idleTimeOut = idleTimeOut;
    }

    public static int getMaxPerRoute() {
        return maxPerRoute;
    }

    public static void setMaxPerRoute(int maxPerRoute) {
        RestAPIUtils.maxPerRoute = maxPerRoute;
    }

    public static int getMaxTotal() {
        return maxTotal;
    }

    public static void setMaxTotal(int maxTotal) {
        RestAPIUtils.maxTotal = maxTotal;
    }

}
