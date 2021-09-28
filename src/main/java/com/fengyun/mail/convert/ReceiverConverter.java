package com.fengyun.mail.convert;

import com.fengyun.mail.dto.MailDTO;
import com.fengyun.mail.entity.ReceiverDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReceiverConverter {

    ReceiverConverter INSTANCE = Mappers.getMapper(ReceiverConverter.class);

    ReceiverDo dtoToDo(MailDTO mailDTO);

    MailDTO doToDTO(ReceiverDo receiverDo);

    List<MailDTO> doToDTO(List<ReceiverDo> list);
}
