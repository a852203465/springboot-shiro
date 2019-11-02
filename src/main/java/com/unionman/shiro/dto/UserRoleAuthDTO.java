package com.unionman.shiro.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户授权角色 dto 对象
 * @author Rong.Jia
 * @date 2019/04/17 14:50
 */
@Data
@ApiModel("用户授权角色 参数对照对象")
public class UserRoleAuthDTO implements Serializable {

    private static final long serialVersionUID = -7151835983773644794L;

    @ApiModelProperty("用户主键")
    private Integer userId;

    /**
     * 角色用户信息
     */
    @ApiModelProperty("角色id")
    private Integer roleId;

}
