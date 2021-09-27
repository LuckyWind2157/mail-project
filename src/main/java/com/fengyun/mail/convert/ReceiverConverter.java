package com.fengyun.mail.convert;

import com.fengyun.mail.dto.ReceiverDTO;
import com.fengyun.mail.entity.ReceiverDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReceiverConverter {

    ReceiverConverter INSTANCE = Mappers.getMapper(ReceiverConverter.class);

    ReceiverDo dtoToDo(ReceiverDTO receiverDTO);

    ReceiverDTO doToDTO(ReceiverDo receiverDo);

    List<ReceiverDTO> doToDTO(List<ReceiverDo> list);
}
