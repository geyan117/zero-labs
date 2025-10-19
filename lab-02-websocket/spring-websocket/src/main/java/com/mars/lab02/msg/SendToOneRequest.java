package com.mars.lab02.msg;

import lombok.Data;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Data
public class SendToOneRequest implements Message {

    public static final String TYPE = "send_to_one";

    /**
     * 发送给哪个用户
     */
    private String toUser;

    /**
     * 消息编号
     */
    private String msgId;

    /**
     * 内容
     */
    private String content;
}
