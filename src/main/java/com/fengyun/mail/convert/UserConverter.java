package com.fengyun.mail.convert;

import com.fengyun.mail.dto.UserDTO;
import com.fengyun.mail.entity.UserDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserDo dtoToDo(UserDTO menuDTO);

    UserDTO doToDTO(UserDo userDo);

    List<UserDTO> doToDTO(List<UserDo> list);
}
