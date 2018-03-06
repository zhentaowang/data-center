package com.adatafun.base.data.center.util;

/**
 * Redis分布式锁
 *
 * @date: 2018/3/1 下午1:58
 * @author: ironc
 * @version: 1.0
 */
public interface RedisDistributionLock {

    /**
     * 加锁成功，返回加锁时间
     *  @param lockKey
     * @param threadName
     * @param acquireTimeout
     * @param timeout @return
     */
    String lock(String lockKey, String threadName, long acquireTimeout, long timeout);

    /**
     * 解锁， 需要更新加锁时间，判断是否有权限
     *  @param lockKey
     * @param identifier
     * @param threadName
     */
    boolean unlock(String lockKey, String identifier, String threadName);

    /**
     * 多服务器集群，使用下面的方法，代替System.currentTimeMillis()，获取redis时间，避免多服务的时间不一致问题！！！
     *
     * @return
     */
    long currtTimeForRedis();

}
