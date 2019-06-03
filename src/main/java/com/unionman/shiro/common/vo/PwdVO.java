package com.unionman.shiro.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 校验密码 vo对象
 * @date 2019/04/18 14:48:22
 * @author Rong.Jia
 */
@Data
@ApiModel("校验密码 对照对象")
public class PwdVO implements Serializable {

    private static final long serialVersionUID = -3321466227528985098L;

    /**
     * 旧密码校验是否成功
     */
    @ApiModelProperty("旧密码校验是否成功 true:失败， false: 成功")
    private Boolean oldPwd = Boolean.FALSE;

    /**
     * 新, 旧密码校验是否 相等
     */
    @ApiModelProperty("新密码校验是否成功 true:相等， false: 不相等")
    private Boolean equals = Boolean.FALSE;

    /**
     * 校验失败原因
     */
    @ApiModelProperty("校验失败原因")
    private String reason;

}
