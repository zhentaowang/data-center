package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.util.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by tiecheng on 2018/1/20.
 */
public abstract class BaseHander implements BaseHandlerInterface {

    abstract String getUrl(Date depDate, String flightNo) throws ParseException;

    abstract String getUrl(String depDate, String flightNo) throws ParseException;

    abstract String getUrl(Date depDate, String depCode, String arrCode) throws ParseException;

    abstract String getUrl(String depDate, String depCode, String arrCode) throws ParseException;

    abstract String getDetailUrl(Date depDate, String flightNo, String depCode, String arrCode) throws ParseException;

    abstract String getDetailUrl(String depDate, String flightNo, String depCode, String arrCode) throws ParseException;

    String parseDom(String html) {
        html = html.replaceAll(" ", "");
        html = html.replaceAll("\r", "");
        html = html.replaceAll("\t", "");
        html = html.replaceAll("\n", "");
        return html;
    }

    /**
     * 校验时间 去除"--"
     *
     * @param depDate
     * @param actualDate
     * @return
     */
    Date verifyTime(Date depDate, String actualDate) {
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
    private int millis(String actualDate) {
        String[] split = actualDate.split(":");
        return Integer.valueOf(split[0]) * 60 + Integer.valueOf(split[1]);
    }

    abstract String flightInfo(Date depDate, String flightNo);

    abstract String flightInfo(Date depDate, String depCode, String arrCode);

    abstract String flightInfo(Date depDate, String flightNo, String depCode, String arrCode);

}
