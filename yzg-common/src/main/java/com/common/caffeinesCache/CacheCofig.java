package com.common.caffeinesCache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * spring cache集成Caffeines(JDK8高效缓存)的配置
 * @author yangzhiguo
 */
@EnableCaching
@Configuration
public class CacheCofig
{
    public static final int MAX_CAP = 50000;
    public static final int DEADLINE = 10;

    /**
     * 创建基于Caffeine的Cache Manager
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = new ArrayList<CaffeineCache>();

        boolean yzg = caches.add(new CaffeineCache("cacheName",// 指定缓存的名称
                Caffeine.newBuilder().recordStats()
                        .expireAfterWrite(DEADLINE, TimeUnit.SECONDS)
                        .maximumSize(MAX_CAP)
                        .build()));

        cacheManager.setCaches(caches);

        return cacheManager;
    }
}
