package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.util.DateUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by tiecheng on 2018/2/1.
 */
public class CrawlerStrategyTest {

    @Test
    public void crawler() throws Exception {
        for (int i = 0; i < 10; i++) {
            Date depDate = DateUtils.stringToDate("2018-02-01", Dictionary.DATE_FORMAT);
            ContextStrategy strategy = new ContextStrategy(new UmetripCrawlerStrategy());
            strategy.setStrategy(new FeeyoCrawlerStrategy());
            String result = strategy.crawler(depDate, "Mu2474");
            System.out.println(result);
        }
    }

    @Test
    public void crawler1() throws Exception {
        for (int i = 0; i < 20; i++) {
            Date depDate = DateUtils.stringToDate("2018-02-01", Dictionary.DATE_FORMAT);
            ContextStrategy strategy = new ContextStrategy(new UmetripCrawlerStrategy());
            String result = strategy.crawler(depDate, "HGH", "CAN");
            System.out.println(result);
        }
    }

}