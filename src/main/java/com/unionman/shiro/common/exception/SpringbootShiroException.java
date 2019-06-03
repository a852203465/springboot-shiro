package com.unionman.shiro.common.exception;

import com.unionman.shiro.common.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description:  项目自定义异常
 * @author Rong.Jia
 * @date 2019/4/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SpringbootShiroException extends RuntimeException  implements Serializable {

    private static final long serialVersionUID = -1501020198729282518L;

    private Integer code;

    private String message;

    public SpringbootShiroException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public SpringbootShiroException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public SpringbootShiroException(ResponseEnum responseEnum, String message) {
        super(message);
        this.code = responseEnum.getCode();
        this.message = message;
    }

    public SpringbootShiroException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.message = message;
    }

    public SpringbootShiroException(ResponseEnum responseEnum, Throwable t) {
        super(responseEnum.getMessage(), t);
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }


}
