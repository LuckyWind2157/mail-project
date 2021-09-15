package com.fengyun.mail.repository;

import com.fengyun.mail.entity.MenuDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuDo, Long>, JpaSpecificationExecutor<MenuDo> {
    List<MenuDo> findAllByStatus(String status);

    List<MenuDo> findAllByNameAndStatus(String name, String status);
}
