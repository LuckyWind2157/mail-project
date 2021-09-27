package com.fengyun.mail.convert;

import com.fengyun.mail.dto.MailProtocolDTO;
import com.fengyun.mail.entity.MailProtocolDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProtocolConverter {

    ProtocolConverter INSTANCE = Mappers.getMapper(ProtocolConverter.class);

    MailProtocolDo dtoToDo(MailProtocolDTO mailProtocolDTO);

    MailProtocolDTO doToDTO(MailProtocolDo mailProtocolDo);

    List<MailProtocolDTO> doToDTO(List<MailProtocolDo> list);
}
