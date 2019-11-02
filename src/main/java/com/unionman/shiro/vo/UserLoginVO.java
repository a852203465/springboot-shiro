package com.unionman.shiro.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户登录 vo 对象
 * @author Rong.Jia
 * @date 2019/04/17 16:55
 */
@Data
@ApiModel("用户登录信息 对照对象")
public class UserLoginVO implements Serializable {

    private static final long serialVersionUID = -6613810301474204417L;

    /**
     *  鉴权参数
     */
    @ApiModelProperty("accessToken")
    private String accessToken;

    /**
     *  鉴权参数，用来刷新accessToken，refreshToken
     */
    @ApiModelProperty("鉴权参数，用来刷新accessToken")
    private String refreshToken;

    /**
     *  系统生成并返回accessToken的失效时间，单位为秒
     */
    @ApiModelProperty(" 系统生成并返回accessToken的失效时间，时间戳")
    private Long expiresIn;

    /**
     * 登录用户信息
     */
    @ApiModelProperty("登录用户信息")
    private UserInfoVO userInfo;

}
