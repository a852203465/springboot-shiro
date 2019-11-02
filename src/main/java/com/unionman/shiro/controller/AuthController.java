package com.unionman.shiro.controller;

import com.unionman.shiro.constants.AuthConstant;
import com.unionman.shiro.constants.CommonConstant;
import com.unionman.shiro.dto.PageDTO;
import com.unionman.shiro.dto.PwdDTO;
import com.unionman.shiro.dto.UserInfoDTO;
import com.unionman.shiro.vo.*;
import com.unionman.shiro.paramcheck.validator.groupvlidator.IdGroupValidator;
import com.unionman.shiro.paramcheck.validator.groupvlidator.UserInfoGroupValidator;
import com.unionman.shiro.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 权限管理 Controller层
 * @author Rong.Jia
 * @date 2019/04/16 14:01
 */
@Slf4j
@Validated
@RestController
@RequiresAuthentication
@RequestMapping("/auth/")
@Api(value = "权限管理", tags = "权限管理 controller，对接页面")
public class AuthController extends AbstractController {

    @Autowired
    private AuthService authService;

    @ApiOperation("添加用户信息")
    @RequiresRoles(value = AuthConstant.ADMINISTRATOR)
    @PostMapping(value = "user", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO saveUserInfo (@RequestBody @Validated({UserInfoGroupValidator.class}) UserInfoDTO userInfoDTO) {

        log.info("saveUserInfo {}", userInfoDTO.toString());

        userInfoDTO.setCreatedUser(getAccount());
        authService.saveUserInfo(userInfoDTO);

        return ResponseVO.success();

    }

    @ApiOperation("删除用户")
    @RequiresRoles(value = AuthConstant.ADMINISTRATOR)
    @DeleteMapping(value = "user/{id}", produces = CommonConstant.RESPONSE_PRODUCES)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "int", value = "用户id", required = true)
    })
    public ResponseVO deleteUserInfo (@PathVariable("id")@Validated(IdGroupValidator.class) Integer id) {

        log.info("deleteUserInfo {}", id);

        authService.deleteUserInfoById(id);

        return ResponseVO.success();

    }

    @ApiOperation("修改用户信息")
    @RequiresPermissions("user:edit")
    @PutMapping(value = "user", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO updateUserInfo (@RequestBody @Validated({UserInfoGroupValidator.class, IdGroupValidator.class}) UserInfoDTO userInfoDTO) {

        log.info("updateUserInfo {}", userInfoDTO.toString());

        userInfoDTO.setUpdatedUser(getAccount());
        authService.updateUserInfo(userInfoDTO);

        return ResponseVO.success();

    }

    @ApiOperation("获取用户列表")
    @RequiresPermissions("user:view")
    @GetMapping(value = "user", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO<PageVO<UserInfoVO>> findUserInfos (PageDTO pageDTO) {

        log.info("findUserInfos {}", pageDTO.toString());

        PageVO<UserInfoVO> pageVO = authService.findUserInfos(pageDTO);

        return ResponseVO.success(pageVO);

    }

    @ApiOperation("查询用户")
    @RequiresPermissions("user:view")
    @GetMapping(value = "user/{id}", produces = CommonConstant.RESPONSE_PRODUCES)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "int", value = "用户id", required = true)
    })
    public ResponseVO<UserInfoVO> findUserInfo (@PathVariable("id")@Validated(IdGroupValidator.class) Integer id) {

        log.info("findUserInfo {}", id);

        UserInfoVO userInfoVO = authService.findUserByUserId(id);

        return ResponseVO.success(userInfoVO);

    }

    @ApiOperation("判断用户是否存在")
    @RequiresRoles(value = AuthConstant.ADMINISTRATOR)
    @GetMapping(value = "user/estimate/{account}", produces = CommonConstant.RESPONSE_PRODUCES)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "account", dataType = "String", value = "用户账号", required = true)
    })
    public ResponseVO<Boolean> estimateUserInfo (@PathVariable("account") String account) {

        log.info("estimateUserInfo {}", account);

        Boolean aBoolean = authService.existsUserInfoByAccount(account);

        return ResponseVO.success(aBoolean);

    }

    @ApiOperation("修改用户密码")
    @RequiresPermissions("user:editPwd")
    @PatchMapping(value = "user/pwd", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO updatePwd (@RequestBody @Validated(UserInfoGroupValidator.class) PwdDTO pwdDTO) {

        log.info("updatePwd {}", pwdDTO.toString());

        authService.modifyPwd(pwdDTO);

        return ResponseVO.success();

    }

    @ApiOperation("校验用户密码")
    @RequiresPermissions("user:editPwd")
    @PostMapping(value = "user/pwd", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO<PwdVO> verifyPwd (@RequestBody @Validated(UserInfoGroupValidator.class) PwdDTO pwdDTO) {

        log.info("verifyPwd {}", pwdDTO.toString());

        PwdVO pwdVO = authService.verifyPwd(pwdDTO);

        return ResponseVO.success(pwdVO);

    }

    @ApiOperation("重置用户密码")
    @RequiresRoles(AuthConstant.ADMINISTRATOR)
    @PatchMapping(value = "user/{account}", produces = CommonConstant.RESPONSE_PRODUCES)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "account", dataType = "String", value = "用户账号", required = true)
    })
    public ResponseVO<String> resetPwd (@PathVariable("account") String account) {

        log.info("resetPwd {}", account);

        String pwd = authService.resetPwd(account);

        return ResponseVO.success(pwd);

    }

    @ApiOperation("获取权限列表")
    @GetMapping(value = "permissions", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO<PageVO<PermissionVO>> findPermissions (PageDTO pageDTO) {

        log.info("findPermissions {}", pageDTO);

        return ResponseVO.success(authService.findPermissions(pageDTO));

    }

}
