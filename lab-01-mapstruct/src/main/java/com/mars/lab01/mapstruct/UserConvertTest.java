package com.mars.lab01.mapstruct;

import com.mars.lab01.mapstruct.convert.UserConvert;
import com.mars.lab01.mapstruct.dataobject.UserDO;
import com.mars.lab01.mapstruct.dto.UserDTO;

/**
 * @author geyan
 * @date 2025/10/10
 */
public class UserConvertTest {

    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setUsername("Mars");
        userDTO.setPassword("123456");

        UserDO userDO = UserConvert.INSTANCE.convert(userDTO);
        System.out.println(userDO.getUserId());
        System.out.println(userDO.getUserName());
        System.out.println(userDO.getPassword());
    }
}
