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

    }


}
