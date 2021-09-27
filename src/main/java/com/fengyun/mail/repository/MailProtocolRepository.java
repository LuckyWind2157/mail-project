package com.fengyun.mail.repository;

import com.fengyun.mail.entity.MailProtocolDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailProtocolRepository extends JpaRepository<MailProtocolDo, Long>, JpaSpecificationExecutor<MailProtocolDo> {


    List<MailProtocolDo> findAllByStatus(String status);

    MailProtocolDo findByUserId(Long userId);
}
