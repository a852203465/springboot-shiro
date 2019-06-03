package com.unionman.shiro.repository;

import com.unionman.shiro.entity.Permission;
import com.unionman.shiro.entity.Role;
import com.unionman.shiro.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 权限信息 持久层接口
 * @author: Rong.Jia
 * @date: 2019/04/17 16:04
 */
@Repository
public interface PermissionRepository extends BaseRepository<Permission> {

    /**
     * @description: 查询某角色拥有的权限
     * @param sysRole 角色信息
     * @author: Rong.Jia
     * @date: 2019/04/17 16:04
     * @return List<Permission> 获取的权限信息
     */
    List<Permission> findPermissionsByRoles(Role sysRole);

    /**
     * @description: 根据权限名获取权限信息
     * @param permissionsName 权限名
     * @author: Rong.Jia
     * @date: 2019/04/17 16:04
     * @return List<Permission> 获取的权限信息
     */
    Permission findPermissionByName(String permissionsName);

    /**
     * @description: 查询某角色拥有的权限
     * @param sysRole 角色信息
     * @param permissionId 权限id
     * @author: Rong.Jia
     * @date: 2019/04/17 16:04
     * @return Long 角色已授权权限个数
     */
    Long countPermissionByIdAndRoles(Integer permissionId, Role sysRole);

}
