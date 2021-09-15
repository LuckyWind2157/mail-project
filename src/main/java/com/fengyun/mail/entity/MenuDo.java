package com.fengyun.mail.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_menu")
@Entity
@Data
public class MenuDo extends BaseDo implements Serializable {
    private static final long serialVersionUID = 214241636221186516L;
    /**
     * 主键
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    /**
     * 编号
     */
    private String code;
    /**
     * 父级菜单
     */
    private String parentCode;
    /**
     * 名称
     */
    private String name;
    /**
     * 备注
     */
    private String remake;
    /**
     * 路径
     */
    private String url;


}