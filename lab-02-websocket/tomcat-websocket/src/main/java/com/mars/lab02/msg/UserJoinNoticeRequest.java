package com.mars.lab02.msg;

import lombok.Data;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Data
public class UserJoinNoticeRequest implements Message {

    public static final String TYPE = "user_join_notice";

    private String username;
}
