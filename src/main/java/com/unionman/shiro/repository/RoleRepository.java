package com.unionman.shiro.repository;

import com.unionman.shiro.entity.Permission;
import com.unionman.shiro.entity.Role;
import com.unionman.shiro.entity.UserInfo;
import com.unionman.shiro.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 权限管理 角色持久层接口
 * @author: Rong.Jia
 * @date: 2018/6/20 11:43
 */
@Repository
public interface RoleRepository extends BaseRepository<Role> {

    /**
     * @description: 查询某用户的对应的角色。
     * @param userInfo 用户信息
     * @author: Rong.Jia
     * @date: 2018/8/8 13:54
     * @return List<Role> 获取的角色信息
     */
    List<Role> findRolesByUserInfos(UserInfo userInfo);

    /**
     * @description: 查询拥有某权限的角色
     * @param sysPermission 权限信息
     * @author: Rong.Jia
     * @date: 2018/8/8 13:54
     * @return List<Role> 获取的角色信息
     */
    List<Role> findRolesByPermissions(Permission sysPermission);

    /**
     * @description: 查询用户是否已经授权角色
     * @param userInfo 用户信息
     * @author: Rong.Jia
     * @date: 2018/8/8 13:54
     * @return Long 角色个数
     */
    Long countRoleByUserInfos(UserInfo userInfo);

    /**
     * @description: 根据角色 获取角色信息
     * @param role 角色标识
     * @author: Rong.Jia
     * @date: 2018/08/28 11:45
     * @return Role 角色信息
     */
    Role findRoleByRole(String role);

}
