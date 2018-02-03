package com.adatafun.base.data.center.crawler;

import java.util.Date;

/**
 * Created by tiecheng on 2018/2/1.
 */
public class ContextStrategy {

    private CrawlerStrategy crawlerStrategy;

    public ContextStrategy(CrawlerStrategy crawlerStrategy) {
        this.crawlerStrategy = crawlerStrategy;
    }

    public ContextStrategy() {

    }

    public String crawler(Date depDate, String flightNo) {
        return crawlerStrategy.crawler(depDate, flightNo);
    }

    public String crawler(Date depDate, String depCode, String arrCode) {
        return crawlerStrategy.crawler(depDate, depCode, arrCode);
    }

    public void setStrategy(CrawlerStrategy strategy) {
        this.crawlerStrategy = strategy;//设置策略类
    }

}
