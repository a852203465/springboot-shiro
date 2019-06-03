package com.unionman.shiro.common.constants;

/**
 * @descritpion: 权限管理常量类
 * @date 2019/04/16 14:50:22
 * @author Rong.Jia
 */
public class AuthConstant {

    /**
     * 管理员用户
     */
    public static final String ADMINISTRATOR = "admin";

    /**
     * 不过期
     */
    public static final long NOT_EXPIRE = -1L;

    /**
     * 默认过期时间 单位：秒
     */
    public static final long DEFAULT_EXPIRE = 3600L;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * 权限 header
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 16;

    /**
     *  默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

}
