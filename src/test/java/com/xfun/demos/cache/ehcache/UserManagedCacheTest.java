package com.xfun.demos.cache.ehcache;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;

/**
 * Created by xfun on 11/5/16.
 */
public class UserManagedCacheTest {

    public static void main(String[] args) {
        UserManagedCache<Long, String> userManagedCache =
                UserManagedCacheBuilder.newUserManagedCacheBuilder(Long.class, String.class)
                        .build(false);
        userManagedCache.init();

        long start = System.currentTimeMillis();
        for (long key = 0; key < 10000000; key++){
            userManagedCache.put(key,"value" + key);
        }
        System.out.println(System.currentTimeMillis() - start);

        userManagedCache.close();
    }

}
