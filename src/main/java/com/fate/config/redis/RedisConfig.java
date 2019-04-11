package com.fate.config.redis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author oyg
 * @Date 2018/10/13/12:13
 */
//@Configuration
public class RedisConfig {

    private static final Log log = LogFactory.getLog(RedisConfig.class);

    @Value("${redisPool.hort}")
    private String host;
    @Value("${redisPool.port}")
    private int port;

    @Value("${redisPool.maxTotal}")
    private int maxTotal = 20;

    @Value("${redisPool.maxIdle}")
    private int maxIdle = 10;

    @Value("${redisPool.minIdle}")
    private int minIdle = 5;

    @Value("${redisPool.timeout}")
    private int timeout;

    private static boolean testOnBorrow = true;

    private static boolean testOnReturn = false;

    @Bean(name = "jedisPool")
    public JedisPool getJedisPool(){
        log.info("初始化JedisPool");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);
        return new JedisPool(config, host,port,timeout);
    }

    @Bean(name = "jedis")
    public Jedis getJedis(@Qualifier(value = "jedisPool")JedisPool jedisPool){
        return jedisPool.getResource();
    }
}
