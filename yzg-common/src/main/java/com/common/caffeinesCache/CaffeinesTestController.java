package com.common.caffeinesCache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine缓存测试
 * @author yangzhiguo
 */
@RestController
@RequestMapping("/caffeines")
public class CaffeinesTestController
{
    private final Log log = LogFactory.getLog(CaffeinesTestController.class);

    private Cache<String,String> cache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)  // 设置数据的失效时间(时间,时间类型)
            .maximumSize(5000)                       // 设置最大容量
            .build();

    @RequestMapping(value = "test")
    public String test(String key){

        String value = cache.getIfPresent(key);
        if (!StringUtils.isEmpty(value))
        {
            log.info("缓存中有指定key的数据,而且在有效时间内;");
        } else {
          log.info("缓存中没有指定key的数据,存入指定key的数据;");
            cache.put(key,"data");
        }

        return value;
    }
}
