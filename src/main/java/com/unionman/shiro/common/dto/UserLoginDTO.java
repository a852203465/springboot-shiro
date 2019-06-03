package com.unionman.shiro.common.dto;

import com.unionman.shiro.paramcheck.validator.groupvlidator.UserLoginGroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description: 用户登录 dto
 * @author Rong.Jia
 * @date 2019/04/17 16:27
 */
@Data
@ApiModel("用户登录 参数对照对象")
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 611509515495545755L;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "账号不能为空", groups = UserLoginGroupValidator.class)
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空", groups = UserLoginGroupValidator.class)
    private String password;

}
