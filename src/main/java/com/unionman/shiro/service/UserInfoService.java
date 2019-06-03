package com.unionman.shiro.service;

import com.unionman.shiro.entity.Role;
import com.unionman.shiro.entity.UserInfo;
import com.unionman.shiro.service.base.BaseService;

/**
 * @description: 权限管理，用户信息service
 * @author: Rong.Jia
 * @date: 2019/04/17 14:57
 */
public interface UserInfoService extends BaseService<UserInfo> {

    /**
     * @description: 根据用户账号 获取该用户信息
     * @param account 用户 账号
     * @author: Rong.Jia
     * @date: 2019/04/17 14:57
     * @return UserInfo 用户信息
     */
    UserInfo findByAccount(String account);

    /**
     * @description: 根据角色id 获取 获取当前角色是否有关联用户
     * @param roleId 角色id
     * @author: Rong.Jia
     * @date: 2019/04/17 14:57
     * @return Long 返回当前角色关联的用户个数
     */
    Long countUserInfoByRoleId(Integer roleId);

    /**
     * @description: 根据用户id, 角色信息 获取用户
     * @param sysRole 角色信息
     * @param userId 用户id
     * @author: Rong.Jia
     * @date: 2019/04/17 14:57
     * @return UserInfo 用户信息
     */
    UserInfo findByIdAndRoles(Integer userId, Role sysRole);

    /**
     * @description: 根据用户id, 角色信息 获取用户个数
     * @param sysRole 角色信息
     * @param userId 用户id
     * @author: Rong.Jia
     * @date: 2019/04/17 14:57
     * @return Long 用户个数
     */
    Long countUserInfoByIdAndRoles(Integer userId, Role sysRole);

    /**
     * @description: 根据用户账号判断用户是否存在
     * @param account 用户账号
     * @author: Rong.Jia
     * @date: 2019/04/18 11:27
     * @return Boolean true/false
     */
    Boolean existsUserInfoByAccount(String account);

    /**
     * @description: 根据用户id 修改密码
     * @param newPwd 密码
     * @param userId 用户id
     * @date 2019/04/18 14:33
     */
    void modifyPwdById(String newPwd, Integer userId);


}
