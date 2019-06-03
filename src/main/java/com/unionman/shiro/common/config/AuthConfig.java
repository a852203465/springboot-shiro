package com.unionman.shiro.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 映射配置文件权限 实体类
 * @author: Rong.Jia
 * @date: 2019/04/17 10:02
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

    private String encryptAESKey;

    private String encryptJWTKey;

    private String accessTokenExpireTime;

    private String refreshTokenExpireTime;

    private String shiroCacheExpireTime;

    private String encrypt;

}
