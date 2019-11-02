package com.unionman.shiro.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 角色 权限数据 dto对象
 * @author Rong.Jia
 * @date 2019/04/17 14:50
 */
@Data
@ApiModel("角色 权限数据 参数对照对象")
public class RolePermissionAuthDTO implements Serializable {

    private static final long serialVersionUID = 1013134901136312507L;

    @ApiModelProperty("角色主键 ")
    @NotNull(message = "角色id 不能为null !!!")
    private Integer roleId;

    @ApiModelProperty("权限ids")
    List<Integer> permissionIds;

}
