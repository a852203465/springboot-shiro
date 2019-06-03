package com.unionman.shiro.common.dto;

import com.unionman.shiro.common.constants.RegularVerifyConstant;
import com.unionman.shiro.paramcheck.validator.groupvlidator.UserInfoGroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @description: 修改密码dto 对象
 * @author Rong.Jia
 * @date 2019/04/18 12:08:22
 */
@Data
@ApiModel("修改密码参数对照 对象")
public class PwdDTO implements Serializable {

    private static final long serialVersionUID = 3482634779913187319L;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "密码(修改密码，校验新密码时必填)长度限制 6~16位 只含有数字、字母")
    @Pattern(regexp = RegularVerifyConstant.PWD_REG, groups = UserInfoGroupValidator.class, message = "密码格式不正确, 或者长度不足6位 / 长度超出限制")
    private String newPwd;

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码",required = true)
    @NotBlank(message = "旧密码不能空", groups = UserInfoGroupValidator.class)
    private String oldPwd;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", required = true)
    @NotBlank(message = "用户账号不能为空", groups = UserInfoGroupValidator.class)
    private String account;

}
