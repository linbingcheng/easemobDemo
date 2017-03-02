package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.FileAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;


public class FileTest {

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }

    /**
     * 上传文件接口测试
     */
    @Test
    public void uploadFile(){
        FileAPI fileAPI = (FileAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.FILE_CLASS);
        File file = new File("C:\\Users\\linbingchneg\\Desktop\\20170301231358.png");
        if(!file.exists()){
            try{
                Thread.currentThread().interrupt();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ResponseWrapper wrapper = (ResponseWrapper) fileAPI.uploadFile(file);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        //此处会返回相关信息 上传路径以及uuid和share-secret，在测试是可以使用
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 下载文件接口测试
     */
    @Test
    public void downloadFile(){
        FileAPI fileAPI = (FileAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.FILE_CLASS);
        String share_secret = "VfEpSmSvEeS7yU8dwa9rAQc-DIL2HhmpujTNfSTsrDt6eNb_";
        String uuid = "55f12940-64af-11e4-8a5b-ff2336f03252";
        ResponseWrapper wrapper = (ResponseWrapper) fileAPI.downloadFile(uuid,share_secret, false);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

}
