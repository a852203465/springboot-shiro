package com.unionman.shiro.common.exception;

import com.unionman.shiro.common.enums.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 自定义401无权限异常(UnauthorizedException)
 * @author Rong.Jia
 * @date 2019/04/17 09:59
 */
@Data
public class CustomUnauthorizedException extends RuntimeException  implements Serializable {

    private static final long serialVersionUID = 5749034820046311832L;

    private Integer code;

    private String message;

    public CustomUnauthorizedException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public CustomUnauthorizedException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public CustomUnauthorizedException(ResponseEnum responseEnum, String message) {
        super(message);
        this.code = responseEnum.getCode();
        this.message = message;
    }

    public CustomUnauthorizedException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.message = message;
    }

    public CustomUnauthorizedException(ResponseEnum responseEnum, Throwable t) {
        super(responseEnum.getMessage(), t);
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

}
