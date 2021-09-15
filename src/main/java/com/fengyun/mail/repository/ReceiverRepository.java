package com.fengyun.mail.repository;

import com.fengyun.mail.entity.ReceiverDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<ReceiverDo, Long>, JpaSpecificationExecutor<ReceiverDo> {


    Long getMaxIdByStatus(String code);
}
