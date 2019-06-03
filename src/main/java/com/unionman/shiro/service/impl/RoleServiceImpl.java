package com.unionman.shiro.service.impl;

import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.utils.AssertUtils;
import com.unionman.shiro.entity.Permission;
import com.unionman.shiro.entity.Role;
import com.unionman.shiro.entity.UserInfo;
import com.unionman.shiro.repository.RoleRepository;
import com.unionman.shiro.service.PermissionService;
import com.unionman.shiro.service.RoleService;
import com.unionman.shiro.service.UserInfoService;
import com.unionman.shiro.service.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 角色service 实现类
 * @author: Rong.Jia
 * @date: 2019/04/17 13:55
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleRepository sysRoleRepository;

    @Autowired
    private PermissionService sysPermissionService;

    @Override
    public List<Role> findRolesByUserInfoAccount(String account){

        UserInfo userInfo = userInfoService.findByAccount(account);

        AssertUtils.isNull(userInfo, ResponseEnum.THE_LIST_OF_USER_INFO_IS_EMPTY);

        return sysRoleRepository.findRolesByUserInfos(userInfo);

    }

    @Override
    public List<Role> findRolesByPermissionsName(String permissionsName ){

        AssertUtils.isNull(permissionsName, ResponseEnum.PERMISSION_RESOURCE_NAME_CANNOT_BE_EMPTY);

        Permission sysPermission = sysPermissionService.findPermissionByName(permissionsName);

        AssertUtils.isNull(sysPermission, ResponseEnum.THE_LIST_OF_PERMISSION_IS_EMPTY);

        return sysRoleRepository.findRolesByPermissions(sysPermission);

    }

    @Override
    public List<Role> findRolesByPermissionId(Integer permissionId ){

        AssertUtils.isNull(permissionId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY);

        Permission sysPermission = sysPermissionService.findById(permissionId);

        AssertUtils.isNull(sysPermission, ResponseEnum.THE_LIST_OF_PERMISSION_IS_EMPTY);

        return sysRoleRepository.findRolesByPermissions(sysPermission);

    }

    @Override
    public Long countRoleByUserInfos(UserInfo userInfo){

        return sysRoleRepository.countRoleByUserInfos(userInfo);

    }

    @Override
    public Long countRoleByUserInfos(Integer userId){

        AssertUtils.isNull(userId,ResponseEnum.THE_ID_CANNOT_BE_EMPTY);

        UserInfo userInfo = userInfoService.findById(userId);

        return countRoleByUserInfos(userInfo);

    }

    @Override
    public Role findRoleByRole(String role) {

        return sysRoleRepository.findRoleByRole(role);

    }
}
