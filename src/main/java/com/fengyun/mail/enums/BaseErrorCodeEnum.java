

package com.fengyun.mail.enums;

public enum BaseErrorCodeEnum implements ErrorCode {
    SUCCESS("0000", "成功"),
    ERROR("9999", "后台异常"),
    INVALIDATE_ARGUMENTS("0400", "非法的请求参数"),
    UNAUTHORIZED("0401", "未授权"),
    FORBIDDEN("0403", "没有权限访问"),
    NOT_FOUND("0404", "请求资源未找到"),
    TOO_MANY("0405", "请求超过限制");

    private String code;
    private String msg;

    public String getErrorCode() {
        return this.code;
    }

    public String getErrorMsg() {
        return this.msg;
    }

    private BaseErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static BaseErrorCodeEnum getEnum(String code) {
        BaseErrorCodeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            BaseErrorCodeEnum ele = var1[var3];
            if (ele.getErrorCode().equals(code)) {
                return ele;
            }
        }

        return null;
    }
}
