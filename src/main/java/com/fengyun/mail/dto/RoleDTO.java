package com.fengyun.mail.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 7927174918225079510L;
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 备注
     */
    private String remake;
    private String status;
}
