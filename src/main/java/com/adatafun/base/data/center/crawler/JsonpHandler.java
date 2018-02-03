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

/**
 * Created by tiecheng on 2018/1/20.
 */
public class JsonpHandler extends BaseHander {

    @Override
    String getUrl(Date depDate, String flightNo) throws ParseException {
        return null;
    }

    @Override
    String getUrl(String depDate, String flightNo) throws ParseException {
        return null;
    }

    @Override
    String getUrl(Date depDate, String depCode, String arrCode) throws ParseException {
        return null;
    }

    @Override
    String getUrl(String depDate, String depCode, String arrCode) throws ParseException {
        return null;
    }

    @Override
    String getDetailUrl(Date depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        return null;
    }

    @Override
    String getDetailUrl(String depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        return null;
    }

    @Override
    public String flightInfo(Date depDate, String flightNo) {
        List<FlightPO> flightPOList;
        try {
            Document doc = Jsoup.connect(getUrl(depDate, flightNo)).get();
            Elements detailMain = doc.getElementsByClass("detail_main");
            for (Element element : detailMain) {
                System.out.println(element.getElementsByClass("reg").get(0).html());
                Elements spans = element.getElementsByTag("span");
                for (Element span : spans) {
                    System.out.println(span.html());
                }
                Elements flyMians = element.getElementsByClass("fly_mian");
                for (Element flyMian : flyMians) {
                    Elements titles = flyMian.getElementsByAttribute("title");
                    for (Element title : titles) {
                        System.out.println(title.attr("title"));
                    }
                    Elements ps = flyMian.getElementsByTag("p");
                    for (Element p : ps) {
                        System.out.println(p.html());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String flightInfo(Date depDate, String depCode, String arrCode) {
        List<FlightPO> flightPOList = new ArrayList<>();
        FlightPO flightPO;
        try {
            Document doc = Jsoup.connect(getUrl(depDate, depCode, arrCode)).get();
            Elements tit = doc.getElementsByClass("tit");
            Element list = doc.getElementById("list");
            Elements lis = list.getElementsByTag("li");
            for (Element li : lis) {
                flightPO = new FlightPO();
                Elements as = li.getElementsByTag("a");
                for (Element a : as) {
                    System.out.println(a.html());
                }
                Elements spans = li.getElementsByTag("span");
                for (Element span : spans) {

                }
                flightPO.setAirlineChineseName(as.get(0).html());
                flightPO.setFlightNo(as.get(1).html());

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String flightInfo(Date depDate, String flightNo, String depCode, String arrCode) {
        List<FlightPO> flightPOList = new ArrayList<>();
        FlightPO flightPO;
        try {
            Document doc = Jsoup.connect(getDetailUrl(depDate, flightNo, depCode, arrCode)).get();
            Elements detailMain = doc.getElementsByClass("detail_main");
            for (Element element : detailMain) {
                flightPO = new FlightPO();
                String flightState = element.getElementsByClass("reg").get(0).html();
                flightPO.setFlightState(flightState);

                Elements spans = element.getElementsByTag("span");
                String baseMsg = spans.get(0).getElementsByTag("b").get(0).html().trim();
                String[] split = baseMsg.split(" ");
                flightPO.setAirlineChineseName(split[0]);
                flightPO.setFlightNo(split[1]);

                flightPO.setDistance(spans.get(1).html().trim().substring(2, 6));
                flightPO.setDepCity(spans.get(3).html().trim());
                flightPO.setArrCity(spans.get(4).html().trim());

                flightPO.setFlightType(spans.get(6).html().trim());
//                for (Element span : spans) {
//                    span.getElementsByTag("b")
//                    System.out.println(span.html());
//                }

                System.out.println(spans.get(10).html().trim());
                System.out.println(spans.get(11).html().trim());
                Elements flyMians = element.getElementsByClass("fly_mian");
                for (Element flyMian : flyMians) {
                    Elements titles = flyMian.getElementsByAttribute("title");
                    for (Element title : titles) {
                        if (title.attr("title").length() == 4) {
                            flightPO.setDepAirport(title.attr("title"));
                        } else {
                            flightPO.setDepAirport(title.attr("title").substring(0, 4));
                            flightPO.setDepTerminal(title.attr("title").substring(4));
                        }
                    }
                    Elements ps = flyMian.getElementsByTag("p");
                    for (int i = 0; i < ps.size() ; i++) {
                        System.out.println(ps.get(i).html());
                    }
                    if (flightState.equals("到达")) {
                        flightPO.setDepActualDate(verifyTime(depDate, ps.get(4).html()));
                    } else {
                        flightPO.setDepEstimatedDate(verifyTime(depDate, ps.get(4).html()));
                    }
//                    for (Element p : ps) {
//                        System.out.println(p.html());
//                    }
                    System.out.println(ps.get(7).attr("src"));
                    System.out.println(ps.get(9).attr("src"));
                }
                flightPOList.add(flightPO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(flightPOList);
    }

    public static void main(String[] args) throws ParseException {
        new JsonpHandler().flightInfo(DateUtils.stringToDate("20180120", "yyyyMMdd"), "MU2474", "NGB", "LHW");
        String s = "http://www.variflight.com/flight/detail/productImg&s=RlVLZ1VJeXQxMWF1TGZlbUtpUk5Wa0RYWFlVPQ==&w=50&h=28&fontSize=13&fontColor=2f3032&background=ffffff?AE71649A58c77=";
    }

}