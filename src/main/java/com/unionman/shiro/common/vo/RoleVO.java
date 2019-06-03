package com.unionman.shiro.common.vo;

import com.unionman.shiro.common.dto.RoleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @description: 权限管理 角色VO
 * @author: Rong.Jia
 * @date: 2019/04/17 15:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色信息 对照对象")
public class RoleVO extends RoleDTO implements Serializable {

    private static final long serialVersionUID = -44498083173851555L;

    /**
     * 权限列表
     */
    //@ApiModelProperty("权限列表")
    //private List<PermissionVO> permissions;

    /**
     * 权限列表
     */
    @ApiModelProperty("权限列表 (key: 模块名, value 下辖权限集合)")
    private Map<String, List<PermissionVO>> permissions;

}
