package com.linbingcheng.easemob.common.invoker;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.linbingcheng.easemob.api.RestAPIInvoker;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.MessageTemplate;
import com.linbingcheng.easemob.common.constant.HTTPMethod;
import com.linbingcheng.easemob.common.utils.RestAPIUtils;
import com.linbingcheng.easemob.common.wrapper.BodyWrapper;
import com.linbingcheng.easemob.common.wrapper.HeaderWrapper;
import com.linbingcheng.easemob.common.wrapper.QueryWrapper;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpClientRestAPIInvoker implements RestAPIInvoker {

    private static final Logger log = LoggerFactory.getLogger(HttpClientRestAPIInvoker.class);

    private static int connectionTimeout = 5000;

    private static int socketTimeout = 5000;

    private static int connectionRequestTime = 1000;


    public ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query) {

        ResponseWrapper responseWrapper = new ResponseWrapper();
        ObjectNode responseNode = JsonNodeFactory.instance.objectNode();

        responseWrapper.setResponseBody(responseNode);

        if (!HTTPMethod.METHOD_GET.equalsIgnoreCase(method) && !HTTPMethod.METHOD_POST.equalsIgnoreCase(method) && !HTTPMethod.METHOD_PUT.equalsIgnoreCase(method) && !HTTPMethod.METHOD_DELETE.equalsIgnoreCase(method)) {
            String msg = MessageTemplate.print(MessageTemplate.UNKNOWN_TYPE_MSG, new String[]{method, "HTTP methods"});
            responseWrapper.addError(msg);
        }
        if (StringUtils.isBlank(url)) {
            String msg = MessageTemplate.print(MessageTemplate.BLANK_OBJ_MSG, new String[]{"Parameter url"});
            responseWrapper.addError(msg);
        }
        if (!RestAPIUtils.match("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url)) {
            String msg = MessageTemplate.print(MessageTemplate.INVAILID_FORMAT_MSG, new String[]{"Parameter url"});
            responseWrapper.addError(msg);
        }
//        if (null != header && header.getHeaders().isEmpty()) {
//            String msg = MessageTemplate.print(MessageTemplate.BLANK_OBJ_MSG, new String[]{"Parameter header"});
//            responseWrapper.addError(msg);
//        }
        if (null != body && !body.validate()) {
            responseWrapper.addError(MessageTemplate.INVALID_BODY_MSG);
        }

        if (responseWrapper.hasError()) {
            return responseWrapper;
        }

        log.debug("=============Request=============");
        log.debug("Method: " + method);
        log.debug("URL: " + url);
        log.debug("Header: " + header);
        log.debug("Body: " + ((null == body) ? "" : body.getBody()));
        log.debug("Query: " + query);
        log.debug("===========Request End===========");

        url = buildQuery(url, query);
        String cacertFilePath = EasemobContext.getInstance().getCacertFilePath();
        String cacertFilePassword = EasemobContext.getInstance().getCacertFilePassword();
        HttpClient client = RestAPIUtils.getHttpClient(StringUtils.startsWithIgnoreCase(url, "HTTPS"), cacertFilePath, cacertFilePassword);
        URL target;
        try {
            target = new URL(url);
        } catch (MalformedURLException e) {
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        HttpRequestBase request;
        HttpResponse response;
        try {
            if (method.equals(HTTPMethod.METHOD_POST)) {
                request = new HttpPost(target.toURI());
            } else if (method.equals(HTTPMethod.METHOD_PUT)) {
                request = new HttpPut(target.toURI());
            } else if (method.equals(HTTPMethod.METHOD_GET)) {
                request = new HttpGet(target.toURI());
            } else if (method.equals(HTTPMethod.METHOD_DELETE)) {
                request = new HttpDelete(target.toURI());
            } else {
                String msg = MessageTemplate.print(MessageTemplate.UNKNOWN_TYPE_MSG, new String[]{method, "Http Method"});
                log.error(msg);
                throw new RuntimeException(msg);
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(getConnectionTimeout()).setConnectionRequestTimeout(getConnectionRequestTime())
                    .setSocketTimeout(getSocketTimeout())
                    .setContentCompressionEnabled(true).build();
            request.setConfig(requestConfig);
        } catch (URISyntaxException e) {
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        if (null != body && null != body.getBody()) {
            ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(body.getBody().toString(), "UTF-8"));
        }
        buildHeader(request, header);
        try {
            response = client.execute(request);
        } catch (IOException e) {
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        responseWrapper = readResponse(responseWrapper, response, false);
        log.debug("=============Response=============");
        log.debug(responseWrapper.toString());
        log.debug("===========Response End===========");
        return responseWrapper;
    }

    public ResponseWrapper uploadFile(String url, HeaderWrapper header, File file) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        ObjectNode responseNode = JsonNodeFactory.instance.objectNode();
        responseWrapper.setResponseBody(responseNode);
        String cacertFilePath = EasemobContext.getInstance().getCacertFilePath();
        String cacertFilePassword = EasemobContext.getInstance().getCacertFilePassword();
        CloseableHttpClient client = (CloseableHttpClient) RestAPIUtils.getHttpClient(StringUtils.startsWithIgnoreCase(url, "HTTPS"), cacertFilePath, cacertFilePassword);
        CloseableHttpResponse response = null;

        if (StringUtils.isBlank(url)) {
            String msg = MessageTemplate.print(MessageTemplate.BLANK_OBJ_MSG, new String[]{"Parameter url"});
            responseWrapper.addError(msg);
        }
        if (!RestAPIUtils.match("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url)) {
            String msg = MessageTemplate.print(MessageTemplate.INVAILID_FORMAT_MSG, new String[]{"Parameter url"});
            responseWrapper.addError(msg);
        }
//        if (null != header && header.getHeaders().isEmpty()) {
//            String msg = MessageTemplate.print(MessageTemplate.BLANK_OBJ_MSG, new String[]{"Parameter header"});
//            responseWrapper.addError(msg);
//        }
        if (null == file || !file.exists() || !file.isFile() || !file.canRead()) {
            responseWrapper.addError(MessageTemplate.INVALID_BODY_MSG);
        }

        if (responseWrapper.hasError()) {
            return responseWrapper;
        }

        log.debug("=============Request=============");
        log.debug("URL: " + url);
        log.debug("Header: " + header);
        log.debug("Body: " + ((null == file) ? "" : file.getName()));
        log.debug("===========Request End===========");

        try {
            HttpPost httppost = new HttpPost(url);
            buildHeader(httppost, header);

            httppost.setEntity(MultipartEntityBuilder.create().addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName()).build());

            response = client.execute(httppost);

        } catch (Exception e) {
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
            }
        }

        responseWrapper = readResponse(responseWrapper, response, false);

        log.debug("=============Response=============");
        log.debug(responseWrapper.toString());
        log.debug("===========Response End===========");
        return responseWrapper;
    }

    public ResponseWrapper downloadFile(String url, HeaderWrapper header) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        ObjectNode responseNode = JsonNodeFactory.instance.objectNode();
        responseWrapper.setResponseBody(responseNode);
        String cacertFilePath = EasemobContext.getInstance().getCacertFilePath();
        String cacertFilePassword = EasemobContext.getInstance().getCacertFilePassword();
        CloseableHttpClient client = (CloseableHttpClient) RestAPIUtils.getHttpClient(StringUtils.startsWithIgnoreCase(url, "HTTPS"), cacertFilePath, cacertFilePassword);

        if (StringUtils.isBlank(url)) {
            String msg = MessageTemplate.print(MessageTemplate.BLANK_OBJ_MSG, new String[]{"Parameter url"});
            responseWrapper.addError(msg);
        }
        if (!RestAPIUtils.match("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url)) {
            String msg = MessageTemplate.print(MessageTemplate.INVAILID_FORMAT_MSG, new String[]{"Parameter url"});
            responseWrapper.addError(msg);
        }
//        if (null != header && header.getHeaders().isEmpty()) {
//            String msg = MessageTemplate.print(MessageTemplate.BLANK_OBJ_MSG, new String[]{"Parameter header"});
//            responseWrapper.addError(msg);
//        }

        if (responseWrapper.hasError()) {
            return responseWrapper;
        }

        log.debug("=============Request=============");
        log.debug("URL: " + url);
        log.debug("Header: " + header);
        log.debug("===========Request End===========");

        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        responseWrapper = readResponse(responseWrapper, response, true);

        log.debug("=============Response=============");
        log.debug(responseWrapper.toString());
        log.debug("===========Response End===========");
        return responseWrapper;
    }

    private void buildHeader(HttpUriRequest request, HeaderWrapper header) {
        if (null != header && !header.getHeaders().isEmpty()) {
            for (NameValuePair nameValuePair : header.getHeaders()) {
                request.addHeader(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
    }

    private String buildQuery(String url, QueryWrapper query) {
        if (null != url && null != query && !query.getQueries().isEmpty()) {
            url += "?";
            for (NameValuePair nameValuePair : query.getQueries()) {
                url += nameValuePair.getName() + "=" + nameValuePair.getValue();
                url += "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    private ResponseWrapper readResponse(ResponseWrapper responseWrapper, HttpResponse response, boolean isFile) {
        ObjectNode responseNode;
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            responseWrapper.setResponseStatus(response.getStatusLine().getStatusCode());

            Object responseContent;
            try {
                if (isFile) {
                    responseContent = entity.getContent();
                } else {
                    responseContent = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                }
            } catch (ParseException e) {
                responseWrapper.addError(e.getMessage());
                return responseWrapper;
            } catch (IOException e) {
                responseWrapper.addError(e.getMessage());
                return responseWrapper;
            }

            if (!isFile) {
                ObjectMapper mapper = new ObjectMapper();
                JsonFactory factory = mapper.getFactory();
                JsonParser jp;
                try {
                    jp = factory.createParser(responseContent.toString());
                    responseNode = mapper.readTree(jp);
                    responseWrapper.setResponseBody(responseNode);
                } catch (IOException e) {
                    log.error(MessageTemplate.STR2JSON_ERROR_MSG, e);
                    responseWrapper.addError(MessageTemplate.STR2JSON_ERROR_MSG);
                }
            } else {
                responseWrapper.setResponseBody(responseContent);
            }
        }
        return responseWrapper;
    }

    public static int getConnectionTimeout() {
        return connectionTimeout;
    }

    public static void setConnectionTimeout(int connectionTimeout) {
        HttpClientRestAPIInvoker.connectionTimeout = connectionTimeout;
    }

    public static int getSocketTimeout() {
        return socketTimeout;
    }

    public static void setSocketTimeout(int socketTimeout) {
        HttpClientRestAPIInvoker.socketTimeout = socketTimeout;
    }

    public static int getConnectionRequestTime() {
        return connectionRequestTime;
    }

    public static void setConnectionRequestTime(int connectionRequestTime) {
        HttpClientRestAPIInvoker.connectionRequestTime = connectionRequestTime;
    }
}
