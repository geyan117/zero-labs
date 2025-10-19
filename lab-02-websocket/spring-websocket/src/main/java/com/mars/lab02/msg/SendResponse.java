package com.mars.lab02.msg;

import lombok.Data;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Data
public class SendResponse implements Message {

    public static final String TYPE = "send_response";

    private String msgId;

    private Integer code;

    private String msg;
}
