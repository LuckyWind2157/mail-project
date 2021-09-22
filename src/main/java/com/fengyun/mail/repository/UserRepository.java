package com.fengyun.mail.repository;

import com.fengyun.mail.entity.UserDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDo, Long>, JpaSpecificationExecutor<UserDo> {

    List<UserDo> findAllByStatus(String code);
}
