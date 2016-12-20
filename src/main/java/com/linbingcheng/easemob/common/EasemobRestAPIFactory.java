package com.linbingcheng.easemob.common;

import com.linbingcheng.easemob.api.RestAPIInvoker;
import com.linbingcheng.easemob.api.impl.EasemobRestAPI;
import com.linbingcheng.easemob.common.invoker.HttpClientRestAPIInvoker;
import com.linbingcheng.easemob.common.invoker.JerseyRestAPIInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasemobRestAPIFactory {

    public static final String TOKEN_CLASS = "EasemobAuthToken";

    public static final String USER_CLASS = "EasemobIMUsers";

    public static final String FILE_CLASS = "EasemobFile";

    public static final String MESSAGE_CLASS = "EasemobChatMessage";

    public static final String SEND_MESSAGE_CLASS = "EasemobSendMessage";

    public static final String CHATGROUP_CLASS = "EasemobChatGroup";

    public static final String CHATROOM_CLASS = "EasemobChatRoom";

    private static final String BASE_PACKAGE = "com.linbingcheng.easemob.api.impl";

    private static final String METHOD_SET_CONTEXT = "setContext";

    private static final String METHOD_SET_INVOKER = "setInvoker";

    private static final Logger log = LoggerFactory.getLogger(EasemobRestAPIFactory.class);

    private static EasemobRestAPIFactory factory;

    private EasemobContext context;

    private RestAPIInvoker jersey = new JerseyRestAPIInvoker();

    private RestAPIInvoker httpclient = new HttpClientRestAPIInvoker();

    private EasemobRestAPIFactory(EasemobContext context) {
        this.context = context;
    }

    public static EasemobRestAPIFactory getInstance(EasemobContext context) {
        if (null == factory) {
            if (null == context || !context.isInitialized()) {
                log.warn(MessageTemplate.INVAILID_CONTEXT_MSG);
                log.warn(MessageTemplate.AUTOMATIC_CONTEXT_MSG);
                context = EasemobContext.getInstance().init();

                if (!context.isInitialized()) {
                    log.error(MessageTemplate.INVAILID_CONTEXT_MSG);
                    throw new RuntimeException(MessageTemplate.INVAILID_CONTEXT_MSG);
                }
            }

            factory = new EasemobRestAPIFactory(context);
        }

        return factory;
    }

    public EasemobRestAPI newInstance(String className) {

        String impLib = context.getImpLib();

        if (!EasemobContext.JERSEY_API.equals(impLib) && !EasemobContext.HTTPCLIENT_API.equals(impLib)) {
            String msg = MessageTemplate.print(MessageTemplate.UNKNOWN_TYPE_MSG, new String[]{impLib, "restapi implementation"});
            log.error(msg);
            throw new RuntimeException(msg);
        }

        return (EasemobRestAPI) getClassInstance(className);
    }

    private Object getClassInstance(String className) {
        Class<?> targetClass = null;
        Object newObj = null;

        try {
            targetClass = Class.forName(BASE_PACKAGE + "." + className);
            newObj = targetClass.newInstance();
        } catch (Exception e) {
            String msg = MessageTemplate.print(MessageTemplate.ERROR_CLASS_MSG, new String[]{className});
            log.error(msg, e);
            throw new RuntimeException(msg);
        }

        // Inject the context and invoker, they are defined in EasemobRestAPI
        try {
            targetClass.getMethod(METHOD_SET_CONTEXT, EasemobContext.class).invoke(newObj, this.context);
        } catch (Exception e) {
            String msg = MessageTemplate.print(MessageTemplate.ERROR_METHOD_MSG, new String[]{METHOD_SET_CONTEXT});
            log.error(msg, e);
            throw new RuntimeException(msg);
        }

        RestAPIInvoker invoker = null;
        if (EasemobContext.HTTPCLIENT_API.equals(context.getImpLib())) {
            invoker = httpclient;
        } else {
            invoker = jersey;
        }
        try {
            targetClass.getMethod(METHOD_SET_INVOKER, RestAPIInvoker.class).invoke(newObj, invoker);
        } catch (Exception e) {
            String msg = MessageTemplate.print(MessageTemplate.ERROR_METHOD_MSG, new String[]{METHOD_SET_INVOKER});
            log.error(msg, e);
            throw new RuntimeException(msg);
        }

        return newObj;
    }

    public EasemobContext getContext() {
        return context;
    }

}
