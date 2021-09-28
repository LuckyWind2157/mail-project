package com.fengyun.mail.repository;

import com.fengyun.mail.entity.SendDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author chenfengyun
 */
@Repository
public interface SendRepository extends JpaRepository<SendDo, Long>, JpaSpecificationExecutor<SendDo> {


}
