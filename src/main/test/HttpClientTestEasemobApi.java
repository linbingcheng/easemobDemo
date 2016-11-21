import com.linbingcheng.easemob.api.AuthTokenAPI;
import com.linbingcheng.easemob.common.ClientContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import org.junit.Test;

/**
 * Created by bingchenglin on 2016/11/21.
 */
public class HttpClientTestEasemobApi {

    @Test
    public void token(){
        EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
        ClientContext context = factory.getContext();
        AuthTokenAPI authToken = (AuthTokenAPI) factory.newInstance(EasemobRestAPIFactory.TOKEN_CLASS);
        String token = String.valueOf(authToken.getAuthToken(context.getClientId(), context.getClientSecret()));
        System.out.println("++++");
        System.out.println(token);
        System.out.println("+++");

    }


}
