package com.unionman.shiro.shiro;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.unionman.shiro.config.AuthConfig;
import com.unionman.shiro.constants.AuthConstant;
import com.unionman.shiro.constants.CommonConstant;
import com.unionman.shiro.enums.ResponseEnum;
import com.unionman.shiro.exception.SpringbootShiroException;
import com.unionman.shiro.utils.JwtUtils;
import com.unionman.shiro.utils.RedisUtils;
import com.unionman.shiro.utils.SpringUtils;
import com.unionman.shiro.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: JWT过滤
 * @author Rong.Jia
 * @date 2019/04/17 10:18:22
 */
@Slf4j
public class JwtFilter  extends BasicHttpAuthenticationFilter {

    private JwtUtils jwtUtils;

    private RedisUtils redisUtils;

    private AuthConfig authConfig;

    JwtFilter() {
        this.redisUtils = SpringUtils.getBean(RedisUtils.class);
        this.authConfig = SpringUtils.getBean(AuthConfig.class);
        this.jwtUtils = SpringUtils.getBean(JwtUtils.class);
    }

    /**
     * 为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        // 查看当前Header中是否携带Authorization属性(Token)，有的话就进行登录认证授权
        if (this.isLoginAttempt(request, response)) {
            try {

                // 进行Shiro的登录UserRealm
                this.executeLogin(request, response);

            } catch (Exception e) {

                // 认证出现异常，传递错误信息msg
                String msg = e.getMessage();

                // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
                Throwable throwable = e.getCause();

                if (throwable instanceof SignatureVerificationException) {

                    // 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
                    msg = ResponseEnum.ANONYMOUS_SUBJECT_UNAUTHORIZED.getMessage();

                } else if (throwable instanceof TokenExpiredException) {

                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = ResponseEnum.AUTHORIZATION_EXPIRES.getMessage();
                    }

                } else {

                    if (throwable != null) {

                        // 获取应用异常msg
                        msg = throwable.getMessage();
                    }

                }

                // 直接返回Response信息
                this.response401(response, msg);

                return false;

            }

        } else {

            // 没有携带Token
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

            // 获取当前请求类型
            String httpMethod = httpServletRequest.getMethod();

            // 获取当前请求URI
            String requestURI = httpServletRequest.getRequestURI();

            // mustLoginFlag = true 开启任何请求必须登录才可访问
            Boolean mustLoginFlag = false;

            if (mustLoginFlag) {

                this.response401(response, ResponseEnum.NOT_LOGGED_IN.getMessage());

                return false;
            }
        }

        return true;
    }

    /**
     * 这里我们详细说明下为什么重写
     * 可以对比父类方法，只是将executeLogin方法调用去除了
     * 如果没有去除将会循环调用doGetAuthenticationInfo方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        this.sendChallenge(request, response);
        return false;
    }

    /**
     * 检测Header里面是否包含Authorization字段，有就进行Token登录认证授权
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);

        // 判断Redis中RefreshToken是否存在
        if (redisUtils.hasKey(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token)) {

            //Redis中RefreshToken还存在，获取RefreshToken的时间戳并 刷新过期时间
            String currentTimeMillisRedis = redisUtils.get(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token).toString();
            redisUtils.set(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token, currentTimeMillisRedis, Integer.parseInt(authConfig.getRefreshTokenExpireTime()));
        }

        return token != null;
    }

    /**
     * 进行AccessToken登录认证授权
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        JwtToken token = new JwtToken(this.getAuthzHeader(request));

        // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
        this.getSubject(request, response).login(token);

        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));

        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);

    }

    /**
     * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {

        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);

        // 获取当前Token的帐号信息
        String account = jwtUtils.getClaim(token, AuthConstant.ACCOUNT);

        // 判断Redis中RefreshToken是否存在
        if (redisUtils.hasKey(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token)) {

            // Redis中RefreshToken还存在，获取RefreshToken的时间戳
            String currentTimeMillisRedis = redisUtils.get(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token).toString();

            // 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
            if (StringUtils.equals(jwtUtils.getClaim(token, AuthConstant.CURRENT_TIME_MILLIS), currentTimeMillisRedis)) {

                // 获取当前最新时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());

                String refreshTokenExpireTime = authConfig.getRefreshTokenExpireTime();

                // 删除原有 RefreshToken
                redisUtils.del(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token);

                // 刷新AccessToken，设置时间戳为当前最新时间戳
                token = jwtUtils.sign(account, currentTimeMillis);

                // 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
                redisUtils.set(AuthConstant.PREFIX_SHIRO_REFRESH_TOKEN + token, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));

                // 将新刷新的AccessToken再次进行Shiro的登录
                JwtToken jwtToken = new JwtToken(token);

                // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
                this.getSubject(request, response).login(jwtToken);

                // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setHeader(AuthConstant.AUTHORIZATION, token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", AuthConstant.AUTHORIZATION);

                return true;
            }
        }
        return false;
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletResponse response, String msg) {

        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        httpServletResponse.setCharacterEncoding(CommonConstant.PROJECT_ENCODING_UTF8);

        httpServletResponse.setContentType(CommonConstant.CONTENT_TYPE_UTF_8);

        try (PrintWriter out = httpServletResponse.getWriter()) {

            String data = JSON.toJSONString(ResponseVO.error(ResponseEnum.UNAUTHORIZED, msg));
            out.append(data);

        } catch (IOException e) {

            throw new SpringbootShiroException(ResponseEnum.SYSTEM_ERROR);

        }
    }


}
