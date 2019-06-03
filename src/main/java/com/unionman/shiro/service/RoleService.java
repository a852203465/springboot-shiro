package com.unionman.shiro.service;

import com.unionman.shiro.entity.Role;
import com.unionman.shiro.entity.UserInfo;
import com.unionman.shiro.service.base.BaseService;

import java.util.List;

/**
 * @description: 角色service
 * @author Rong.Jia
 * @date 2019/04/17 14:57
 */
public interface RoleService extends BaseService<Role> {

    /**
     * @description: 查询某用户名称 的对应的角色。
     * @param account 用户名称
     * @author Rong.Jia
     * @date 2019/04/17 14:57
     * @return List<Role> 获取的角色信息
     */
    List<Role> findRolesByUserInfoAccount(String account);

    /**
     * @description: 根据权限名 查询拥有某权限的角色
     * @param permissionsName 权限名
     * @author Rong.Jia
     * @date 2019/04/17 14:57
     * @return List<Role> 获取的角色信息
     */
    List<Role> findRolesByPermissionsName(String permissionsName);

    /**
     * @description: 根据权限id 查询拥有某权限的角色
     * @param permissionId 权限id
     * @author Rong.Jia
     * @date 2019/04/17 14:57
     * @return List<Role> 获取的角色信息
     */
    List<Role> findRolesByPermissionId(Integer permissionId);

    /**
     * @description: 查询用户是否已经授权角色
     * @param userInfo 用户信息
     * @author Rong.Jia
     * @date 2019/04/17 14:57
     * @return Long 角色个数
     */
    Long countRoleByUserInfos(UserInfo userInfo);

    /**
     * @description: 根据用户id 查询用户是否已经授权角色
     * @param userId 用户id
     * @author Rong.Jia
     * @date 2019/04/17 14:57
     * @return Long 角色个数
     */
    Long countRoleByUserInfos(Integer userId);

    /**
     * @description: 根据用户id 查询用户是否已经授权角色
     * @param role 角色标识
     * @author Rong.Jia
     * @date 2019/04/17 14:57
     * @return Role 角色信息
     */
    Role findRoleByRole(String role);

}
