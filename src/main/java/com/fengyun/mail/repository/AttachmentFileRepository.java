package com.fengyun.mail.repository;

import com.fengyun.mail.entity.AttachmentFileDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentFileRepository extends JpaRepository<AttachmentFileDo, Long>, JpaSpecificationExecutor<AttachmentFileDo> {


}
