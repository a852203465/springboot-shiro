package com.unionman.shiro.service.impl;

import com.unionman.shiro.common.config.AuthConfig;
import com.unionman.shiro.common.constants.AuthConstant;
import com.unionman.shiro.common.constants.CommonConstant;
import com.unionman.shiro.common.constants.NumberConstant;
import com.unionman.shiro.common.dto.*;
import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.CustomUnauthorizedException;
import com.unionman.shiro.common.exception.SpringbootShiroException;
import com.unionman.shiro.common.utils.*;
import com.unionman.shiro.common.vo.*;
import com.unionman.shiro.entity.Permission;
import com.unionman.shiro.entity.Role;
import com.unionman.shiro.entity.UserInfo;
import com.unionman.shiro.service.AuthService;
import com.unionman.shiro.service.PermissionService;
import com.unionman.shiro.service.RoleService;
import com.unionman.shiro.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: 权限管理 service层 实现类
 * @author: Rong.Jia
 * @date: 2019/04/17 14:08
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PermissionService sysPermissionService;

    @Autowired
    private RoleService sysRoleService;

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public PageVO<UserInfoVO> findUserInfos(PageDTO pageDTO){

        PageVO<UserInfoVO> pageVO = new PageVO<>();

        String authorization = ServletUtils.getHttpRequest().getHeader(AuthConstant.AUTHORIZATION);

        if (AssertUtils.isNull(authorization)) {
            throw new CustomUnauthorizedException(ResponseEnum.UNAUTHORIZED);
        }

        // 当前用户
        String account = jwtUtils.getClaim(authorization, AuthConstant.ACCOUNT);

        if(AssertUtils.isNumberLess0(pageDTO.getCurrentPage())){

            if (!AuthConstant.ADMINISTRATOR.equals(account)){

                UserInfo userInfo = userInfoService.findByAccount(account);
                List<UserInfoVO> voList = Collections.singletonList(getUserInfoVO(userInfo));
                pageVO.setTotal(voList.size());
                pageVO.setRecords(voList);

            }else {

                List<UserInfo> userInfoList = userInfoService.findAll();
                List<UserInfoVO> userInfoVOList = getUserInfoVOS(userInfoList);
                pageVO.setTotal(userInfoVOList.size());
                pageVO.setRecords(userInfoVOList);

            }

        }else if (!AuthConstant.ADMINISTRATOR.equals(account)) {

            UserInfo userInfo = userInfoService.findByAccount(account);

            List<UserInfoVO> voList = Collections.singletonList(getUserInfoVO(userInfo));

            pageVO.setTotal(voList.size());
            pageVO.setRecords(voList);

        } else {

            SortDTO sortDTO  = new SortDTO();
            sortDTO.setOrderType(pageDTO.getOrderType());
            sortDTO.setOrderField(pageDTO.getOrderField());

            Page<UserInfo> page = userInfoService.findAll(PageableUtils.basicPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), sortDTO));

            pageVO.setTotalPages(page.getTotalPages());
            pageVO.setHasNext(page.hasNext());
            pageVO.setHasPrevious(page.hasPrevious());
            pageVO.setIsFirst(page.isFirst());
            pageVO.setIsLast(page.isLast());
            pageVO.setRecords(getUserInfoVOS(page.getContent()));
            pageVO.setTotal((int)page.getTotalElements());
            pageVO.setCurrentPage(pageDTO.getCurrentPage());
            pageVO.setPageSize(pageDTO.getPageSize());

        }

        AssertUtils.isEmptyList(pageVO.getRecords(), ResponseEnum.THE_LIST_OF_USER_INFO_IS_EMPTY);

        return pageVO;
    }

    @Override
    public void saveUserInfo(UserInfoDTO userInfoDTO){

        UserInfo userInfo = userInfoService.findByAccount(userInfoDTO.getAccount());

        if (AssertUtils.isNotNull(userInfo)) {
            throw new CustomUnauthorizedException(ResponseEnum.ACCOUNT_ALREADY_EXISTS);
        }

        // 判断密码长度
        if (userInfoDTO.getPassword().length() > AuthConstant.PASSWORD_MAX_LEN) {
                throw new SpringbootShiroException(ResponseEnum.PASSWORD_LENGTH_LIMIT_EXCEEDED);
        }

        userInfo = new UserInfo();

        BeanUtils.copyProperties(userInfoDTO, userInfo);

        userInfo.setCreatedTime(DateUtils.getTimestamp());

        // 角色
        Role role = sysRoleService.findById(NumberConstant.TWO);
        userInfo.setRole(role);

        // 加密后的密码
        String aeSencode = EncryptUtils.encodeAES(userInfoDTO.getAccount() + userInfoDTO.getPassword(), authConfig.getEncryptAESKey());
        userInfo.setPassword(aeSencode);

        userInfoService.insetNew(userInfo);

    }

    @Override
    public void deleteUserInfoById(Integer userId){

        UserInfo userInfo = userInfoService.findById(userId);

        AssertUtils.isNull(userInfo, ResponseEnum.USERINFO_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED);

        // 判断是否管理员
        if(AuthConstant.ADMINISTRATOR.equals(userInfo.getAccount())){
            throw new SpringbootShiroException(ResponseEnum.SYSTEM_ADMINISTRATOR_CANNOT_DELETE);
        }

        // 获取token， 并判断是否为空
        String token = ServletUtils.getHttpRequest().getHeader(AuthConstant.AUTHORIZATION);
        if (AssertUtils.isNull(token)) {
            throw new CustomUnauthorizedException(ResponseEnum.UNAUTHORIZED);
        }

        //  解析token, 获取账号, 并获取该账号信息  判断删除用户是否是当前登录用户
        if(userId.equals(userInfoService.findByAccount(jwtUtils.getClaim(token, AuthConstant.ACCOUNT)).getId())){
            throw new SpringbootShiroException(ResponseEnum.CURRENT_USER_CANNOT_DELETE);
        }

        userInfoService.deleteById(userId);

    }

    @Override
    public UserInfoVO findUserByAccount(String userAccount){

        UserInfo userInfo = userInfoService.findByAccount(userAccount);

        return getUserInfoVO(userInfo);

    }

    @Override
    public UserInfoVO findUserByUserId(Integer userId){

        UserInfo userInfo = userInfoService.findById(userId);

        AssertUtils.isNull(userInfo, ResponseEnum.THE_LIST_OF_USER_INFO_IS_EMPTY);

        return getUserInfoVO(userInfo);

    }

    @Override
    public  void updateUserInfo(UserInfoDTO userInfoDTO){

        UserInfo userInfo = userInfoService.findById(userInfoDTO.getId());

        AssertUtils.isNull(userInfo, ResponseEnum.USERINFO_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED);

        userInfo.setRole(userInfo.getRole());

        userInfo.setUpdatedTime(DateUtils.getTimestamp());
        userInfo.setUpdatedUser(userInfoDTO.getUpdatedUser());
        userInfo.setName(userInfoDTO.getName());
        userInfo.setMobile(userInfo.getMobile());
        userInfo.setGender(userInfo.getGender());

        userInfoService.modify(userInfo);

    }

    @Override
    public void savePermission(PermissionDTO permissionDTO){

        AssertUtils.isNotNull(sysPermissionService.findPermissionByName(permissionDTO.getName()), ResponseEnum.PERMISSION_ALREADY_EXISTS);

        permissionDTO.setCreatedTime(DateUtils.getTimestamp());

        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDTO, permission);

        sysPermissionService.insetNew(permission);

    }

    @Override
    public void updatePermission(PermissionDTO permissionDTO){

        Permission permission = sysPermissionService.findById(permissionDTO.getId());

        AssertUtils.isNull(permission, ResponseEnum.PERMISSION_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED);

        permission.setUpdatedTime(DateUtils.getTimestamp());
        permission.setName(permissionDTO.getName());
        permission.setPermission(permissionDTO.getPermission());

        sysPermissionService.modify(permission);

    }

    @Override
    public PermissionVO findPermissionById(Integer permissionId){

        Permission permission = sysPermissionService.findById(permissionId);

        return getPermissionVO(permission);

    }

    @Override
    public PermissionVO findPermissionByName(String permissionName){

        Permission permission = sysPermissionService.findPermissionByName(permissionName);

        return getPermissionVO(permission);

    }

    @Override
    public void deletePermissionById(Integer permissionId){

        List<Role> sysRoles = sysRoleService.findRolesByPermissionId(permissionId);
        if(sysRoles.size() > 0){
            throw new SpringbootShiroException(ResponseEnum.SYSPERMISSION_RELATED_SYSROLE);
        }

        sysPermissionService.deleteById(permissionId);

    }

    @Override
    public PageVO<PermissionVO> findPermissions(PageDTO pageDTO){

        // 权限返回结果数据
        PageVO<PermissionVO> pageVO = new PageVO<PermissionVO>();

        if(pageDTO.getCurrentPage() < 0){

            List<Permission> permissionList = sysPermissionService.findAll();

            List<PermissionVO> permissionVOList = getPermissionVOS(permissionList);

            pageVO.setTotal(permissionVOList.size());
            pageVO.setRecords(permissionVOList);

        }else {

            SortDTO sortDTO  = new SortDTO();
            sortDTO.setOrderType(pageDTO.getOrderType());
            sortDTO.setOrderField(pageDTO.getOrderField());

            Page<Permission> page = sysPermissionService.findAll(PageableUtils.basicPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), sortDTO));

            pageVO.setTotalPages(page.getTotalPages());
            pageVO.setHasNext(page.hasNext());
            pageVO.setHasPrevious(page.hasPrevious());
            pageVO.setIsFirst(page.isFirst());
            pageVO.setIsLast(page.isLast());
            pageVO.setRecords(getPermissionVOS(page.getContent()));
            pageVO.setTotal((int)page.getTotalElements());
            pageVO.setCurrentPage(pageDTO.getCurrentPage());
            pageVO.setPageSize(pageDTO.getPageSize());

        }

        AssertUtils.isEmptyList(pageVO.getRecords(), ResponseEnum.THE_LIST_OF_PERMISSION_IS_EMPTY);

        return pageVO;

    }

    @Override
    public void saveRole(RoleDTO roleDTO){

        AssertUtils.isNotNull(sysRoleService.findRoleByRole(roleDTO.getRole()), ResponseEnum.ROLE_ALREADY_EXISTS);

        Role sysRole = new Role();
        BeanUtils.copyProperties(roleDTO, sysRole);

        Set<Integer> permissionIds = roleDTO.getPermissionIds();

        if (AssertUtils.isNotNull(permissionIds)) {

            Set<Permission> permissions =new HashSet<>();
            for (Integer permissionId : permissionIds){
                permissions.add(sysPermissionService.findById(permissionId));
            }
            sysRole.setPermissions(permissions);

        }

        sysRole.setCreatedTime(DateUtils.getTimestamp());

        sysRoleService.insetNew(sysRole);

    }

    @Override
    public void updateSysRole(RoleDTO sysRoleDTO){

        Role role = sysRoleService.findById(sysRoleDTO.getId());

        if (AssertUtils.isNull(role)){
            throw new SpringbootShiroException(ResponseEnum.ROLE_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED);
        }

        Long aLong = userInfoService.countUserInfoByRoleId(sysRoleDTO.getId());

        if(aLong > 0){
            throw new SpringbootShiroException(ResponseEnum.DATA_QUOTE);
        }

        role.setUpdatedTime(DateUtils.getTimestamp());
        role.setRole(sysRoleDTO.getRole());

        Set<Integer> permissionIds = sysRoleDTO.getPermissionIds();

        Set<Permission> sysPermissions =new HashSet<>();
        for (Integer permissionId : permissionIds){
            sysPermissions.add(sysPermissionService.findById(permissionId));
        }

        role.setPermissions(sysPermissions);


        sysRoleService.modify(role);

    }

    @Override
    public RoleVO findRoleById(Integer roleId){

        Role sysRole = sysRoleService.findById(roleId);

        return getRoleVO(sysRole);

    }

    @Override
    public  void deleteRoleById(Integer roleId){

        Role sysRole = sysRoleService.findById(roleId);

        AssertUtils.isNull(sysRole, ResponseEnum.ROLE_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED);

        //获取角色的关联用户个数
        Long aLong = userInfoService.countUserInfoByRoleId(roleId);

        if(aLong > 0){
            throw new SpringbootShiroException(ResponseEnum.DATA_QUOTE);
        }

        sysRoleService.deleteById(roleId);

    }

    @Override
    public PageVO<RoleVO> findRoles(PageDTO pageDTO){

        PageVO<RoleVO> pageVO = new PageVO<>();

        if(pageDTO.getCurrentPage() < 0){

            List<Role> roleList = sysRoleService.findAll();

            List<RoleVO> roleVOList = getRoleVOS(roleList);

            pageVO.setTotal(roleVOList.size());
            pageVO.setRecords(roleVOList);

        }else {

            SortDTO sortDTO  = new SortDTO();
            sortDTO.setOrderType(pageDTO.getOrderType());
            sortDTO.setOrderField(pageDTO.getOrderField());

            Page<Role> page = sysRoleService.findAll(PageableUtils.basicPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), sortDTO));

            pageVO.setTotalPages(page.getTotalPages());
            pageVO.setHasNext(page.hasNext());
            pageVO.setHasPrevious(page.hasPrevious());
            pageVO.setIsFirst(page.isFirst());
            pageVO.setIsLast(page.isLast());
            pageVO.setRecords(getRoleVOS(page.getContent()));
            pageVO.setTotal((int)page.getTotalElements());
            pageVO.setCurrentPage(pageDTO.getCurrentPage());
            pageVO.setPageSize(pageDTO.getPageSize());

        }

        AssertUtils.isEmptyList(pageVO.getRecords(), ResponseEnum.THE_LIST_OF_ROLE_IS_EMPTY);

        return pageVO;
    }

    @Override
    public void userRoleAuthorization(UserRoleAuthDTO userRoleAuthDTO){

        //用户信息
        UserInfo userInfoEntity = userInfoService.findById(userRoleAuthDTO.getUserId());

        Role sysRole = sysRoleService.findById(userRoleAuthDTO.getRoleId());

        Long aLong = sysRoleService.countRoleByUserInfos(userInfoEntity);

        //判断该用户是否已经授权
        if (aLong > 0){
            throw new CustomUnauthorizedException(ResponseEnum.USER_HAS_BEEN_AUTHORIZED);
        }

        userInfoEntity.setRole(sysRole);

        userInfoService.insetNew(userInfoEntity);

    }

    @Override
    public void rolePermissionAuth(RolePermissionAuthDTO rolePermissionAuthDTO){

        // 权限信息集合, 用于批量给角色添加权限菜单
//        List<SysPermission> sysPermissionList = new ArrayList<>();

        Set<Permission> sysPermissions = new HashSet<>();

        // 角色 信息
        Role sysRole = sysRoleService.findById(rolePermissionAuthDTO.getRoleId());

        for (Integer permissionId : rolePermissionAuthDTO.getPermissionIds()){

            Long aLong = sysPermissionService.countPermissionByIdAndRoles(permissionId, sysRole);

            // 判断该角色是否已授权该权限
            if(aLong > 0){
                throw new CustomUnauthorizedException(ResponseEnum.ROLE_HAS_AUTHORIZED_MENU);
            }

            //权限信息
            Permission sysPermission = sysPermissionService.findById(permissionId);

            sysPermissions.add(sysPermission);

        }

        sysRole.setPermissions(sysPermissions);

        sysRoleService.insetNew(sysRole);

    }

    @Override
    public Boolean existsUserInfoByAccount(String account) {

        AssertUtils.isNull(account, ResponseEnum.ACCOUNT_IS_EMPTY);

        return userInfoService.existsUserInfoByAccount(account);

    }

    @Override
    public PwdVO verifyPwd(PwdDTO pwdDTO) {

        UserInfo userInfo = userInfoService.findByAccount(pwdDTO.getAccount());

        AssertUtils.isNull(userInfo, ResponseEnum.USERINFO_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED);

        // 密码进行AES解密
        String decodeAES = EncryptUtils.decodeAES(userInfo.getPassword(), authConfig.getEncryptAESKey());

        PwdVO pwdVO = new PwdVO();

        // 校验原密码
        if (!AssertUtils.isEquals(decodeAES, pwdDTO.getAccount() + pwdDTO.getOldPwd())) {

            pwdVO.setOldPwd(Boolean.TRUE);
            pwdVO.setReason(ResponseEnum.PASSWORD_CHECK_FAILED.getMessage());

            return pwdVO;

        }

        // 校验新密码与旧密码是否相同
        if (AssertUtils.isNotNull(pwdDTO.getNewPwd())) {
            if (AssertUtils.isEquals(decodeAES, pwdDTO.getAccount() + pwdDTO.getNewPwd())) {

                pwdVO.setEquals(Boolean.TRUE);
                pwdVO.setReason(ResponseEnum.THE_OLD_PASSWORD_IS_THE_SAME_AS_THE_NEW_ONE.getMessage());
                return pwdVO;

            }
        }

        return pwdVO;

    }

    @Override
    public void modifyPwd(PwdDTO pwdDTO) {

        // 判断密码长度
        if (pwdDTO.getNewPwd().length() > AuthConstant.PASSWORD_MAX_LEN) {
            throw new SpringbootShiroException(ResponseEnum.PASSWORD_LENGTH_LIMIT_EXCEEDED);
        }

        UserInfo userInfo = userInfoService.findByAccount(pwdDTO.getAccount());

        if (AssertUtils.isNotNull(verifyPwd(pwdDTO).getOldPwd()) && verifyPwd(pwdDTO).getOldPwd()) {
            throw new SpringbootShiroException(ResponseEnum.PASSWORD_CHECK_FAILED);
        }else if (AssertUtils.isNotNull(verifyPwd(pwdDTO).getEquals()) && verifyPwd(pwdDTO).getEquals()) {
            throw new SpringbootShiroException(ResponseEnum.THE_OLD_PASSWORD_IS_THE_SAME_AS_THE_NEW_ONE);
        }

        // 加密
        String aeSencode = EncryptUtils.encodeAES(pwdDTO.getAccount() + pwdDTO.getNewPwd(), authConfig.getEncryptAESKey());

        userInfoService.modifyPwdById(aeSencode, userInfo.getId());

    }

    @Override
    public String resetPwd(String account) {

        UserInfo userInfo = userInfoService.findByAccount(account);

        AssertUtils.isNull(userInfo, ResponseEnum.USERINFO_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED);

        // 随机数
        //String randomNumeric = RandomStringUtils.randomNumeric(NumberConstant.ONE + NumberConstant.FIVE, NumberConstant.SIXTEEN);

        // 加密
        String encodeAES = EncryptUtils.encodeAES(userInfo.getAccount() + AuthConstant.DEFAULT_PASSWORD, authConfig.getEncryptAESKey());

        userInfoService.modifyPwdById(encodeAES, userInfo.getId());

        return AuthConstant.DEFAULT_PASSWORD;

    }

    private List<UserInfoVO> getUserInfoVOS(List<UserInfo> userInfoList) {
        List<UserInfoVO> userInfoVOList = new ArrayList<>();

        for (UserInfo userInfo : userInfoList) {

            if (AssertUtils.isNotNull(userInfo)) {
                UserInfoVO userInfoVO = getUserInfoVO(userInfo);
                userInfoVOList.add(userInfoVO);
            }

        }
        return userInfoVOList;
    }

    private UserInfoVO getUserInfoVO(UserInfo userInfo) {

        UserInfoVO userInfoVO = new UserInfoVO();

        if (AssertUtils.isNotNull(userInfo)) {

            // 用户信息
            BeanUtils.copyProperties(userInfo, userInfoVO);

            // 角色信息
            Role role = userInfo.getRole();

            if (AssertUtils.isNotNull(role)) {

                RoleVO roleVO = getRoleVO(role);

                // 权限资源
                if (AssertUtils.isNotNull(role.getPermissions())) {
                    Set<Permission> permissions = role.getPermissions();
                    List<PermissionVO> permissionVOList = getPermissionVOS(new ArrayList<>(permissions));
                    roleVO.setPermissions(permissionModuleDistinguish(permissionVOList));
                }

                userInfoVO.setRole(roleVO);
            }

        }

        return userInfoVO;
    }

    /**
     * @description: 对权限列表进行模块区分
     * @param permissions 权限列表
     * @author Rong.Jia
     * @date 2019/05/30 16:33:22
     * @return Map<String, List<PermissionVO>>  key: 模块, value: 权限列表集合
     */
    private Map<String, List<PermissionVO>> permissionModuleDistinguish (List<PermissionVO> permissions) {

        Map<String, List<PermissionVO>> permissionVOMap = new HashMap<>();

        if (AssertUtils.isNotNull(permissions)) {

            for (PermissionVO permissionVO : permissions) {

                List<PermissionVO> permissionVOList = new ArrayList<>();

                if (AssertUtils.isNotNull(permissionVO)) {

                    // 获取模块
                    String permission = permissionVO.getPermission();
                    String[] moduleArr = StringUtils.split(permission, CommonConstant.COLON);

                    String module = moduleArr[NumberConstant.ZERO];

                    if (permissionVOMap.containsKey(module)) {
                        permissionVO.setPermission(moduleArr[NumberConstant.ONE]);
                        permissionVOMap.get(module).add(permissionVO);
                    }else {
                        permissionVO.setPermission(moduleArr[NumberConstant.ONE]);
                        permissionVOList.add(permissionVO);
                        permissionVOMap.put(module, permissionVOList);
                    }
                }
            }
        }

        return permissionVOMap;

    }

    private PermissionVO getPermissionVO(Permission permission) {
        PermissionVO permissionVO = new PermissionVO();

        if (AssertUtils.isNotNull(permission)) {
            BeanUtils.copyProperties(permission, permissionVO);
        }
        return permissionVO;
    }

    private List<PermissionVO> getPermissionVOS(List<Permission> permissionList) {

        List<PermissionVO> permissionVOList = new ArrayList<>();

        for (Permission permission : permissionList) {
            PermissionVO permissionVO = getPermissionVO(permission);
            permissionVOList.add(permissionVO);

        }
        return permissionVOList;
    }

    private RoleVO getRoleVO(Role sysRole) {
        RoleVO roleVO = new RoleVO();

        if (AssertUtils.isNotNull(sysRole)) {
            BeanUtils.copyProperties(sysRole, roleVO);
        }
        return roleVO;
    }

    private List<RoleVO> getRoleVOS(List<Role> roleList) {
        List<RoleVO> roleVOList = new ArrayList<>();

        for (Role role : roleList) {

            RoleVO roleVO = getRoleVO(role);
            roleVOList.add(roleVO);
        }
        return roleVOList;
    }

}
