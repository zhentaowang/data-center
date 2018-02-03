package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.po.FlightPO;
import com.adatafun.base.data.center.util.DateUtils;
import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tiecheng on 2018/2/1.
 */
public class UmetripCrawlerStrategy implements CrawlerStrategy {

    @Override
    public String getUrl(Date depDate, String flightNo) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("http://www.umetrip.com/mskyweb/fs/fc.do?flightNo=").
                append(flightNo).
                append("&date=").
                append(DateUtils.dateToString(depDate, "yyyy-MM-dd")).
                append("&channel=");
        return sb.toString();
    }

    @Override
    public String getUrl(String depDate, String flightNo) throws ParseException {
        return null;
    }

    @Override
    public String getUrl(Date depDate, String depCode, String arrCode) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("http://www.umetrip.com/mskyweb/fs/fa.do?dep=").
                append(depCode).
                append("&arr=").
                append(arrCode).
                append("&date=").
                append(DateUtils.dateToString(depDate, "yyyy-MM-dd")).
                append("&channel=");
        return sb.toString();
    }

    @Override
    public String getUrl(String depDate, String depCode, String arrCode) throws ParseException {
        return null;
    }

    @Override
    public String getDetailUrl(Date depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        return null;
    }

    @Override
    public String getDetailUrl(String depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        return null;
    }

    /**
     * TODO 未完成
     *
     * @param depDate
     * @param flightNo
     * @return
     */
    @Override
    public String crawler(Date depDate, String flightNo) {
        try {
            Document doc = Jsoup.connect(getUrl(depDate, flightNo)).get();
            Elements fly_box = doc.getElementsByClass("fly_box");
            for (Element flyBox : fly_box) {
                System.out.println(flyBox.html());
            }
            Elements p_info = doc.getElementsByClass("p_info");
            for (Element element : p_info) {
                System.out.println(element.html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String crawler(Date depDate, String depCode, String arrCode) {
        List<FlightPO> flightPOList = new ArrayList<>();
        FlightPO flightPO;
        try {
            Document doc = Jsoup.connect(getUrl(depDate, depCode, arrCode)).get();
            String names = doc.html();
            int i = names.indexOf("航班搜索结果");
            names = names.substring(i - 12, i);
            String[] split = names.split("--");
            Elements scripts = doc.getElementsByAttributeValue("type", "text/javascript");
            Element element = scripts.get(12);
            Elements lists = element.getElementsByAttributeValue("type", "text/javascript");
            for (Element list : lists) {
                String pattern = "temp.push\\(\"(.*?)\"\\);";
                Matcher matcher = Pattern.compile(pattern).matcher(
                        list.html().
                                replace("\t", "").
                                replace("\r", "").
                                replace("\n", ""));
                while (matcher.find()) {
                    flightPO = new FlightPO();
                    Document rows = Jsoup.parse(matcher.group(1));
                    Elements spans = rows.getElementsByTag("span");
                    Elements as = spans.get(0).getElementsByTag("a");
                    flightPO.setFlightNo(as.get(0).html());
                    flightPO.setAirlineChineseName(as.get(1).html());
                    String flightState = spans.get(6).html().replace("\"+\"", "").trim();
                    flightPO.setFlightState(flightState);
                    flightPO.setDepAirport(split[0].replace(" ", ""));
                    flightPO.setArrAirport(split[1].replace(" ", ""));
                    flightPO.setDepAirportCode(depCode);
                    flightPO.setArrAirportCode(arrCode);
                    flightPO.setDepScheduledDate(verifyTime(depDate, spans.get(1).html().replace("\"+\"", "").trim()));
                    flightPO.setDepActualDate(verifyTime(depDate, spans.get(2).html().replace("\"+\"", "").trim()));
                    String[] terminal = spans.get(3).html().replace("\"+\"", "").trim().split("/");
                    flightPO.setDepTerminal("--".equals(terminal[0]) == true ? null : terminal[0]);
                    flightPO.setArrTerminal("--".equals(terminal[1]) == true ? null : terminal[1]);
                    flightPO.setArrScheduledDate(verifyTime(depDate, spans.get(4).html().replace("\"+\"", "").trim()));
                    flightPO.setArrActualDate(verifyTime(depDate, spans.get(5).html().replace("\"+\"", "").trim()));
                    flightPOList.add(flightPO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(flightPOList);
    }

}
