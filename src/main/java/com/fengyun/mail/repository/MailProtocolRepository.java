package com.fengyun.mail.repository;

import com.fengyun.mail.domain.MailProtocolDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MailProtocolRepository extends JpaRepository<MailProtocolDao, Long>, JpaSpecificationExecutor<MailProtocolDao> {


}
