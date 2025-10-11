package com.mars.lab02.msg;

import lombok.Data;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Data
public class SendToUserRequest implements Message {

    public static final String TYPE = "send_to_user";

    private String msgId;

    private String content;
}
