package com.fengyun.mail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MenuDTO {
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
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;
}
