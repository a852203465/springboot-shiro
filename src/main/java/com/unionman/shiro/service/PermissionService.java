package com.unionman.shiro.service;


import com.unionman.shiro.entity.Permission;
import com.unionman.shiro.entity.Role;
import com.unionman.shiro.service.base.BaseService;

import java.util.List;

/**
 * @description: 权限信息 service层接口类
 * @author Rong.Jia
 * @date 2018/6/20 16:05
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * @description: 根据角色id查询某角色拥有的权限
     * @param roleId 角色id
     * @author Rong.Jia
     * @date 2018/8/8 13:54
     * @return List<Permission> 获取的权限信息
     */
    List<Permission> findPermissionsByRoleId(Integer roleId);

    /**
     * @description: 查询某用户拥有的权限。
     * @param account 用户账号
     * @author Rong.Jia
     * @date 2018/8/8 13:54
     * @return List<Permission> 获取的权限信息
     */
    List<Permission> findPermissionsByUserInfoName(String account);

    /**
     * @description: 查询某角色拥有的权限
     * @param sysRole 角色信息
     * @author Rong.Jia
     * @date 2018/8/8 13:54
     * @return Long 角色已授权权限个数
     */
    Long countPermissionByIdAndRoles(Integer permissionId, Role sysRole);

    /**
     * @description: 根据权限名获取权限信息
     * @param permissionsName 权限名
     * @author: Rong.Jia
     * @date: 2019/04/17 16:04
     * @return List<Permission> 获取的权限信息
     */
    Permission findPermissionByName(String permissionsName);

}
