package com.mars.lab01.mapstruct.convert;

import com.mars.lab01.mapstruct.dataobject.UserDO;
import com.mars.lab01.mapstruct.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author geyan
 * @date 2025/10/10
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "username", target = "userName")
    })
    UserDO convert(UserDTO userDTO);
}
