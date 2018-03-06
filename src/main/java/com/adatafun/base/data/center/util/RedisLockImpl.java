package com.adatafun.base.data.center.util;

import com.adatafun.base.data.center.conf.RedisConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Created by tiecheng on 2018/3/1.
 */
public class RedisLockImpl implements RedisDistributionLock {

    public static String ADDR = "127.0.0.1"; //地址

    private static int PORT = 6379;//端口

    private static String PSW = null;//密码

    private static int MAX_ACTIVE = 500;//最大连接数量

    private static int MAX_IDLE = 200;//最小连接数量

    private static long MAX_WAIT = 10000L;//最大的等待时间

    private static int TIMEOUT = 10000;//超时时间

    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    private static Jedis jedis = null;

    private static final Logger logger = LoggerFactory.getLogger(RedisLockImpl.class);

    private static RedisLockImpl redisLock;

    public static RedisLockImpl getInstance() {
        if (redisLock == null) {
            redisLock = new RedisLockImpl();
        }
        return redisLock;
    }

    /**
     * 初始化连接池
     */
    private synchronized void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        ADDR = StringUtils.isBlank(RedisConf.ADDR) ? ADDR : RedisConf.ADDR;

        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setMaxTotal(MAX_ACTIVE);
        jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, PSW);
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public Jedis getJedis() {
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            } else {
                init();
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            logger.error("获取Redis实例出错:", e);
        }
        return jedis;
    }

    /**
     * 回收jedis
     *
     * @param jedis
     */
    public void returnJedis(Jedis jedis) {
        if (null != jedis) {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.error("回收jedis出错", e);
            }
        }
    }

    @Override
    public String lock(String lockKey, String threadName, long acquireTimeout, long timeout) {
        logger.info("线程：{}，开始执行锁", threadName);
        String retIdentifier = null;
        Jedis jedis = RedisUtils.getJedis();
        // 随机生成一个value
        String identifier = UUID.randomUUID().toString();
        // 超时时间，上锁后超过此时间则自动释放锁
        int lockExpire = (int) (timeout / 1000);
        // 锁名，即key值
        String _lockKey = "lock:" + lockKey;
        // 获取锁的超时时间，超过这个时间则放弃获取锁
        long end = System.currentTimeMillis() + acquireTimeout;
        try {
            while (currtTimeForRedis() < end) {
                if (jedis.setnx(_lockKey, identifier) == 1) {
                    jedis.expire(_lockKey, lockExpire);
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                if (jedis.ttl(_lockKey) == -1) {
                    jedis.expire(_lockKey, lockExpire);
                }
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            returnJedis(jedis);
        }
        return retIdentifier;
    }

    @Override
    public boolean unlock(String lockKey, String identifier, String threadName) {
        // 锁名，即key值
        String _lockKey = "lock:" + lockKey;
        boolean retFlag = false;
        Jedis jedis = RedisUtils.getJedis();
        try {
            while (true) {
                // 监视lock，准备开始事务
                jedis.watch(_lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnJedis(jedis);
        }
        return retFlag;
    }

    @Override
    public long currtTimeForRedis() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                seckill(Thread.currentThread().getName());
            }).start();
        }
    }

    public static void seckill(String name) {
        RedisLockImpl instance = RedisLockImpl.getInstance();
        String query = instance.lock("query", name, 1000 * 20, 1000);
        System.out.println(query);
        System.out.println(Thread.currentThread().getName() + "获得锁");
        instance.unlock("query", name, query);
    }

}
