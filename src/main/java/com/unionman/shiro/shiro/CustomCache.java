package com.unionman.shiro.shiro;

import com.unionman.shiro.config.AuthConfig;
import com.unionman.shiro.constants.AuthConstant;
import com.unionman.shiro.utils.JwtUtils;
import com.unionman.shiro.utils.RedisUtils;
import com.unionman.shiro.utils.SpringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

/**
 * @description: 重写Shiro的Cache保存读取
 * @author Rong.Jia
 * @date 2018/8/30 15:49
 */
@SuppressWarnings("unchecked")
public class CustomCache<K, V>  implements Cache<K,V> {

    private RedisUtils redisUtils;

    private AuthConfig authConfig;

    private JwtUtils jwtUtils;

    public CustomCache() {
        this.redisUtils = SpringUtils.getBean(RedisUtils.class);
        this.authConfig = SpringUtils.getBean(AuthConfig.class);
        this.jwtUtils = SpringUtils.getBean(JwtUtils.class);
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key 缓存的key
     * @author Rong.Jia
     * @date 2018/9/4 18:33
     * @return java.lang.String
     */
    private String getKey(Object key) {

        //return AuthConstant.PREFIX_SHIRO_CACHE + jwtUtils.getClaim(key.toString(), AuthConstant.ACCOUNT);

        return AuthConstant.PREFIX_SHIRO_CACHE + key;
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(!redisUtils.hasKey(this.getKey(key))){
            return null;
        }
        return redisUtils.get(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {

        // 读取配置文件，获取Redis的Shiro缓存过期时间
        String shiroCacheExpireTime = authConfig.getShiroCacheExpireTime();

        // 设置Redis的Shiro缓存
        return redisUtils.set(this.getKey(key), value, Integer.parseInt(shiroCacheExpireTime));
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(!redisUtils.hasKey(this.getKey(key))){
            return null;
        }
        redisUtils.del(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        Objects.requireNonNull(redisUtils).clear();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Long size = Objects.requireNonNull(redisUtils).size();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        return Objects.requireNonNull(redisUtils).keys();
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            values.add(redisUtils.get(this.getKey(key)));
        }
        return values;
    }



}
