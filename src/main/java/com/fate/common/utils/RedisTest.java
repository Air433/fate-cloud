package com.fate.common.utils;

import com.fate.modules.sys.service.SysUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author oyg
 * @Date 2018/10/1/11:38
 */
public class RedisTest {

    @Autowired
    private SysUserService sysUserService;

    private static JedisPool pool;

    private static int maxTotal = 20;

    private static int maxIdle = 10;

    private static int minIdle = 5;

    private static boolean testOnBorrow = true;

    private static boolean testOnReturn = false;

    static {
        initPool();
    }

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, "127.0.0.1",6379,5000);
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void close(Jedis jedis){
        jedis.close();
    }

    @Test
    public void t1(){
        String UUID = java.util.UUID.randomUUID().toString();
        Jedis jedis = getJedis();
        boolean out = RedisTool.tryGetDistributedLock(jedis, "OUT", UUID, 5000);
        System.err.println(out);
        boolean releaseDistributedLock = RedisTool.releaseDistributedLock(jedis, "OUT", UUID);
        System.err.println(releaseDistributedLock);
    }
    @Test
    public void t2() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                String UUID = java.util.UUID.randomUUID().toString();
                Jedis jedis = getJedis();
                System.err.println("线程："+Thread.currentThread().getName()+"尝试获取锁");
                boolean out = RedisTool.tryGetDistributedLock(jedis, "OUT", UUID, 10000);
                while (out==false){
                    out = RedisTool.tryGetDistributedLock(jedis, "OUT", UUID, 10000);
                }
                System.err.println("线程："+Thread.currentThread().getName()+"得到锁");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("线程："+Thread.currentThread().getName()+"释放锁");
                boolean releaseDistributedLock = RedisTool.releaseDistributedLock(jedis, "OUT", UUID);

            }).start();
        }
        Thread.sleep(30000);
    }

    @Test
    public void t3() throws Exception {
        sysUserService.testRedis();
    }
}
