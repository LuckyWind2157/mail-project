package com.fengyun.mail.dto;

import lombok.Data;

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

}
