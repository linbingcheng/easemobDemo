package com.linbingcheng.easemob;

import com.linbingcheng.easemob.common.EasemobContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bingchenglin on 2017/2/28.
 */
public class IMUserTest {

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }

    @Test
    public void addIMUser(){

    }

}
