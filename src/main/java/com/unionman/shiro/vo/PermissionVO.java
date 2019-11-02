package com.unionman.shiro.vo;

import com.unionman.shiro.dto.PermissionDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description: 权限管理 角色VO
 * @author: Rong.Jia
 * @date: 2019/04/17 15:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色信息 对照对象")
public class PermissionVO extends PermissionDTO implements Serializable {

    private static final long serialVersionUID = -3673656694763259051L;

}
