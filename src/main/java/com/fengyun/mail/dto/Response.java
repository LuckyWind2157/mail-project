
package com.fengyun.mail.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fengyun.mail.enums.BaseErrorCodeEnum;
import com.fengyun.mail.enums.ErrorCode;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = -1039257272318417480L;
    @JsonIgnore
    private Boolean unwrap = false;
    private String code = "";
    private String message = "";
    private T items = null;

    public Response() {
        this.code = "";
        this.message = "";
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(String code, String message, T items) {
        this.code = code;
        this.message = message;
        this.items = items;
    }

    public static <T> Response<T> ok() {
        return ok(null);
    }

    public static <T> Response<T> ok(T item) {
        return new Response(BaseErrorCodeEnum.SUCCESS.getErrorCode(), BaseErrorCodeEnum.SUCCESS.getErrorMsg(), item);
    }

    public static <T> Response<T> error(String code, String message) {
        return new Response(code, message, (Object) null);
    }

    public static <T> Response<T> error(T item) {
        return new Response(BaseErrorCodeEnum.ERROR.getErrorCode(), BaseErrorCodeEnum.ERROR.getErrorMsg(), item);
    }

    public static <T> Response<T> error(Response resp) {
        return new Response(resp.getCode(), resp.getMessage());
    }

    public static <T> Response<T> error(ErrorCode errorCode) {
        return new Response(errorCode.getErrorCode(), errorCode.getErrorMsg());
    }

    public static Boolean Succ(Response resp) {
        return BaseErrorCodeEnum.SUCCESS.getErrorCode().equals(resp.getCode());
    }

    public Response unwrap() {
        this.unwrap = true;
        return this;
    }

    public Boolean getUnwrap() {
        return this.unwrap;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getItems() {
        return this.items;
    }

    public void setUnwrap(Boolean unwrap) {
        this.unwrap = unwrap;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Response)) {
            return false;
        } else {
            Response<?> other = (Response) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59:
                {
                    Object this$unwrap = this.getUnwrap();
                    Object other$unwrap = other.getUnwrap();
                    if (this$unwrap == null) {
                        if (other$unwrap == null) {
                            break label59;
                        }
                    } else if (this$unwrap.equals(other$unwrap)) {
                        break label59;
                    }

                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$items = this.getItems();
                Object other$items = other.getItems();
                if (this$items == null) {
                    if (other$items != null) {
                        return false;
                    }
                } else if (!this$items.equals(other$items)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Response;
    }

    public int hashCode() {
        int result = 1;
        Object $unwrap = this.getUnwrap();
        result = result * 59 + ($unwrap == null ? 43 : $unwrap.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $items = this.getItems();
        result = result * 59 + ($items == null ? 43 : $items.hashCode());
        return result;
    }

    public String toString() {
        return "Response(unwrap=" + this.getUnwrap() + ", code=" + this.getCode() + ", message=" + this.getMessage() + ", items=" + this.getItems() + ")";
    }
}
