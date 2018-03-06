package com.adatafun.base.data.center.util;

import com.adatafun.base.data.center.conf.RedisConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * desc :
 * Created by Lin on 2017/7/12.
 */
@Component
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

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

    public static String pre_fix = "data_center:";//key 前缀

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            init();
        } catch (Exception e) {
            logger.error("初始化Redis出错:", e);
        }
    }

    /**
     * 初始化连接池
     */
    private synchronized static void init() {

        JedisPoolConfig config = new JedisPoolConfig();
//        ADDR = RedisConfig.host;
//        PORT = RedisConfig.port == 0 ? PORT: RedisConfig.port;
//
//        MAX_ACTIVE = RedisConfig.maxActive == 0 ? MAX_ACTIVE : RedisConfig.maxActive;
//        if(RedisConfig.maxIdle > 0){
//            MAX_IDLE = RedisConfig.maxIdle;
//        }
//        if(RedisConfig.maxWait > 0){
//            MAX_WAIT = RedisConfig.maxWait;
//        }
//        if(RedisConfig.timeout > 0){
//            TIMEOUT = RedisConfig.timeout;
//        }
//        TEST_ON_BORROW = RedisConfig.testOnBorrow;
//        PSW = "".equals(RedisConfig.password) ? null : RedisConfig.password;

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
    public static Jedis getJedis() {
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
     * 设置单个值
     *
     * @param key
     * @param value
     * @return
     */
    public static String set(String key, String value) {
        return set(key, value, null);
    }

    /**
     * 设置单个值，并设置超时时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间（秒）
     * @return
     */
    public static String set(String key, String value, Integer timeout) {
        String result = null;

        Jedis jedis = RedisUtils.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.set(pre_fix + key, value);
            if (null != timeout) {
                jedis.expire(pre_fix + key, timeout);
            }
        } catch (Exception e) {
            logger.error("设置单个值出错", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 设置单个值，并设置超时时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间（秒）
     * @return
     */
    public static String setNx(String key, String value, Integer timeout) {
        String result = null;

        Jedis jedis = RedisUtils.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            if (jedis.setnx(pre_fix + key, value) == 1) {
                if (null != timeout) {
                    jedis.expire(pre_fix + key, timeout);
                }
                return value;
            }
            if (jedis.setnx(pre_fix + key, value) == 0) {
                return "";
            }
        } catch (Exception e) {
            logger.error("设置单个值出错", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 获取自增值
     *
     * @param key
     * @return
     */
    public static Long incr(String key) {
        Long result = null;

        Jedis jedis = RedisUtils.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.incr(pre_fix + key);
        } catch (Exception e) {
            logger.error("自增失败", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 获取单个值
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String result = null;
        Jedis jedis = RedisUtils.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.get(pre_fix + key);
        } catch (Exception e) {
            logger.error("获取值出错", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }


    /**
     * 设置Map缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = RedisUtils.getJedis();
        try {
            if (jedis.exists(getBytesKey(pre_fix + key))) {
                jedis.del(pre_fix + key);
            }
            Map<byte[], byte[]> map = new HashMap<>();
            for (Map.Entry<String, Object> e : value.entrySet()) {
                map.put(getBytesKey(e.getKey()), ObjectUtils.serialize(e.getValue()));
            }
            result = jedis.hmset(getBytesKey(pre_fix + key), map);
            if (cacheSeconds != 0) {
                jedis.expire(pre_fix + key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("设置map出错", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 获取Map缓存
     *
     * @param key 键
     * @return 值
     */
    public static Map<String, Object> getObjectMap(String key) {
        Map<String, Object> result = null;
        Jedis jedis = RedisUtils.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            if (jedis.exists(getBytesKey(pre_fix + key))) {
                result = new HashMap<>();
                Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(pre_fix + key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
                    result.put(new String(e.getKey(), "UTF-8"), ObjectUtils.unserialize(e.getValue()));
                }
            }
        } catch (Exception e) {
            logger.error("获取map出错", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 删除redis中数据
     *
     * @param key
     * @return
     */
    public static boolean del(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = RedisUtils.getJedis();
        if (null == jedis) {
            return Boolean.FALSE;
        }
        try {
            jedis.del(pre_fix + key);
        } catch (Exception e) {
            logger.error("删除redis数据出错:", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }


    /**
     * 追加
     *
     * @param key
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Long append(String key, String value) {
        Long result = Long.valueOf(0);
        Jedis jedis = RedisUtils.getJedis();
        if (null == jedis) {
            return result;
        }
        try {
            result = jedis.append(pre_fix + key, value);
        } catch (Exception e) {
            logger.error("追加redis数据出错:", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 检测是否存在
     *
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Boolean exists(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = RedisUtils.getJedis();
        if (null == jedis) {
            return result;
        }
        try {
            result = jedis.exists(pre_fix + key);
        } catch (Exception e) {
            logger.error("检查是否存在出错：，" + e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    /**
     * 设置key的过期时间
     *
     * @param key
     * @param timeout
     * @return
     */
    public static Long expire(String key, Integer timeout) {
        Long result = null;
        Jedis jedis = RedisUtils.getJedis();
        if (null == jedis) {
            return result;
        }
        try {
            result = jedis.expire(pre_fix + key, timeout);
        } catch (Exception e) {
            logger.error("设置过期时间出错：", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }


    /**
     * 回收jedis
     *
     * @param jedis
     */
    public static void returnJedis(Jedis jedis) {
        if (null != jedis) {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.error("回收jedis出错", e);
            }
        }
    }

    /**
     * 获取byte[]类型Key
     *
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            byte[] result = null;
            try {
                result = ((String) object).getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            return result;
        } else {
            return ObjectUtils.serialize(object);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        RedisUtils.set("hello", Math.random() + "");
        System.out.println(RedisUtils.get("hello"));
        Thread.sleep(5000L);


        System.out.println("关闭redis");
    }

}
