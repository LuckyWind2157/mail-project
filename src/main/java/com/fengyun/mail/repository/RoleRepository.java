package com.fengyun.mail.repository;

import com.fengyun.mail.entity.RoleDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDo, Long>, JpaSpecificationExecutor<RoleDo> {
}
