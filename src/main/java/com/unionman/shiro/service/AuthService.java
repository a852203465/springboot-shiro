package com.unionman.shiro.service;

import com.unionman.shiro.dto.*;
import com.unionman.shiro.vo.*;

/**
 * @description: 权限管理 service层
 * @author Rong.Jia
 * @date 2019/04/17 15:25
 */
public interface AuthService {

    /**
     * @description: 获取用户信息 并分页
     * @param pageDTO 分页条件
     * @author Rong.Jia
     * @date 2019/04/17 15:25·
     * @return PageVO<UserInfoVO> 分页数据
     */
    PageVO<UserInfoVO> findUserInfos(PageDTO pageDTO);

    /**
     * @description: 添加用户信息
     * @param userInfoDTO 用户信息
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     */
    void saveUserInfo(UserInfoDTO userInfoDTO);

    /**
     * @description: 根据用户id 删除用户
     * @param userId 用户id
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     */
    void deleteUserInfoById(Integer userId);

    /**
     * @description: 根据用户账号 获取用户信息
     * @param userAccount 用户账号
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     *  @return  UserInfoVO 用户信息
     */
    UserInfoVO findUserByAccount(String userAccount);

    /**
     * @description: 根据用户id 获取用户信息
     * @param userId 用户Id
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     * @return  UserInfoVO 用户信息
     */
    UserInfoVO findUserByUserId(Integer userId);

    /**
     * @description: 修改用户信息
     * @param userInfoDTO 用户信息
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     */
    void updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * @description: 添加权限
     * @param permissionDTO 权限信息
     * @author Rong.Jia
     * @date  2019/04/17 15:25
     */
    void savePermission(PermissionDTO permissionDTO);

    /**
     * @description: 修改权限
     * @param permissionDTO 权限信息
     * @author Rong.Jia
     * @date  2019/04/17 15:25
     */
    void updatePermission(PermissionDTO permissionDTO);

    /**
     * @description: 根据权限id获取权限
     * @param permissionId 权限id
     * @author Rong.Jia
     * @date  2019/04/17 15:25
     * @return  PermissionVO 权限信息
     */
    PermissionVO findPermissionById(Integer permissionId);

    /**
     * @description: 根据权限名获取权限
     * @param permissionName 权限名
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     * @return PermissionVO 权限信息
     */
    PermissionVO findPermissionByName(String permissionName);

    /**
     * @description: 根据权限id 删除权限
     * @param permissionId 权限id
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     */
    void deletePermissionById(Integer permissionId);

    /**
     * @description: 根据分页条件查询权限信息
     * @param pageDTO 分页查询条件
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     * @return PageVO<PermissionVO> 权限信息
     */
    PageVO<PermissionVO> findPermissions(PageDTO pageDTO);

    /**
     * @description: 添加角色信息
     * @param roleDTO 角色信息对象
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     */
    void saveRole(RoleDTO roleDTO);

    /**
     * @description: 修改角色信息
     * @param roleDTO 角色信息
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     */
    void updateSysRole(RoleDTO roleDTO);

    /**
     * @description: 根据角色Id 获取角色信息
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     * @return RoleVO 角色信息
     */
    RoleVO findRoleById(Integer roleId);

    /**
     * @description: 根据角色Id 删除角色
     * @author Rong.Jia
     * @date 2019/04/17 15:25
     */
    void deleteRoleById(Integer roleId);

    /**
     * @description: 角色用户查询
     * @param pageDTO 分页查询条件
     * @date 2019/04/17 15:25
     * @author Rong.Jia
     * @return PageVO<RoleVO> 角色信息
     */
    PageVO<RoleVO> findRoles(PageDTO pageDTO);

    /**
     * @description: 用户授权角色
     * @param userRoleAuthDTO 用户授权对象
     * @date 2019/04/17 15:25
     * @author Rong.Jia
     */
    void userRoleAuthorization(UserRoleAuthDTO userRoleAuthDTO);

    /**
     * @description: 角色授权权限
     * @param rolePermissionAuthDTO 角色授权对象
     * @date 2019/04/17 15:25
     * @author Rong.Jia
     */
    void rolePermissionAuth(RolePermissionAuthDTO rolePermissionAuthDTO);

    /**
     * @description: 根据用户账号判断用户是否存在
     * @param account 用户账号
     * @author: Rong.Jia
     * @date: 2019/04/18 11:27
     * @return Boolean true/false
     */
    Boolean existsUserInfoByAccount(String account);

    /**
     * @description: 验证密码
     * @param pwdDTO 密码参数
     * @date 2019/04/18 12:14:22
     * @return PwdVO 校验结果
     */
    PwdVO verifyPwd(PwdDTO pwdDTO);

    /**
     * @description: 修改密码
     * @param pwdDTO 密码参数
     * @date 2019/04/18 14:14:22
     * @return boolean true/false
     */
    void modifyPwd(PwdDTO pwdDTO);

    /**
     * @description: 重置用户密码
     * @param account 用户账号
     * @date 2019/04/18 13:47:22
     * @return String 重置后密码
     */
    String resetPwd(String account);

}
