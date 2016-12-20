package com.linbingcheng.easemob.api;

import com.linbingcheng.easemob.common.body.*;

public interface SendMessageAPI {
    /**
     * 发送消息 <br>
     * POST
     *
     * @param payload 消息体
     * @return
     * @see MessageBody
     * @see TextMessageBody
     * @see ImgMessageBody
     * @see AudioMessageBody
     * @see VideoMessageBody
     * @see CommandMessageBody
     */
    Object sendMessage(Object payload);
}
