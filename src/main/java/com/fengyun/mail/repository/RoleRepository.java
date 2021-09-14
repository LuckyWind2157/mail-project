package com.fengyun.mail.repository;

import com.fengyun.mail.domain.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDao, Long>, JpaSpecificationExecutor<RoleDao> {
}
