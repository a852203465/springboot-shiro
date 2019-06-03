package com.unionman.shiro.service.impl;

import com.unionman.shiro.common.exception.SpringbootShiroException;
import com.unionman.shiro.entity.Role;
import com.unionman.shiro.entity.UserInfo;
import com.unionman.shiro.repository.UserInfoRepository;
import com.unionman.shiro.service.RoleService;
import com.unionman.shiro.service.UserInfoService;
import com.unionman.shiro.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 权限管理，用户信息service实现类
 * @author: Rong.Jia
 * @date: 2019/04/17 13:51
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleService sysRoleService;

    @Override
    public UserInfo findByAccount(String account) {

        return userInfoRepository.findByAccount(account);

    }

    @Override
    public Long countUserInfoByRoleId(Integer roleId) {

        // 获取角色对象
        Role role = sysRoleService.findById(roleId);

        return userInfoRepository.countUserInfosByRole(role);

    }

    @Override
    public UserInfo findByIdAndRoles(Integer userId, Role sysRole) {

        return userInfoRepository.findByIdAndRole(userId, sysRole);

    }

    @Override
    public Long countUserInfoByIdAndRoles(Integer userId, Role sysRole) {

        return userInfoRepository.countUserInfoByIdAndRole(userId, sysRole);

    }

    @Override
    public Boolean existsUserInfoByAccount(String account) {

        return userInfoRepository.existsUserInfoByAccount(account);

    }

    @Override
    @Transactional(rollbackFor = SpringbootShiroException.class)
    public void modifyPwdById(String newPwd, Integer userId) {

        userInfoRepository.modifyPwdById(newPwd, userId);

    }


}
