package com.unionman.shiro.repository;

import com.unionman.shiro.entity.Role;
import com.unionman.shiro.entity.UserInfo;
import com.unionman.shiro.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @description: 权限管理 用户信息 持久
 * @author: Rong.Jia
 * @date: 2019/04/17 14:18
 */
@Repository
public interface UserInfoRepository extends BaseRepository<UserInfo> {

    /**
     * @description: 根据用户名查找用户实体
     * @param account 用户账号
     * @return UserInfo 用户信息
     * @author: Rong.Jia
     * @date: 2019/04/17 14:18
     * @return UserInfo 用户信息
     */
    UserInfo findByAccount(String account);

    /**
     * @description: 根据角色信息 获取关联的用户个数
     * @param role 角色信息
     * @author: Rong.Jia
     * @date: 2019/04/17 14:18
     * @return Long 返回当前角色关联的用户个数
     */
    Long countUserInfosByRole(Role role);

    /**
     * @description: 根据用户id, 角色信息 获取用户
     * @param role 角色信息
     * @param userId 用户id
     * @author: Rong.Jia
     * @date: 2019/04/17 14:18
     * @return UserInfo 用户信息
     */
    UserInfo findByIdAndRole(Integer userId, Role role);

    /**
     * @description: 根据用户id, 角色信息 获取用户个数
     * @param role 角色信息
     * @param userId 用户id
     * @author: Rong.Jia
     * @date: 2019/04/17 14:18
     * @return Long 用户个数
     */
    Long countUserInfoByIdAndRole(Integer userId, Role role);

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
    @Modifying
    @Query(value = "update um_t_users set password=?1 where id=?2", nativeQuery = true)
    void modifyPwdById(String newPwd, Integer userId);

}