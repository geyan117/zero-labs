package com.mars.lab02.msg;

import lombok.Data;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Data
public class AuthResponse implements Message {

    public static final String TYPE = "auth_response";

   private Integer code;

   private String msg;
}
