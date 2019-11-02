package com.unionman.shiro.controller;

import com.unionman.shiro.constants.AuthConstant;
import com.unionman.shiro.utils.AssertUtils;
import com.unionman.shiro.utils.JwtUtils;
import com.unionman.shiro.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 抽象 Controller层
 * @author Rong.Jia
 * @date 2019/04/16 10:58
 */
public abstract class AbstractController {

    @Autowired
    private JwtUtils jwtUtils;

    protected String getAccount() {

        //获取用户请求
        HttpServletRequest request = ServletUtils.getHttpRequest();

        //获取用户名
        String authorization = request.getHeader(AuthConstant.AUTHORIZATION);

        String account;

        if (AssertUtils.isNotNull(authorization)) {

            account = jwtUtils.getClaim(authorization, AuthConstant.ACCOUNT);

        } else {
            account = AuthConstant.ADMINISTRATOR;
        }

        return account;
    }


}
