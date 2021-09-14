package com.fengyun.mail.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Table(name = "t_user")
@Entity
@Data
public class UserDao extends BaseDao implements Serializable {
    private static final long serialVersionUID = -88598328571083439L;
    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    private String status;

    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "ID")})
    private Set<RoleDao> roleDaoSet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    private MailProtocolDao mailProtocolDao;
}