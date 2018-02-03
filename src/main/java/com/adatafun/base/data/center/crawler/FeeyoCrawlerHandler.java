package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.po.FlightPO;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.HttpClientUtils;
import com.adatafun.base.data.center.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tiecheng on 2018/1/19.
 */
@Component
public class FeeyoCrawlerHandler extends BaseHander {

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

    /**
     * 航班信息查询
     *
     * @param depDate
     * @param flightNo
     * @return
     */
    @Override
    public String flightInfo(Date depDate, String flightNo) {
        List<FlightPO> flightPOList = new ArrayList<>();
        try {
            FlightPO flightPO;
            Document doc = Jsoup.connect(getUrl(depDate, flightNo)).get();

            String shareFlightNo = null;

            Elements fly_list = doc.getElementsByClass("fly_list");
            for (Element element : fly_list) {
                if (element.getElementsByClass("list_share").size() > 0) {
                    shareFlightNo = element.getElementsByClass("list_share").attr("title").substring(5);
                }

                Elements li_com = element.getElementsByClass("li_com");
                for (Element element1 : li_com) {
                    Elements span = element1.getElementsByTag("span");
                    flightPO = new FlightPO();
                    flightPO.setAirlineChineseName(span.get(0).getElementsByTag("a").get(0).text());
                    flightPO.setFlightNo(span.get(0).getElementsByTag("a").get(1).text());
                    Date depSchduleDate = parseDate(depDate, span.get(1).text());
                    Date arrSchduleDate = parseDate(depDate, span.get(4).text());

                    if (depSchduleDate.after(arrSchduleDate)) {
                        arrSchduleDate = DateUtils.addDay(arrSchduleDate, 1);
                    }

                    flightPO.setDepScheduledDate(depSchduleDate);
                    if (span.get(3).text().length() > 4) {
                        flightPO.setDepTerminal(span.get(3).text().substring(4));
                    }
                    flightPO.setDepAirport(span.get(3).text().substring(0, 4));

                    flightPO.setArrScheduledDate(arrSchduleDate);
                    if (span.get(6).text().length() > 4) {
                        flightPO.setArrTerminal(span.get(6).text().substring(4));
                    }
                    if (StringUtils.isNoneBlank(shareFlightNo)) {
                        flightPO.setShareFlightNo(shareFlightNo);
                        flightPO.setIsShare(Dictionary.CONDITION_TRUE);
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
    public String flightInfo(Date depDate, String depCode, String arrCode) {
        return null;
    }

    @Override
    public String flightInfo(Date depDate, String flightNo, String depCode, String arrCode) {
        return null;
    }

    /**
     * 航班详情查询
     *
     * @param depDate
     * @param flightNo
     * @param depCode
     * @param arrCode
     * @return
     */
    public String flightInfoDetail(Date depDate, String flightNo, String depCode, String arrCode) {
        List<FlightPO> flightPOList = new ArrayList<>();
        try {
            String url = getDetailUrl(depDate, flightNo, depCode, arrCode);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = client.execute(httpGet);
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            result = parseDom(result);
            String pattern = "<divclass=\"detail_main\">(.*?)</div>(.*?)<divclass=\"flyProc\">(.*?)</ul>(.*?)<div class=\"fly_mian\">(.*?)</ul>(.*?)<div class=\"fly_mian\">(.*?)</ul>";
            pattern = "<divclass=\"detail_main\">(.*?)</span>(.*?)<divclass=\"flyProc\">(.*?)</ul>(.*?)" +
                    "<divclass=\"f_titlef_title_a\">(.*?)</ul>(.*?)<divclass=\"f_titlef_title_c\">(.*?)</ul>";
//            pattern = "<divclass=\"detail_main\">(.*?)</div>";
            Pattern r = Pattern.compile(pattern);
            Matcher matcher = r.matcher(result);
            FlightPO flightPO;
            while (matcher.find()) {
                flightPO = new FlightPO();
                String s = matcher.group();

                System.out.println(matcher.group(1));
                String pattern_1 = "\"reg\">(.*?)</div>(.*?)<b>(.*?)</b>";
                Matcher matcher_1 = Pattern.compile(pattern_1).matcher(matcher.group(1));
                if (matcher_1.find()) {
                    System.out.println(matcher_1.group(1));
                    System.out.println(matcher_1.group(3));
                }

                System.out.println(matcher.group(3));
                String pattern_2 = "<span.*?>(.*?)</span>";
                Matcher matcher_2 = Pattern.compile(pattern_2).matcher(matcher.group(3));
                while (matcher_2.find()) {
                    System.out.println(matcher_2.group());
                }

                System.out.println(matcher.group(5));
                String pattern_3 = "<spanclass.*?>(.*?)</span>.*?</em>(.*?)</h2>(.*?)<ul.*?>(.*?)";
                Matcher matcher_3 = Pattern.compile(pattern_3).matcher(matcher.group(5));
                while (matcher_3.find()) {
                    System.out.println(matcher_3.group(1));
                    System.out.println(matcher_3.group(2));
                    System.out.println(matcher_3.group(4));
                }

                System.out.println(matcher.group(7));
                String pattern_4 = "<spanclass.*?>(.*?)</span>.*?</em>(.*?)</h2>(.*?)<ul.*?>(.*?)";
                Matcher matcher_4 = Pattern.compile(pattern_4).matcher(matcher.group(7));
                while (matcher_4.find()) {
                    System.out.println(matcher_4.group(1));
                    System.out.println(matcher_4.group(2));
                    System.out.println(matcher_4.group(4));
                }
//                int i = 0;
//                if (s.contains("detail_main")) {
//                    flightPO.setFlightState(s.substring(s.indexOf("reg") + 5, s.indexOf("reg") + 9));
//                }
//                if (s.contains("flyProc") && filterSpan(s) != null) {
//                    flightPO.setDistance(parseSpan(filterSpan(s)[0]));
//                    System.out.println("飞行时间" + parseSpan(filterSpan(s)[1]));
//                }
//                if (s.contains("cir_lcurr") && filterSpan(s) != null) {
//                    flightPO.setDepAirport(parseSpan(filterSpan(s)[0]));
//                }
//                if (s.contains("p_info") && filterLi(s) != null) {
//
//                }
//                if (s.contains("f_titlef_title_a")){
//
//                }
//                if (s.contains("f_titlef_title_c")){
//
//                }
//                while (matcher_row.find()) {
//                    s = matcher_row.group();
//                    System.out.println(s);
//                    switch (i) {
//                        case 0:
//                            parseTitle = parseTitle(s);
//                            if (parseTitle.length == 2) {
//                                flightPO.setAirlineChineseName(parseTitle[0]);
//                                flightPO.setFlightNo(parseTitle[1]);
//                            }
//                            break;
//                        case 1:
//                            flightPO.setDepScheduledDate(verifyTime(depDate, parseSpan(s)));
//                            break;
//                        case 2:
//                            flightPO.setDepActualDate(verifyTime(depDate, parseSpan(s)));
//                            break;
//                        case 3:
//                            String dep = parseSpan(s);
//                            if (dep.length() > 4) {
//                                flightPO.setDepTerminal(dep.substring(4, dep.length()));
//                            }
//                            flightPO.setDepAirport(dep.substring(0, 4));
//                            break;
//                        case 4:
//                            flightPO.setArrScheduledDate(verifyTime(depDate, parseSpan(s)));
//                            break;
//                        case 5:
//                            flightPO.setArrActualDate(verifyTime(depDate, parseSpan(s)));
//                            break;
//                        case 6:
//                            String arr = parseSpan(s);
//                            if (arr.length() > 4) {
//                                flightPO.setArrTerminal(arr.substring(4, arr.length()));
//                            }
//                            flightPO.setArrAirport(arr.substring(0, 4));
//                            break;
//                        case 7:
////                        flightPO.setArrActualDate(verifyTime(depDate, parseSpan(matcher.group(18))));
//                            break;
//                        case 8:
//                            flightPO.setFlightState(parseSpan(s));
//                            break;
//                        case 9:
//                            flightPO.setDepAirportCode(parseSpanFob(s)[0]);
//                            flightPO.setArrAirportCode(parseSpanFob(s)[1]);
//                            break;
//                        default:
//                            break;
//                    }
//                    i++;
//                }
                flightPOList.add(flightPO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(flightPOList);
    }

    @Override
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
    @Override
    Date verifyTime(Date depDate, String actualDate) {
        if ("--".equals(actualDate) || "".equals(actualDate)) {
            return null;
        } else {
            return DateUtils.addMinute(depDate, millis(actualDate));
        }
    }

    /**
     * 转换时间
     * e.g. 08:25 转成分钟数
     *
     * @param actualDate
     * @return
     */
    private int millis(String actualDate) {
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
    private Date parseDate(Date depDate, String time) {
        return DateUtils.addMinute(depDate, millis(time.trim()));
    }

    /**
     * 匹配SPAN标签
     *
     * @param content
     * @return
     */
    private String[] filterSpan(String content) {
        String pattern = "<span.*?>(.*?)</span>";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        String[] result = new String[matcher.groupCount()];
        int i = 0;
        while (matcher.find()) {
            result[i] = matcher.group();
            i++;
        }
        return null;
    }

    /**
     * 匹配SPAN标签
     *
     * @param content
     * @return
     */
    private String[] filterLi(String content) {
        String pattern = "<li.*?>(.*?)</li>";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        String[] result = new String[matcher.groupCount()];
        int i = 0;
        while (matcher.find()) {
            result[i] = matcher.group();
            i++;
        }
        return null;
    }

    /**
     * 解析SPAN标签的内容
     * 获取普通参数
     *
     * @param content
     * @return
     */
    private String parseSpan(String content) {
        String pattern = ">(.*?)<";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        while (matcher.find()) {
            return matcher.group().substring(1, matcher.group().length() - 1);
        }
        return null;
    }

    private String[] parseSpanFob(String content) {
        String[] result = new String[2];
        result[0] = content.substring(content.indexOf("depCode") + 8, content.indexOf("depCode") + 10);
        result[1] = content.substring(content.indexOf("arrCode") + 8, content.indexOf("arrCode") + 10);
        return result;
    }


    /**
     * 解析TITLE标签的内容
     * 获取航班号和航空公司
     *
     * @param content
     * @return
     */
    private String[] parseTitle(String content) {
        String[] result = new String[2];
        String pattern = "\"title=\"(.*?)\">";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        int i = 0;
        while (matcher.find()) {
            result[i] = matcher.group().substring(8, matcher.group().length() - 2);
            i++;
        }
        return result;
    }

    public static void main(String[] args) throws ParseException, IOException {
        FeeyoCrawlerHandler feeyoCrawlerHandler = new FeeyoCrawlerHandler();
//        System.out.println(feeyoCrawlerHandler.flightInfo(DateUtils.stringToDate("20180119", "yyyyMMdd"), "MU2474"));
//        System.out.println(feeyoCrawlerHandler.flightInfoDetail(DateUtils.stringToDate("20180120", "yyyyMMdd"), "MU2474", "NGB", "LHW"));
//        String url = feeyoCrawlerHandler.getDetailUrl(DateUtils.stringToDate("20180120", "yyyyMMdd"), "MU2474", "NGB", "LHW");

    }

}
