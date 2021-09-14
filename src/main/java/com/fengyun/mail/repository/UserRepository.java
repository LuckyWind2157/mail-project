package com.fengyun.mail.repository;

import com.fengyun.mail.domain.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long>, JpaSpecificationExecutor<UserDao> {

    List<UserDao> findAllByStatus(String code);
}
