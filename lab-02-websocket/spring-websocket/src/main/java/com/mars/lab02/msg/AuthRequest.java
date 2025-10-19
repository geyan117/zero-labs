package com.mars.lab02.msg;

import lombok.Data;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Data
public class AuthRequest implements Message {

    public static final String TYPE = "auth_request";

    private String accessToken;
}
