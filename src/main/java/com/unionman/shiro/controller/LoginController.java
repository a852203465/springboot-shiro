package com.unionman.shiro.controller;

import com.unionman.shiro.config.AuthConfig;
import com.unionman.shiro.constants.AuthConstant;
import com.unionman.shiro.constants.CommonConstant;
import com.unionman.shiro.constants.NumberConstant;
import com.unionman.shiro.dto.UserLoginDTO;
import com.unionman.shiro.enums.ResponseEnum;
import com.unionman.shiro.exception.CustomUnauthorizedException;
import com.unionman.shiro.utils.AssertUtils;
import com.unionman.shiro.utils.EncryptUtils;
import com.unionman.shiro.utils.JwtUtils;
import com.unionman.shiro.utils.RedisUtils;
import com.unionman.shiro.vo.ResponseVO;
import com.unionman.shiro.vo.UserInfoVO;
import com.unionman.shiro.vo.UserLoginVO;
import com.unionman.shiro.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.unionman.shiro.constants.AuthConstant.*;

/**
 * @description: 登录/登出管理 Controller层
 * @author Rong.Jia
 * @date 2019/04/17 16:01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/")
@Api(value = "登录/登出管理", tags = "登录/登出管理 controller，对接页面")
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
    public ResponseVO<UserInfoVO> login (@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse httpServletResponse) {

        log.info("login {}", userLoginDTO.toString());

        // 查询并验证用户是否存在
        UserInfoVO userInfoVO = authService.findUserByAccount(userLoginDTO.getAccount());

        // 校验用户
        checkUser(userInfoVO);

        // 密码进行AES解密
        String key = EncryptUtils.decodeAES(userInfoVO.getPassword(), authConfig.getEncryptAESKey());

        //  登录次数限制
        retryLimit(userLoginDTO.getAccount(), userLoginDTO.getPassword(), userInfoVO);

        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());

        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = jwtUtils.sign(userLoginDTO.getAccount(), currentTimeMillis);

        redisUtils.set(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token, currentTimeMillis, Integer.parseInt(authConfig.getRefreshTokenExpireTime()));

        httpServletResponse.setHeader(AuthConstant.AUTHORIZATION, token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", AuthConstant.AUTHORIZATION);

        return ResponseVO.success(userInfoVO);

    }

    @ApiOperation("退出登录")
    @RequiresAuthentication
    @PostMapping(value = "logout", produces = CommonConstant.RESPONSE_PRODUCES)
    public ResponseVO logout (HttpServletRequest request, HttpServletResponse response) {

        log.info("logout {}", System.currentTimeMillis());

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

    /**
     * @description: 校验用户
     * @param userInfoVO 用户信息
     * @date 2019/11/02 13:55:33
     * @author Rong.Jia
     */
    private void checkUser(UserInfoVO userInfoVO) {

        if (AssertUtils.isNull(userInfoVO)) {

            log.error("checkUser {} ", "The account does not exist");
            throw new CustomUnauthorizedException(ResponseEnum.ACCOUNT_DOES_NOT_EXIST);
        }else {

            if (AssertUtils.isNull(userInfoVO.getAccount())) {

                log.error("checkUser {} ", "The account does not exist");
                throw new CustomUnauthorizedException(ResponseEnum.ACCOUNT_DOES_NOT_EXIST);
            }
        }
    }

    /**
     * @description: 登录次数限制
     * @param account 登录账号
     * @param password 登录密码
     * @param userInfoVO 账号信息
     * @date 2019/11/02 13:55:33
     * @author Rong.Jia
     */
    private void retryLimit(String account, String password, UserInfoVO userInfoVO) {

        String shiroLoginCount = PREFIX_SHIRO_LOGIN_COUNT + account;
        String shiroIsLock = PREFIX_SHIRO_IS_LOCK + account;

        ValueOperations<String, Object> opsForValue = redisUtils.getRedisTemplate().opsForValue();

        // 密码进行AES解密
        String decodeAes = EncryptUtils.decodeAES(userInfoVO.getPassword(), authConfig.getEncryptAESKey());

        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (!StringUtils.equals(decodeAes, account + password)) {

            log.error("Wrong account or password");

            //访问一次，计数一次
            if (AssertUtils.isEquals(USER_LOCK_UN.get(NumberConstant.ZERO), opsForValue.get(shiroIsLock))) {

                log.error("验证未通过,错误次数大于5次,账户已锁定  account：{}", account);

                throw new ExcessiveAttemptsException(ResponseEnum.USER_NAME_OR_PASSWORD_ERRORS_GREATER_THAN_5_TIMES.getMessage());

            }

            opsForValue.increment(shiroLoginCount, NumberConstant.ONE);

            //计数大于5时，设置用户被锁定一小时
            if(Integer.parseInt(String.valueOf(opsForValue.get(shiroLoginCount))) >= NumberConstant.FIVE){

                opsForValue.set(shiroIsLock, USER_LOCK_UN.get(NumberConstant.ZERO));
                redisUtils.getRedisTemplate().expire(shiroIsLock, NumberConstant.ONE.longValue(), TimeUnit.HOURS);

            }

            throw new CustomUnauthorizedException(ResponseEnum.WRONG_ACCOUNT_OR_PASSWORD);

        }else {

            //清空登录计数
            opsForValue.set(shiroLoginCount, NumberConstant.ZERO);

            //设置未锁定状态
            opsForValue.set(shiroIsLock, USER_LOCK_UN.get(NumberConstant.ONE));

        }
    }




}
