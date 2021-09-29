package com.fengyun.mail.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Table(name = "t_user")
@Entity
@Data
public class UserDo extends BaseDo implements Serializable {
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

    private String remark;

}
