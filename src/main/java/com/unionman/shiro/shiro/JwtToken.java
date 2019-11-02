package com.unionman.shiro.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description: JwtToken
 * @author Rong.Jia
 * @date 2019/04/17 10:18:22
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1836059398459414694L;

    /**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
