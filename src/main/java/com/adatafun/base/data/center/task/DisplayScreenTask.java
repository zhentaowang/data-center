package com.adatafun.base.data.center.task;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.crawler.CANCrawlerHandler;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by tiecheng on 2018/1/26.
 */
@Component
public class DisplayScreenTask {

    @Autowired
    private CANCrawlerHandler canCrawlerHandler;

//    @Scheduled(cron = "0 0/5 * * * *")
    public void freshDisplayScreenDep() {
        try {
            Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);
            String depKey = date + "_dep_flightDisplayScreen";
            String s = canCrawlerHandler.parseAllFromCANDep();
            RedisUtils.set(depKey, s, 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 0/5 * * * *")
    public void freshDisplayScreenArr() {
        try {
            Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);
            String arrrKey = date + "_arr_flightDisplayScreen";
            String s = canCrawlerHandler.parseAllFromCANArr();
            RedisUtils.set(arrrKey, s, 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
