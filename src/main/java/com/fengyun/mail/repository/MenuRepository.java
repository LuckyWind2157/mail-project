package com.fengyun.mail.repository;

import com.fengyun.mail.domain.MenuDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuDao, Long>, JpaSpecificationExecutor<MenuDao> {
}
