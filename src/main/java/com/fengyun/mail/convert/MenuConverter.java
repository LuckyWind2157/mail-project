package com.fengyun.mail.convert;

import com.fengyun.mail.dto.MenuDTO;
import com.fengyun.mail.entity.MenuDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConverter {

    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    MenuDo dtoToMenuDo(MenuDTO menuDTO);

    MenuDTO doToMenuDTO(MenuDo menuDo);

    List<MenuDTO> doToMenuDTO(List<MenuDo> list);
}
