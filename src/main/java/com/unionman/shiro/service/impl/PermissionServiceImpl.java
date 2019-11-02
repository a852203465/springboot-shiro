package com.unionman.shiro.service.impl;

import com.unionman.shiro.enums.ResponseEnum;
import com.unionman.shiro.utils.AssertUtils;
import com.unionman.shiro.entity.Permission;
import com.unionman.shiro.entity.Role;
import com.unionman.shiro.repository.PermissionRepository;
import com.unionman.shiro.service.PermissionService;
import com.unionman.shiro.service.RoleService;
import com.unionman.shiro.service.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 权限信息 service层接口实现类
 * @author: Rong.Jia
 * @date: 2019/04/17 14:07
 */
@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleService sysRoleService;

   @Override
    public List<Permission> findPermissionsByRoleId(Integer roleId){

       Role sysRole = sysRoleService.findById(roleId);

       AssertUtils.isNull(sysRole, ResponseEnum.THE_LIST_OF_ROLE_IS_EMPTY);

       return permissionRepository.findPermissionsByRoles(sysRole);

   }

   @Override
    public List<Permission> findPermissionsByUserInfoName(String account){

       //先通过用户查询角色
       List<Role> sysRoleList = sysRoleService.findRolesByUserInfoAccount(account);

       // 存储权限信息
       List<Permission> permissionList = new ArrayList<>();

       if(sysRoleList.size() > 0){
           for (Role sysRole:  sysRoleList){

               if(AssertUtils.isNotNull(sysRole)){

                   //通过角色再查询权限
                   List<Permission> permissions = permissionRepository.findPermissionsByRoles(sysRole);
                   permissionList.addAll(permissions);

               }
           }
       }

       return permissionList;
   }

   @Override
    public Long countPermissionByIdAndRoles(Integer permissionId, Role sysRole){

       return permissionRepository.countPermissionByIdAndRoles(permissionId, sysRole);

   }

    @Override
    public Permission findPermissionByName(String permissionsName) {

       AssertUtils.isNull(permissionsName, ResponseEnum.PERMISSION_RESOURCE_NAME_CANNOT_BE_EMPTY);

        return permissionRepository.findPermissionByName(permissionsName);

    }
}
