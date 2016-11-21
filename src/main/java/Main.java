import com.linbingcheng.easemob.api.AuthTokenAPI;
import com.linbingcheng.easemob.common.ClientContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;

/**
 * Created by bingchenglin on 2016/11/21.
 */
public class Main {
    public static void main(String[] args) {
        EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
        ClientContext context = factory.getContext();
        AuthTokenAPI authToken = (AuthTokenAPI) factory.newInstance(EasemobRestAPIFactory.TOKEN_CLASS);
        authToken.getAuthToken(context.getClientId(), context.getClientSecret());
        System.out.println("++++");

    }
}
