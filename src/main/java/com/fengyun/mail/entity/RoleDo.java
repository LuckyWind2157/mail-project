package com.fengyun.mail.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Table(name = "t_role")
@Entity
@Data
public class RoleDo extends BaseDo implements Serializable {
    private static final long serialVersionUID = 668763132481364075L;
    /**
     * 主键id
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 备注
     */
    private String remake;

    @ManyToMany
    @JoinTable(name = "t_role_menu", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", referencedColumnName = "ID")})
    private Set<MenuDo> menuDoSet;
}