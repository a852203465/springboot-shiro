package com.unionman.shiro.common.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @description: 重写Shiro缓存管理器
 * @author Rong.Jia
 * @date 2019/04/17 10:17:41
 */
public class CustomCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<K,V>();
    }

}
