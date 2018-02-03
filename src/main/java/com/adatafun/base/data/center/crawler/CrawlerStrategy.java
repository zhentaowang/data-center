package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.util.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @date: 2018/2/1 上午11:41
 * @author: ironc
 * @version: 1.0
 */
public interface CrawlerStrategy {

    String getUrl(Date depDate, String flightNo) throws ParseException;

    String getUrl(String depDate, String flightNo) throws ParseException;

    String getUrl(Date depDate, String depCode, String arrCode) throws ParseException;

    String getUrl(String depDate, String depCode, String arrCode) throws ParseException;

    String getDetailUrl(Date depDate, String flightNo, String depCode, String arrCode) throws ParseException;

    String getDetailUrl(String depDate, String flightNo, String depCode, String arrCode) throws ParseException;

    String crawler(Date depDate, String flightNo);

    String crawler(Date depDate, String depCode, String arrCode);

    /**
     * 校验时间 去除"--"
     *
     * @param depDate
     * @param actualDate
     * @return
     */
    default Date verifyTime(Date depDate, String actualDate) {
        if ("--".equals(actualDate) || "".equals(actualDate)) {
            return null;
        } else {
            return DateUtils.addMinute(depDate, millis(actualDate));
        }
    }

    /**
     * 转换时间
     * 08:25 转成分钟数
     *
     * @param actualDate
     * @return
     */
    default int millis(String actualDate) {
        String[] split = actualDate.split(":");
        return Integer.valueOf(split[0]) * 60 + Integer.valueOf(split[1]);
    }

    /**
     * 解析日期
     *
     * @param depDate
     * @param time
     * @return
     */
    default Date parseDate(Date depDate, String time) {
        return DateUtils.addMinute(depDate, millis(time.trim()));
    }

}
