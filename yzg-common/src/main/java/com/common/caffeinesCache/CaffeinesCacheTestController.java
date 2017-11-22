package com.common.caffeinesCache;


import com.common.annotations.TestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring cache集成Caffeines(JDK8高效缓存)测试
 * @author yangzhiguo
 */
@RestController
@RequestMapping("/CaffeinesCacheTest")
public class CaffeinesCacheTestController
{
    private final Log log = LogFactory.getLog(TestController.class);

    /**
     * 测试:
     *  如果缓存中有指定key的数据,则不执行该方法,否则通过指定的缓存&key得到数据
     *
     *  value:缓存名称
     *  key:指定缓存中数据的key,如果不指定key,默认用方法的形参作为key
     */
    @RequestMapping(value = "points")
    @Cacheable(value = "cacheName",key = "#id")
    public String points(String id){
        System.out.println(id);
        String tmp = "数据";
        return tmp;
    }
}
