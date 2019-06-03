package com.unionman.shiro.controller;

import com.unionman.shiro.common.config.AuthConfig;
import com.unionman.shiro.common.constants.AuthConstant;
import com.unionman.shiro.common.constants.CommonConstant;
import com.unionman.shiro.common.dto.UserLoginDTO;
import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.CustomUnauthorizedException;
import com.unionman.shiro.common.utils.AssertUtils;
import com.unionman.shiro.common.utils.EncryptUtils;
import com.unionman.shiro.common.utils.JwtUtils;
import com.unionman.shiro.common.utils.RedisUtils;
import com.unionman.shiro.common.vo.ResponseVO;
import com.unionman.shiro.common.vo.UserInfoVO;
import com.unionman.shiro.common.vo.UserLoginVO;
import com.unionman.shiro.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录/登出管理 Controller层
 * @author Rong.Jia
 * @date 2019/04/17 16:01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/")
@Api(value = "登录/登出管理", description = "登录/登出管理 controller，对接页面")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation("登录")
    @PostMapping(value = "login", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO<UserLoginVO> login (@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse httpServletResponse) {

        log.info("login {}", userLoginDTO.toString());

        // 查询并验证用户是否存在
        UserInfoVO userInfoVO = authService.findUserByAccount(userLoginDTO.getAccount());

        if (AssertUtils.isNull(userInfoVO)) {
            throw new CustomUnauthorizedException(ResponseEnum.ACCOUNT_DOES_NOT_EXIST);
        }

        // 密码进行AES解密
        String decodeAES = EncryptUtils.decodeAES(userInfoVO.getPassword(), authConfig.getEncryptAESKey());

        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (decodeAES.equals(userLoginDTO.getAccount() + userLoginDTO.getPassword())) {

            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());

            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = jwtUtils.sign(userLoginDTO.getAccount(), currentTimeMillis);

            redisUtils.set(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token, currentTimeMillis, Integer.parseInt(authConfig.getRefreshTokenExpireTime()));

            // 清除可能存在的Shiro权限信息缓存
            if (redisUtils.hasKey(AuthConstant.PREFIX_SHIRO_CACHE + token)) {
                redisUtils.del(AuthConstant.PREFIX_SHIRO_CACHE + token);
            }

            httpServletResponse.setHeader(AuthConstant.AUTHORIZATION, token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", AuthConstant.AUTHORIZATION);

            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setAccessToken(token);
            userLoginVO.setExpiresIn(jwtUtils.getExpirationToken(token).getTime());
            userLoginVO.setUserInfo(userInfoVO);

            return ResponseVO.success(userLoginVO);

        } else {
            throw new CustomUnauthorizedException(ResponseEnum.WRONG_ACCOUNT_OR_PASSWORD);
        }

    }

    @ApiOperation("退出登录")
    @RequiresAuthentication
    @PostMapping(value = "logout", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO logout (HttpServletRequest request, HttpServletResponse response) {

        log.info("logout {}");

        String token = request.getHeader(AuthConstant.AUTHORIZATION);

        // String account = jwtUtils.getClaim(token, AuthConstant.ACCOUNT);

        if (redisUtils.hasKey(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token)) {
            redisUtils.del(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token);
        }else {
            ResponseVO.error(ResponseEnum.ACCOUNT_AUTOMATIC_LOGOUT);
        }

        response.setHeader(AuthConstant.AUTHORIZATION, null);

        return ResponseVO.success();

    }



}
