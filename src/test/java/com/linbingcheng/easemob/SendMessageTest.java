package com.linbingcheng.easemob;

import com.linbingcheng.easemob.api.SendMessageAPI;
import com.linbingcheng.easemob.common.EasemobContext;
import com.linbingcheng.easemob.common.EasemobRestAPIFactory;
import com.linbingcheng.easemob.common.body.AudioMessageBody;
import com.linbingcheng.easemob.common.body.ImgMessageBody;
import com.linbingcheng.easemob.common.body.TextMessageBody;
import com.linbingcheng.easemob.common.body.VideoMessageBody;
import com.linbingcheng.easemob.common.constant.MsgTargetType;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linbingchneg on 2017/3/1.
 */
public class SendMessageTest {

    @Before
    public void before(){
        System.out.println("easemob API context init begin");
        EasemobContext.getInstance().init("easemob/easemob_appinfo.properties");
        System.out.println("easemob API context init end");
    }

    /**
     * 发送文本信息
     */
    @Test
    public void SendTextMessageMessage(){
        SendMessageAPI sendMessageAPI = (SendMessageAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
        //发送信息到用户
        String[] targets = new String[]{"yonghuming6"};
        Map<String, String> ext = new HashMap<String, String>();
        TextMessageBody textMessageBody = new TextMessageBody(MsgTargetType.USERS,targets,"yonghuming8",ext,"发送信息");
        ResponseWrapper wrapper = (ResponseWrapper) sendMessageAPI.sendMessage(textMessageBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 发送图片信息
     */
    @Test
    public void SendImgMessageMessage(){
        SendMessageAPI sendMessageAPI = (SendMessageAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
        //发送信息到群组
        String[] targets = new String[]{"9398104686593"};
        Map<String, String> ext = new HashMap<String, String>();
        String url = "https://a1.easemob.com/1105161117178528/linbingchengtest1/chatfiles/55f12940-64af-11e4-8a5b-ff2336f03252";
        String filename = "20170301231358.png";
        String secret = "VfEpSmSvEeS7yU8dwa9rAQc-DIL2HhmpujTNfSTsrDt6eNb_";
        long w = 480;
        long h = 720;
        ImgMessageBody imgMessageBody = new ImgMessageBody(MsgTargetType.GROUPS,targets,"yonghuming8",ext,url,filename,secret,w,h);
        ResponseWrapper wrapper = (ResponseWrapper) sendMessageAPI.sendMessage(imgMessageBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 发送音频信息
     */
    @Test
    public void SendAudioMessageMessage(){
        SendMessageAPI sendMessageAPI = (SendMessageAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
        //发送信息到聊天室
        String[] targets = new String[]{"9390557036549"};
        Map<String, String> ext = new HashMap<String, String>();
        String url = "https://a1.easemob.com/1105161117178528/linbingchengtest1/chatfiles/55f12940-64af-11e4-8a5b-ff2336f03252";
        String filename = "Samantha Jade - Boyfriend.mp3";
        String secret = "Hfx_WlXGEeSdDW-SuX2EaZcXDC7ZEig3OgKZye9IzKOwoCjM";
        long l = 10;
        AudioMessageBody audioMessageBody = new AudioMessageBody(MsgTargetType.ROOMS,targets,"yonghuming8",ext,url,filename,secret,l);
        ResponseWrapper wrapper = (ResponseWrapper) sendMessageAPI.sendMessage(audioMessageBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }

    /**
     * 发送视频信息
     */
    @Test
    public void SendVideoMessageMessage(){
        SendMessageAPI sendMessageAPI = (SendMessageAPI) EasemobContext.getAPIFactory().newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
        //发送信息到用户
        String[] targets = new String[]{"yonghuming6"};
        Map<String, String> ext = new HashMap<String, String>();
        String thumb = "https://a1.easemob.com/1105161117178528/linbingchengtest1/chatfiles/55f12940-64af-11e4-8a5b-ff2336f03252";//缩略图
        String url = "https://a1.easemob.com/1105161117178528/linbingchengtest1/chatfiles/55f12940-64af-11e4-8a5b-ff2336f03252";
        String filename = "墨泥学车软件使用视频介绍.mp4";
        String secret = "VfEpSmSvEeS7yU8dwa9rAQc-DIL2HhmpujTNfSTsrDt6eNb_";
        String thumb_secret = "ZyebKn9pEeSSfY03ROk7ND24zUf74s7HpPN1oMV-1JxN2O2I";
        long file_length = 16523558;
        long length = 10;
        VideoMessageBody videoMessageBody = new VideoMessageBody(MsgTargetType.USERS,targets,"yonghuming8",ext,url,filename,secret,length,file_length,thumb,thumb_secret);
        ResponseWrapper wrapper = (ResponseWrapper) sendMessageAPI.sendMessage(videoMessageBody);
        System.out.println("+++++++++");
        Assert.assertNotNull(wrapper);
        System.out.println(wrapper.getResponseBody());
        System.out.println(wrapper.toString());
        System.out.println("+++++++++");
    }



}
