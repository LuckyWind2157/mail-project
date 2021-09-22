
package com.fengyun.mail.dto;

import com.fengyun.mail.enums.BaseErrorCodeEnum;
import com.fengyun.mail.enums.ErrorCode;

import java.io.Serializable;

public class ResponsePageDTO<T> implements Serializable {
    private static final long serialVersionUID = -1039257272318417480L;
    private String code = "";
    private String msg = "";
    private Long count;
    private T data = null;

    // code ,msg,count,data
    public ResponsePageDTO() {
        this.code = "";
        this.msg = "";
    }

    public ResponsePageDTO(String code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ResponsePageDTO(String code, String message, T items) {
        this.code = code;
        this.msg = message;
        this.data = items;
    }

    public static <T> ResponsePageDTO<T> ok() {
        return ok(null);
    }

    public static <T> ResponsePageDTO<T> ok(T item) {
        return new ResponsePageDTO(BaseErrorCodeEnum.SUCCESS.getErrorCode(), BaseErrorCodeEnum.SUCCESS.getErrorMsg(), item);
    }

    public static <T> ResponsePageDTO<T> error(String code, String message) {
        return new ResponsePageDTO(code, message, (Object) null);
    }

    public static <T> ResponsePageDTO<T> error(T item) {
        return new ResponsePageDTO(BaseErrorCodeEnum.ERROR.getErrorCode(), BaseErrorCodeEnum.ERROR.getErrorMsg(), item);
    }

    public static <T> ResponsePageDTO<T> error(ResponsePageDTO resp) {
        return new ResponsePageDTO(resp.getCode(), resp.getMsg());
    }

    public static <T> ResponsePageDTO<T> error(ErrorCode errorCode) {
        return new ResponsePageDTO(errorCode.getErrorCode(), errorCode.getErrorMsg());
    }

    public static Boolean Succ(ResponsePageDTO resp) {
        return BaseErrorCodeEnum.SUCCESS.getErrorCode().equals(resp.getCode());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
