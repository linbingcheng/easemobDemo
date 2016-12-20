package com.linbingcheng.easemob.common;

import com.linbingcheng.easemob.common.invoker.HttpClientRestAPIInvoker;
import com.linbingcheng.easemob.common.utils.RestAPIUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EasemobContext {


    private static final Logger log = LoggerFactory.getLogger(EasemobContext.class);

    private static EasemobContext context = new EasemobContext();

    private static EasemobRestAPIFactory factory = null;

    public static Boolean initialized = Boolean.FALSE;

    /*
     * Implementation 默认配置文件地址
	 */
    public static final String DEFAULT_PROPERTIES = "easemob/easemob_appinfo.properties";


    /*
     * Implementation List
	 */
    public static final String JERSEY_API = "jersey";

    public static final String HTTPCLIENT_API = "httpclient";

    /*
     * Properties
     */
    private static final String API_PROTOCAL_KEY = "API_PROTOCAL";

    private static final String API_HOST_KEY = "API_HOST";

    private static final String API_ORG_KEY = "API_ORG";

    private static final String API_APP_KEY = "API_APP";

    private static final String APP_CLIENT_ID_KEY = "APP_CLIENT_ID";

    private static final String APP_CLIENT_SECRET_KEY = "APP_CLIENT_SECRET";

    private static final String APP_IMP_LIB_KEY = "APP_IMP_LIB";

    private static final String CACERT_FILE_PATH_KEY = "CACERT_FILE_PATH";

    private static final String CACERT_FILE_PASSWORD_KEY = "CACERT_FILE_PASSWORD";

    private static final String HTTP_CHATSET_KEY = "HTTP_CHATSET";

    private static final String CONNECTION_IDLE_TIMEOUT_KEY = "CONNECTION_IDLE_TIMEOUT";

    private static final String CONNECTION_TIMEOUT_KEY = "CONNECTION_TIMEOUT";

    private static final String SOCKET_TIMEOUT_KEY = "SOCKET_TIMEOUT";

    private static final String CONNECTION_REQUEST_TIME_KEY = "CONNECTION_REQUEST_TIME";

    private static final String MAX_PER_ROUTE_CONNECTION_KEY = "MAX_PER_ROUTE_CONNECTION";

    private static final String Max_TOTAL_CONNECTION_KEY = "MAX_TOTAL_CONNECTION";

    /*
     * context
     */

    private String protocal;

    private String host;

    private String org;

    private String app;

    private String clientId;

    private String clientSecret;

    private String impLib;

    private String cacertFilePath;

    private String cacertFilePassword;

    private String httpChatset = "UTF-8";

    private int maxPerRouteConnetion = 20;

    private int maxTotalConnection = 60;

    private int connectionIdleTimeout = 30;

    private int connectionTimeout = 5000;

    private int socketTimeout = 5000;

    private int connectionRequestTime = 1000;

    private static TokenGenerator token; // Wrap the token generator

    private EasemobContext() {
    }

    public static EasemobContext getInstance() {
        return context;
    }

    public static EasemobContext init() {
        log.warn("Context initialization type was set to FILE by default.");
        return init(DEFAULT_PROPERTIES);
    }

    public static EasemobContext init(String configProperties) {
        if (initialized) {
            log.warn("Context has been initialized already, skipped!");
            return context;
        }
        initFromPropertiesFile(configProperties);
        // Initialize the token generator by default
        if (context.initialized) {
            token = new TokenGenerator(context);
        }
        return context;
    }

    public static EasemobRestAPIFactory getAPIFactory() {
        if (!context.isInitialized()) {
            log.error(MessageTemplate.INVAILID_CONTEXT_MSG);
            throw new RuntimeException(MessageTemplate.INVAILID_CONTEXT_MSG);
        }

        if (null == factory) {
            factory = EasemobRestAPIFactory.getInstance(context);
        }

        return factory;
    }

    public String getSeriveURL() {
        if (null == context || !context.isInitialized()) {
            log.error(MessageTemplate.INVAILID_CONTEXT_MSG);
            throw new RuntimeException(MessageTemplate.INVAILID_CONTEXT_MSG);
        }
        String serviceURL = context.getProtocal() + "://" + context.getHost() + "/" + context.getOrg() + "/" + context.getApp();
        return serviceURL;
    }

    public String getAuthToken() {
        if (null == token) {
            log.error(MessageTemplate.INVAILID_TOKEN_MSG);
            throw new RuntimeException(MessageTemplate.INVAILID_TOKEN_MSG);
        }

        return token.request(Boolean.FALSE);
    }

    private static void initFromPropertiesFile(String configProperties) {
        Properties p = new Properties();

        try {
            InputStream inputStream = EasemobContext.class.getClassLoader().getResourceAsStream("easemob/easemob_appinfo.properties");
            p.load(inputStream);
        } catch (IOException e) {
            log.error(MessageTemplate.print(MessageTemplate.FILE_ACCESS_MSG, new String[]{"easemob/easemob_appinfo.properties"}));
            return; // Context not initialized
        }

        String protocal = p.getProperty(API_PROTOCAL_KEY);
        String host = p.getProperty(API_HOST_KEY);
        String org = p.getProperty(API_ORG_KEY);
        String app = p.getProperty(API_APP_KEY);
        String clientId = p.getProperty(APP_CLIENT_ID_KEY);
        String clientSecret = p.getProperty(APP_CLIENT_SECRET_KEY);
        String impLib = p.getProperty(APP_IMP_LIB_KEY);
        String cacertFilePath = p.getProperty(CACERT_FILE_PATH_KEY);
        String cacertFilePassword = p.getProperty(CACERT_FILE_PASSWORD_KEY);
        String httpChatset = p.getProperty(HTTP_CHATSET_KEY);
        String connectionIdleTimeout = p.getProperty(CONNECTION_IDLE_TIMEOUT_KEY);
        String connectionTimeout = p.getProperty(CONNECTION_TIMEOUT_KEY);
        String socketTimeout = p.getProperty(SOCKET_TIMEOUT_KEY);
        String connectionRequestTime = p.getProperty(CONNECTION_REQUEST_TIME_KEY);
        String maxPerRouteConnetion = p.getProperty(MAX_PER_ROUTE_CONNECTION_KEY);
        String maxTotalConnection = p.getProperty(Max_TOTAL_CONNECTION_KEY);
        if (StringUtils.isBlank(protocal)
                || StringUtils.isBlank(host) || StringUtils.isBlank(org) || StringUtils.isBlank(app) || StringUtils.isBlank(clientId) || StringUtils.isBlank(clientSecret) || StringUtils.isBlank(impLib)) {
            log.error(MessageTemplate.print(MessageTemplate.INVAILID_PROPERTIES_MSG, new String[]{"easemob/easemob_appinfo.properties"}));
            return; // Context not initialized
        }

        context.protocal = protocal;
        context.host = host;
        context.org = org;
        context.app = app;
        context.clientId = clientId;
        context.clientSecret = clientSecret;
        context.impLib = impLib;
        context.cacertFilePath = cacertFilePath;
        context.cacertFilePassword = cacertFilePassword;

        context.httpChatset = StringUtils.isBlank(httpChatset) ? context.httpChatset : httpChatset;
        context.connectionIdleTimeout = StringUtils.isBlank(connectionIdleTimeout) ? context.connectionIdleTimeout : Integer.valueOf(connectionIdleTimeout);
        context.connectionTimeout = StringUtils.isBlank(connectionTimeout) ? context.connectionIdleTimeout : Integer.valueOf(connectionTimeout);
        context.socketTimeout = StringUtils.isBlank(socketTimeout) ? context.socketTimeout : Integer.valueOf(socketTimeout);
        context.connectionRequestTime = StringUtils.isBlank(connectionRequestTime) ? context.connectionRequestTime : Integer.valueOf(connectionRequestTime);
        context.maxPerRouteConnetion = StringUtils.isBlank(maxPerRouteConnetion) ? context.maxPerRouteConnetion : Integer.valueOf(maxPerRouteConnetion);
        context.maxTotalConnection = StringUtils.isBlank(maxTotalConnection) ? context.maxTotalConnection : Integer.valueOf(maxTotalConnection);
        RestAPIUtils.setIdleTimeOut(context.connectionIdleTimeout);
        RestAPIUtils.setMaxPerRoute(context.maxPerRouteConnetion);
        RestAPIUtils.setMaxTotal(context.maxTotalConnection);
        HttpClientRestAPIInvoker.setConnectionRequestTime(context.connectionRequestTime);
        HttpClientRestAPIInvoker.setConnectionTimeout(context.connectionTimeout);
        HttpClientRestAPIInvoker.setSocketTimeout(context.socketTimeout);
        initialized = Boolean.TRUE;
        log.debug("protocal: " + context.protocal);
        log.debug("host: " + context.host);
        log.debug("org: " + context.org);
        log.debug("app: " + context.app);
        log.debug("clientId: " + context.clientId);
        log.debug("clientSecret: " + context.clientSecret);
        log.debug("cacartFilePath: " + context.cacertFilePath);
        log.debug("getCacertFilePassword: " + context.cacertFilePassword);
        log.debug("httpChatset: " + context.httpChatset);
        log.debug("maxTotalConnection: " + context.maxTotalConnection);
        log.debug("maxPerRouteConnetion: " + context.maxPerRouteConnetion);
        log.debug("connectionIdleTimeout: " + context.connectionIdleTimeout);
        log.debug("connectionTimeout: " + context.connectionTimeout);
        log.debug("socketTimeout: " + context.socketTimeout);
        log.debug("connectionRequestTime: " + context.connectionRequestTime);
    }

    public String getProtocal() {
        return protocal;
    }

    public String getHost() {
        return host;
    }

    public String getOrg() {
        return org;
    }

    public String getApp() {
        return app;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Boolean isInitialized() {
        return initialized;
    }

    public String getImpLib() {
        return impLib;
    }

    public String getCacertFilePath() {
        return cacertFilePath;
    }

    public String getCacertFilePassword() {
        return cacertFilePassword;
    }

    public String getHttpChatset() {
        return httpChatset;
    }

    public int getConnectionIdleTimeout() {
        return connectionIdleTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public int getConnectionRequestTime() {
        return connectionRequestTime;
    }

    public int getMaxPerRouteConnetion() {
        return maxPerRouteConnetion;
    }

    public int getMaxTotalConnection() {
        return maxTotalConnection;
    }

    public static void main(String[] args) {
        EasemobContext.getInstance().init(null);
    }

}
