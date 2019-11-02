package com.unionman.shiro.dto;

import com.unionman.shiro.domain.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

/**
 * @description: 角色信息dto 对象
 * @date 2019/04/17 14:16:22
 * @author Rong.Jia
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色信息参数对照 对象")
public class RoleDTO extends Base implements Serializable {

    private static final long serialVersionUID = -9793520535175777L;

    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    @ApiModelProperty(value = "角色标识(必填，如:\"管理员\")")
    private String role;

    /**
     * 权限ids
     */
    @ApiModelProperty(value = "权限id集合(必填) ", required = true)
    private Set<Integer> permissionIds;

}
