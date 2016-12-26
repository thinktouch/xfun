package com.xfun.demos.cache.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static com.sun.scenario.Settings.set;

/**
 * Created by xfun on 11/23/16.
 */
public class JedisClusterTest {

    private static HostAndPort nodeInfo1 = new HostAndPort("localhost", 7379);
    private static HostAndPort nodeInfo2 = new HostAndPort("localhost", 7380);
    private static HostAndPort nodeInfo3 = new HostAndPort("localhost", 7381);
    private static HostAndPort nodeInfo4 = new HostAndPort("localhost", 7382);
    private static HostAndPort nodeInfo5 = new HostAndPort("localhost", 7383);
    private static HostAndPort nodeInfo6 = new HostAndPort("localhost", 7384);

    public static void main(String[] args){
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(nodeInfo1);
        jedisClusterNodes.add(nodeInfo2);
        jedisClusterNodes.add(nodeInfo3);
        jedisClusterNodes.add(nodeInfo4);
        jedisClusterNodes.add(nodeInfo5);
        jedisClusterNodes.add(nodeInfo6);

        JedisCluster jCluster = new JedisCluster(jedisClusterNodes);

        long begin = Calendar.getInstance().getTimeInMillis();

        int setCount = 0;
        int getCount = 100000;

        for (int i = 0; i < setCount; i++){
            jCluster.set(new StringBuilder("xfun-key").append(i).toString(), new StringBuilder("xfun-value").append(i).toString());
        }

        for (int i = 0; i < getCount; i++){
            jCluster.get(new StringBuilder("xfun-key").append(i).toString());
        }

        long elapsed = Calendar.getInstance().getTimeInMillis() - begin;

        System.out.println("------ single thread ------");
        System.out.println("set:" + setCount);
        System.out.println("get:" + getCount);
        System.out.println("cost time:" + elapsed);
        System.out.println("ops:" + (1000 * (getCount + setCount))/elapsed);

        System.out.println("------ multi thread ------");

        int threadCount = 20;
        for(int t = 0; t < threadCount; t++){
            Thread thread = new Thread(new JedisClusterTestThread(jCluster, setCount, getCount));
            thread.start();
        }
    }
}

class JedisClusterTestThread implements Runnable{
    private JedisCluster jCluster;
    private int setCount;
    private int getCount;

    public JedisClusterTestThread(JedisCluster _jCluster, int _setCount, int _getCount) {
        this.jCluster = _jCluster;
        this.setCount = _setCount;
        this.getCount = _getCount;
    }

    public void run() {
        long begin = Calendar.getInstance().getTimeInMillis();

        for (int i = 0; i < setCount; i++){
            jCluster.set(new StringBuilder("xfun-key").append(i).toString(), new StringBuilder("xfun-value").append(i).toString());
        }

        for (int i = 0; i < getCount; i++){
            jCluster.get(new StringBuilder("xfun-key").append(i).toString());
        }

        long elapsed = Calendar.getInstance().getTimeInMillis() - begin;

        System.out.println("thread-" + Thread.currentThread().getId() + " set:" + setCount);
        System.out.println("thread-" + Thread.currentThread().getId() + " get:" + getCount);
        System.out.println("thread-" + Thread.currentThread().getId() + " cost time:" + elapsed);
        System.out.println("thread-" + Thread.currentThread().getId() + " ops:" + (1000 * (getCount + setCount))/elapsed);
    }
}