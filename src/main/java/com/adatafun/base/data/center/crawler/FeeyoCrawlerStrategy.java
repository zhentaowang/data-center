package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.po.FlightPO;
import com.adatafun.base.data.center.util.DateUtils;
import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tiecheng on 2018/2/1.
 */
public class FeeyoCrawlerStrategy implements CrawlerStrategy {

    private static final String CODE = "AE71649A58c77";

    @Override
    public String getUrl(Date depDate, String flightNo) throws ParseException {
        StringBuilder url = new StringBuilder();
        url.append("http://www.variflight.com/flight/fnum/").
                append(flightNo).
                append(".html?").
                append(CODE).
                append("&fdate=").
                append(DateUtils.dateToString(depDate, "yyyyMMdd"));
        return url.toString();
    }

    @Override
    public String getUrl(String depDate, String flightNo) throws ParseException {
        StringBuilder url = new StringBuilder();
        url.append("http://www.variflight.com/flight/fnum/").
                append(flightNo).
                append(".html?").
                append(CODE).
                append("&fdate=").
                append(depDate);
        return url.toString();
    }

    @Override
    public String getUrl(Date depDate, String depCode, String arrCode) throws ParseException {
        StringBuilder url = new StringBuilder();
        url.append("http://www.variflight.com/flight/").
                append(depCode).
                append("-").
                append(arrCode).
                append(".html?").
                append(CODE).
                append("&fdate=").
                append(DateUtils.dateToString(depDate, "yyyyMMdd"));
        return url.toString();
    }

    @Override
    public String getUrl(String depDate, String depCode, String arrCode) throws ParseException {
        StringBuilder url = new StringBuilder();
        url.append("http://www.variflight.com/flight/").
                append(depCode).
                append("-").
                append(arrCode).
                append(".html?").
                append(CODE).
                append("&fdate=").
                append(depDate);
        return url.toString();
    }

    @Override
    public String getDetailUrl(Date depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        StringBuilder url = new StringBuilder();
        url.append("http://www.variflight.com/schedule/").
                append(depCode).
                append("-").
                append(arrCode).
                append("-").
                append(flightNo).
                append(".html?").
                append(CODE).
                append("&fdate=").
                append(DateUtils.dateToString(depDate, "yyyyMMdd"));
        return url.toString();
    }

    @Override
    public String getDetailUrl(String depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        StringBuilder url = new StringBuilder();
        url.append("http://www.variflight.com/schedule/").
                append(depCode).
                append("-").
                append(arrCode).
                append("-").
                append(flightNo).
                append(".html?").
                append(CODE).
                append("&fdate=").
                append(depDate);
        return url.toString();
    }

    @Override
    public String crawler(Date depDate, String flightNo) {
        List<FlightPO> flightPOList = new ArrayList<>();
        try {
            FlightPO flightPO;
            Document doc = Jsoup.connect(getUrl(depDate, flightNo)).get();
            Elements fly_list = doc.getElementsByClass("fly_list");
            for (Element element : fly_list) {
                Elements li_com = element.getElementsByClass("li_com");
                for (Element element1 : li_com) {
                    Elements span = element1.getElementsByTag("span");
                    flightPO = new FlightPO();
                    flightPO.setAirlineChineseName(span.get(0).getElementsByTag("a").get(0).text());
                    flightPO.setFlightNo(span.get(0).getElementsByTag("a").get(1).text());
                    flightPO.setDepScheduledDate(parseDate(depDate, span.get(1).text()));
                    if (span.get(3).text().length() > 4) {
                        flightPO.setDepTerminal(span.get(3).text().substring(4));
                    }
                    flightPO.setDepAirport(span.get(3).text().substring(0, 4));
                    flightPO.setArrScheduledDate(parseDate(depDate, span.get(4).text()));
                    if (span.get(6).text().length() > 4) {
                        flightPO.setArrTerminal(span.get(6).text().substring(4));
                    }
                    flightPO.setArrAirport(span.get(6).text().substring(0, 4));
                    flightPO.setFlightState(span.get(8).text());
                    flightPOList.add(flightPO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(flightPOList);
    }

    @Override
    public String crawler(Date depDate, String depCode, String arrCode) {
        return null;
    }

}
