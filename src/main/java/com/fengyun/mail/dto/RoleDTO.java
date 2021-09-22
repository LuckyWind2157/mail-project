package com.fengyun.mail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;
}
