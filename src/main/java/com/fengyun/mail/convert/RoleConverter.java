package com.fengyun.mail.convert;

import com.fengyun.mail.dto.RoleDTO;
import com.fengyun.mail.entity.RoleDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    RoleDo dtoToDo(RoleDTO roleDTO);

    RoleDTO doToDTO(RoleDo roleDo);

    List<RoleDTO> doToDTO(List<RoleDo> list);
}
