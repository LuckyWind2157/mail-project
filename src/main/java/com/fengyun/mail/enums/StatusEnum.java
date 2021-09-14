package com.fengyun.mail.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {
    EFFECTIVE("Y", "有效数据"),
    NOT_EFFECTIVE("N", "无效数据");
    private String code;
    private String des;

}
