package com.fengyun.mail.service.impl;

import com.fengyun.mail.convert.ProtocolConverter;
import com.fengyun.mail.dto.MailProtocolDTO;
import com.fengyun.mail.entity.MailProtocolDo;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.repository.MailProtocolRepository;
import com.fengyun.mail.service.MailProtocolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author chenfengyun
 */
@Service
public class MailProtocolServiceImpl implements MailProtocolService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MailProtocolRepository mailProtocolRepository;

    public MailProtocolServiceImpl(MailProtocolRepository mailProtocolRepository) {
        this.mailProtocolRepository = mailProtocolRepository;
    }

    @Override
    public List<MailProtocolDo> findAllByStatus(String status) {
        return mailProtocolRepository.findAllByStatus(status);
    }

    @Override
    public MailProtocolDTO findByUserId(Long userId) {
        MailProtocolDo mailProtocolDo = mailProtocolRepository.findByUserId(userId);
        return Objects.nonNull(mailProtocolDo) ? ProtocolConverter.INSTANCE.doToDTO(mailProtocolDo) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(MailProtocolDTO dto) {
        if (Objects.isNull(dto.getId())) {
            logger.info("新增");
            MailProtocolDo mailProtocolDo = ProtocolConverter.INSTANCE.dtoToDo(dto);
            mailProtocolDo.setStatus(StatusEnum.EFFECTIVE.getCode());
            mailProtocolDo.setCreatedId(0L);
            mailProtocolRepository.save(mailProtocolDo);
        } else {
            logger.info("更新");
            MailProtocolDo mailProtocolDo = ProtocolConverter.INSTANCE.dtoToDo(dto);
            mailProtocolRepository.save(mailProtocolDo);
        }
    }
}
