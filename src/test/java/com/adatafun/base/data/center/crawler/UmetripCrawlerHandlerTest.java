package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.util.DateUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by tiecheng on 2018/2/1.
 */
public class UmetripCrawlerHandlerTest {
    @Test
    public void flightInfo() throws Exception {
        Date date = DateUtils.stringToDate("2018-02-01", Dictionary.DATE_FORMAT);
        String s = new UmetripCrawlerHandler().flightInfo(date, "MU2474");
        System.out.println(s);
    }

    @Test
    public void flightInfo1() throws Exception {
    }

    @Test
    public void flightInfo2() throws Exception {
    }

}