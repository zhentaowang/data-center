package com.adatafun.base.data.center.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by tiecheng on 2018/1/4.
 */
@Component
public class RedisConf {

    public static String ADDR;

    public static int CAN_REDIS_TIME;

    public static int OTHER_REDIS_TIME;

    @Value("${redis.addr}")
    public void setADDR(String addr) {
        ADDR = addr;
    }

    @Value("${redis.time.can}")
    public void setCanRedisTime(int canRedisTime) {
        CAN_REDIS_TIME = canRedisTime;
    }

    @Value("${redis.time.other}")
    public void setOtherRedisTime(int otherRedisTime) {
        OTHER_REDIS_TIME = otherRedisTime;
    }

}
