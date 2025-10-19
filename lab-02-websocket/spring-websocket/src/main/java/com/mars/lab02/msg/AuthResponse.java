package com.mars.lab02.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse implements Message {

    public static final String TYPE = "auth_response";

   private Integer code;

   private String msg;
}
