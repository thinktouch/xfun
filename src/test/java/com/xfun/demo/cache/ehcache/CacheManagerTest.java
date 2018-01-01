package com.xfun.demo.cache.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xfun on 11/4/16.
 */
public class CacheManagerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheManagerTest.class);

    public static void main(String[] args){
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("preConfigured",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10000000)))
                .build();
        cacheManager.init();

        Cache<Long, String> preConfigured =
                cacheManager.getCache("preConfigured", Long.class, String.class);

        Cache<Long, String> myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10000000)).build());


        long start = System.currentTimeMillis();
        for (long key = 0; key < 10000000; key++){
            myCache.put(key,"value" + key);
        }

//        Iterator<Cache.Entry<Long, String>> inter = myCache.iterator();
//        while(inter.hasNext()) {
//            inter.next().getKey();
//        }

        System.out.println(System.currentTimeMillis() - start);

        cacheManager.removeCache("preConfigured");

        cacheManager.close();
    }

}
