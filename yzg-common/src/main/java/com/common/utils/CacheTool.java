package com.common.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 获取缓存
 * YZG on 2017/5/15.
 */
public class CacheTool {

    public static Cache<String,String> getCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(5000)
                .build();
    }
}
