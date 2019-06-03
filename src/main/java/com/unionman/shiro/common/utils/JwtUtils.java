package com.unionman.shiro.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.unionman.shiro.common.config.AuthConfig;
import com.unionman.shiro.common.constants.AuthConstant;
import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.SpringbootShiroException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @description: JWT工具类
 * @author Rong.Jia
 * @date 2019/04/17 11:15:49
 */
@Slf4j
@Component
public class JwtUtils {

    @Autowired
    private AuthConfig authConfig;

    /**
     * @description: 校验token是否正确
     * @param token Token
     * @return boolean 是否正确
     * @author Rong.Jia
     * @date 2019/04/17 11:15:49
     */
    public boolean verify(String token) {

        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, AuthConstant.ACCOUNT) + Base64ConvertUtils.decode(authConfig.getEncryptJWTKey());

            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            return true;

        } catch (UnsupportedEncodingException e) {

            log.error("UnsupportedEncodingException {}", e.getMessage());

            throw new SpringbootShiroException(ResponseEnum.ENCRYPTION_OR_DECRYPTION_FAILED);
        }
    }

    /**
     * @description: 获得Token中的信息无需secret解密也能获得
     * @param token token
     * @param claim
     * @author Wang926454
     * @author Rong.Jia
     * @date 2019/04/17 11:15:49
     * @return String
     */
    public String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();

        } catch (JWTDecodeException e) {

            log.error("JWTDecodeException {}", e.getMessage());

            throw new SpringbootShiroException(ResponseEnum.ENCRYPTION_OR_DECRYPTION_FAILED);

        }
    }

    /**
     * @description: 生成签名
     * @param account 帐号
     * @author Rong.Jia
     * @date 2019/04/17 11:15:49
     * @return java.lang.String 返回加密的Token
     */
    public String sign(String account, String currentTimeMillis) {

        try {

            // 帐号加JWT私钥加密
            String secret = account + Base64ConvertUtils.decode(authConfig.getEncryptJWTKey());

            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(authConfig.getAccessTokenExpireTime()) * 1000);

            Algorithm algorithm = Algorithm.HMAC256(secret);

            // 附带account帐号信息
            return JWT.create()
                    .withClaim(AuthConstant.ACCOUNT, account)
                    .withClaim(AuthConstant.CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);

        } catch (UnsupportedEncodingException e) {

            log.error("UnsupportedEncodingException {}", e.getMessage());

            throw new SpringbootShiroException(ResponseEnum.ENCRYPTION_OR_DECRYPTION_FAILED);
        }

    }

    /**
     * @description: 获取TOKEN失效时间
     * @param token TOKEN信息
     * @return 失效时间
     */
    public Date getExpirationToken(String token) {

        DecodedJWT jwt = JWT.decode(token);

        return jwt.getExpiresAt();

    }

    /**
     * @description:  校验TOKEN是否过期
     * @param token TOKEN信息
     * @return true/fasle 是否过期
     */
    public Boolean isTokenExpired(String token) {

        Date expiration = getExpirationToken(token);

        // 报异常
        return expiration.before(new Date());

    }


}
