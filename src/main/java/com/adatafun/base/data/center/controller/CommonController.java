package com.adatafun.base.data.center.controller;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.crawler.CANCrawlerHandler;
import com.adatafun.base.data.center.po.BlackListPO;
import com.adatafun.base.data.center.po.WhiteListPO;
import com.adatafun.base.data.center.service.WhiteAndBlackListServiceInterface;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.IpUtils;
import com.adatafun.base.data.center.util.RedisUtils;
import com.adatafun.base.data.center.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by tiecheng on 2018/1/22.
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    private final static String URL_DEP = "http://www.gbiac.net/hbxx/flightquery?isArrival=false&isAll=true&recently_20_data=true";

    private final static String URL_ARR = "http://www.gbiac.net/hbxx/flightQuery?isArrival=true&isAll=true&recently_20_data=true";

    @Autowired
    private WhiteAndBlackListServiceInterface whiteAndBlackListServiceInterface;

    @Autowired
    private CANCrawlerHandler canCrawlerHandler;

    @GetMapping(path = "ip/{type}", produces = {"application/json"})
    public String createIp(@PathVariable String type, @RequestParam String ip) {

        Date date = new Date();
        JSONObject result = new JSONObject();

        if (IpUtils.check(ip)) {
            if ("black".equals(type)) {
                BlackListPO blackListPO = new BlackListPO();
                blackListPO.setIp(ip);
                blackListPO.setCreateTime(date);
                if (whiteAndBlackListServiceInterface.createBlcakList(blackListPO) > 0) {
                    result.put("ip", ip);
                }
            }

            if ("white".equals(type)) {
                WhiteListPO whiteListPO = new WhiteListPO();
                whiteListPO.setIp(ip);
                whiteListPO.setCreateTime(date);
                if (whiteAndBlackListServiceInterface.createWhiteList(whiteListPO) > 0) {
                    result.put("ip", ip);
                }
            }
        } else {
            result.put("ip", "不合法");
        }

        return JSON.toJSONString(result);
    }

    @GetMapping(path = "fresh")
    public void fresh(){
        whiteAndBlackListServiceInterface.freshWhiteAndBlackLists();
    }

//    @GetMapping(path = "flight-display-screen/simple", produces = {"application/json"})
//    public String getFlightDisplayScreenSimple(@RequestParam("type") String type) {
//        List<Flight> result = new ArrayList<>();
//        String depKey = "dep-flight-display-screen";
//        String arrrKey = "arr-flight-display-screen";
//        String cache;
//        try {
//            type = type.trim();
//            if ("出发".equals(type)) {
//                cache = RedisUtils.get(depKey);
//                if (StringUtils.isNoneBlank(cache)) {
//                    result = JSONArray.parseArray(cache, Flight.class);
//                    return JSON.toJSONString(result);
//                }
//                result = parse(URL_DEP);
//                if (result.size() > 0) {
//                    RedisUtils.set(depKey, JSON.toJSONString(result), 60 * 5);
//                }
//            }
//            if ("到达".equals(type)) {
//                cache = RedisUtils.get(arrrKey);
//                if (StringUtils.isNoneBlank(cache)) {
//                    result = JSONArray.parseArray(cache, Flight.class);
//                    return JSON.toJSONString(result);
//                }
//                result = parse(URL_ARR);
//                if (result.size() > 0) {
//                    RedisUtils.set(depKey, JSON.toJSONString(result), 60 * 5);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return JSON.toJSONString(result);
//    }

    @GetMapping(path = "flight-display-screen/all", produces = {"application/json"})
    public String getFlightDisplayScreenAll(@RequestParam("type") String type) {
        String result = null;
        String cache;
        try {
            Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);
            String depKey = date + "_dep_flightDisplayScreen";
            String arrrKey = date + "_arr_flightDisplayScreen";
            type = type.trim();
            if ("出发".equals(type)) {
                cache = RedisUtils.get(depKey);
                if (StringUtils.isNoneBlank(cache)) {
                    result = cache;
                    return result;
                }
                result = canCrawlerHandler.parseAllFromCANDep();
                if (StringUtils.isNoneBlank(result)) {
                    RedisUtils.set(depKey, result, 60 * 60 * 24);
                }
            }
            if ("到达".equals(type)) {
                cache = RedisUtils.get(arrrKey);
                if (StringUtils.isNoneBlank(cache)) {
                    result = cache;
                    return result;
                }
                result = canCrawlerHandler.parseAllFromCANArr();
                if (StringUtils.isNoneBlank(result)) {
                    RedisUtils.set(arrrKey, result, 60 * 60 * 24);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    private List<Flight> parse(String url) throws IOException {
//        List<Flight> result = new ArrayList();
//        CloseableHttpClient httpClient = new DefaultHttpClient();
//        HttpGet get = new HttpGet(url);
//        get.setHeader("Host", "www.gbiac.net");
//        get.setHeader("Connection", "keep-alive");
//        get.setHeader("Cache-Control", "max-age=0");
//        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
//        get.setHeader("Upgrade-Insecure-Requests", "1");
//        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//                "Accept-Encoding: gzip, deflate");
//        get.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        get.setHeader("Cookie", "JSESSIONID=E6FB44B41BD4340E994ECB80449BCB65.node1; GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; Hm_lvt_e76379e9296ddf1578b19c77def4a876=1516947710; Hm_lpvt_e76379e9296ddf1578b19c77def4a876=1516947756");
//        CloseableHttpResponse response = httpClient.execute(get);
//        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
//        Document doc = Jsoup.parse(s);
//        Elements tr = doc.getElementsByTag("tr");
//        for (Element element : tr) {
//            Flight flight = new Flight();
//            Elements td = element.getElementsByTag("td");
//            if (td.size() <= 0) {
//                continue;
//            }
//            flight.setDepTime(td.get(0).text());
//            flight.setAirlineName(td.get(1).text());
//            flight.setFlightNo(td.get(2).text());
//            flight.setArrCity(td.get(3).text());
//            flight.setCheckInCounter(td.get(4).text());
//            flight.setBoardGate(td.get(5).text());
//            flight.setFlightState(td.get(6).text());
//            result.add(flight);
//        }
//        return result;
//    }
//
//    private List<Flight> parse2() throws IOException {
//        List<Flight> result = new ArrayList();
//        String url = "http://www.gbiac.net/hbxx/flightquery?p_p_id=flightindex_WAR_flightqueryportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-4&p_p_col_count=1";
//        CloseableHttpClient httpClient = new DefaultHttpClient();
//        HttpPost post = new HttpPost(url);
//        post.setHeader("Host", "www.gbiac.net");
//        post.setHeader("Origin", "http://www.gbiac.net");
//        post.setHeader("Referer", "http://www.gbiac.net/hbxx/flightquery?p_p_id=flightindex_WAR_flightqueryportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-4&p_p_col_count=1");
//        post.setHeader("Upgrade-Insecure-Requests", "1");
//        post.setHeader("Accept-Encoding", "gzip, deflate");
//        post.setHeader("Connection", "keep-alive");
//        post.setHeader("Cache-Control", "max-age=0");
//        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
//        post.setHeader("Upgrade-Insecure-Requests", "1");
//        post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//                "Accept-Encoding: gzip, deflate");
//        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        post.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        post.setHeader("Cookie", "GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; JSESSIONID=F14F7D04B682A834D007A39DAC6F4A29.node1; Hm_lvt_e76379e9296ddf1578b19c77def4a876=1516949147,1516949188,1516949211,1516954041; Hm_lpvt_e76379e9296ddf1578b19c77def4a876=1516954215");
//        Map<String, String> map = new HashMap<>();
//        map.put("flight_number_or_city", "");
//        map.put("flight_route", "4");
//        map.put("isSearch", "true");
//        map.put("flight_number", "");
//        map.put("city_code", "");
//        map.put("begin_flight_date", "2018-01-26 00:00:00");
//        map.put("end_flight_date", "2018-01-26 23:59:00");
//        map.put("isPassengerPlane", "true");
//        map.put("airline_code", "");
//        map.put("flight_date", "0");
//        map.put("flight_time", "all");
//        map.put("flight_status", "all");
//
//        ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();//用于存放表单数据.
//        //遍历map 将其中的数据转化为表单数据
//        for (Map.Entry<String, String> entry :
//                map.entrySet()) {
//            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//        }
//
//        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
//        post.setEntity(urlEncodedFormEntity);
//
//        CloseableHttpResponse response = httpClient.execute(post);
//        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
//        Document doc = Jsoup.parse(s);
//        Elements tr = doc.getElementsByTag("tr");
//        for (Element element : tr) {
//            Flight flight = new Flight();
//            Elements td = element.getElementsByTag("td");
//            if (td.size() <= 0) {
//                continue;
//            }
//            flight.setDepTime(td.get(0).text());
//            flight.setAirlineName(td.get(1).text());
//            flight.setFlightNo(td.get(2).text());
//            flight.setArrCity(td.get(3).text());
//            flight.setCheckInCounter(td.get(4).text());
//            flight.setBoardGate(td.get(5).text());
//            flight.setFlightState(td.get(6).text());
//            result.add(flight);
//        }
//        return result;
//    }
//
//    private List<Flight> parse3() throws IOException {
//        List<Flight> result = new ArrayList();
//
//        String url = "http://www.gbiac.net/hbxx/flightquery?" +
//                "p_p_id=flightindex_WAR_flightqueryportlet&" +
//                "p_p_lifecycle=0&" +
//                "p_p_state=normal&" +
//                "p_p_mode=view&" +
//                "p_p_col_id=column-4&" +
//                "p_p_col_count=1&" +
//                "_flightindex_WAR_flightqueryportlet_delta=50&" +
//                "_flightindex_WAR_flightqueryportlet_keywords=&" +
//                "_flightindex_WAR_flightqueryportlet_advancedSearch=false&" +
//                "_flightindex_WAR_flightqueryportlet_andOperator=true&" +
//                "_flightindex_WAR_flightqueryportlet_flight_number=&" +
//                "_flightindex_WAR_flightqueryportlet_city_code=&" +
//                "_flightindex_WAR_flightqueryportlet_airline_code=&" +
//                "_flightindex_WAR_flightqueryportlet_flight_route=4&" +
//                "_flightindex_WAR_flightqueryportlet_begin_flight_date=2018-01-26+00%3A00%3A00&" +
//                "_flightindex_WAR_flightqueryportlet_end_flight_date=2018-01-26+23%3A59%3A00&" +
//                "_flightindex_WAR_flightqueryportlet_flight_status=all&" +
//                "_flightindex_WAR_flightqueryportlet_isPassengerPlane=true&" +
//                "_flightindex_WAR_flightqueryportlet_isDomestic=true&" +
//                "_flightindex_WAR_flightqueryportlet_isArrival=true&" +
//                "_flightindex_WAR_flightqueryportlet_languageId=&" +
//                "_flightindex_WAR_flightqueryportlet_search_type=&" +
//                "_flightindex_WAR_flightqueryportlet_flight_number_or_city=&" +
//                "_flightindex_WAR_flightqueryportlet_flight_date=0&" +
//                "_flightindex_WAR_flightqueryportlet_flight_time=all&" +
//                "_flightindex_WAR_flightqueryportlet_cur=1";
//        CloseableHttpClient httpClient = new DefaultHttpClient();
//        HttpGet get = new HttpGet(url);
//        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        get.setHeader("Accept-Encoding", "gzip, deflate");
//        get.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//        get.setHeader("Connection", "keep-alive");
//        get.setHeader("Cookie", "GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; JSESSIONID=F14F7D04B682A834D007A39DAC6F4A29.node1; Hm_lvt_e76379e9296ddf1578b19c77def4a876=1516949147,1516949188,1516949211,1516954041; Hm_lpvt_e76379e9296ddf1578b19c77def4a876=1516955258");
//        get.setHeader("Host", "www.gbiac.net");
//        get.setHeader("Upgrade-Insecure-Requests", "1");
//        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
//        CloseableHttpResponse response = httpClient.execute(get);
//        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
//        System.out.println(s);
//        Document doc = Jsoup.parse(s);
//        Elements tr = doc.getElementsByTag("tr");
//        for (Element element : tr) {
//            Flight flight = new Flight();
//            Elements td = element.getElementsByTag("td");
//            if (td.size() <= 0) {
//                continue;
//            }
//            flight.setDepTime(td.get(0).text());
//            flight.setAirlineName(td.get(1).text());
//            flight.setFlightNo(td.get(2).text());
//            flight.setArrCity(td.get(3).text());
//            flight.setCheckInCounter(td.get(4).text());
//            flight.setBoardGate(td.get(5).text());
//            flight.setFlightState(td.get(6).text());
//            result.add(flight);
//        }
//        return result;
//    }
//
//    private List<Flight> parse(int page, int rows) throws IOException, URISyntaxException {
//        List<Flight> result = new ArrayList();
//
//        String url = "http://www.gbiac.net/hbxx/flightquery?" +
//                "p_p_id=flightindex_WAR_flightqueryportlet&" +
//                "p_p_lifecycle=0&" +
//                "p_p_state=normal&" +
//                "p_p_mode=view&" +
//                "p_p_col_id=column-4&" +
//                "p_p_col_count=1&" +
//                "_flightindex_WAR_flightqueryportlet_delta=" + rows + "&" +
//                "_flightindex_WAR_flightqueryportlet_keywords=&" +
//                "_flightindex_WAR_flightqueryportlet_advancedSearch=false&" +
//                "_flightindex_WAR_flightqueryportlet_andOperator=true&" +
//                "_flightindex_WAR_flightqueryportlet_flight_number=&" +
//                "_flightindex_WAR_flightqueryportlet_city_code=&" +
//                "_flightindex_WAR_flightqueryportlet_airline_code=&" +
//                "_flightindex_WAR_flightqueryportlet_flight_route=4&" +
//                "_flightindex_WAR_flightqueryportlet_begin_flight_date=2018-01-26+00%3A00%3A00&" +
//                "_flightindex_WAR_flightqueryportlet_end_flight_date=2018-01-26+23%3A59%3A00&" +
//                "_flightindex_WAR_flightqueryportlet_flight_status=all&" +
//                "_flightindex_WAR_flightqueryportlet_isPassengerPlane=true&" +
//                "_flightindex_WAR_flightqueryportlet_isDomestic=true&" +
//                "_flightindex_WAR_flightqueryportlet_isArrival=true&" +
//                "_flightindex_WAR_flightqueryportlet_languageId=&" +
//                "_flightindex_WAR_flightqueryportlet_search_type=&" +
//                "_flightindex_WAR_flightqueryportlet_flight_number_or_city=&" +
//                "_flightindex_WAR_flightqueryportlet_flight_date=0&" +
//                "_flightindex_WAR_flightqueryportlet_flight_time=all&" +
//                "_flightindex_WAR_flightqueryportlet_cur=" + page;
//
//        CloseableHttpClient httpClient = new DefaultHttpClient();
//        HttpGet get = new HttpGet(url);
//        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        get.setHeader("Accept-Encoding", "gzip, deflate");
//        get.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//        get.setHeader("Connection", "keep-alive");
//        get.setHeader("Cookie", "GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; JSESSIONID=F14F7D04B682A834D007A39DAC6F4A29.node1; Hm_lvt_e76379e9296ddf1578b19c77def4a876=1516949147,1516949188,1516949211,1516954041; Hm_lpvt_e76379e9296ddf1578b19c77def4a876=1516955258");
//        get.setHeader("Host", "www.gbiac.net");
//        get.setHeader("Upgrade-Insecure-Requests", "1");
//        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
//
//        CloseableHttpResponse response = httpClient.execute(get);
//        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
//        System.out.println(s);
//        Document doc = Jsoup.parse(s);
//        Elements tr = doc.getElementsByTag("tr");
//        for (Element element : tr) {
//            Flight flight = new Flight();
//            Elements td = element.getElementsByTag("td");
//            if (td.size() <= 0) {
//                continue;
//            }
//            flight.setDepTime(td.get(0).text());
//            flight.setAirlineName(td.get(1).text());
//            flight.setFlightNo(td.get(2).text());
//            flight.setArrCity(td.get(3).text());
//            flight.setCheckInCounter(td.get(4).text());
//            flight.setBoardGate(td.get(5).text());
//            flight.setFlightState(td.get(6).text());
//            result.add(flight);
//        }
//        return result;
//    }
//
//    private List<Flight> parseFromCAN(int page, int rows) throws Exception {
//        List<Flight> result = new ArrayList();
//        String url = "http://www.gbiac.net/hbxx/flightquery";
//        Map<String, String> params = new HashMap<>();
//        params.put("p_p_id", "flightindex_WAR_flightqueryportlet");
//        params.put("p_p_lifecycle", "0");
//        params.put("p_p_state", "normal");
//        params.put("p_p_mode", "view");
//        params.put("p_p_col_id", "column-4");
//        params.put("p_p_col_count", "1");
//        params.put("_flightindex_WAR_flightqueryportlet_delta", String.valueOf(rows));
//        params.put("_flightindex_WAR_flightqueryportlet_keywords", "");
//        params.put("_flightindex_WAR_flightqueryportlet_advancedSearch", "false");
//        params.put("_flightindex_WAR_flightqueryportlet_andOperator", "true");
//        params.put("_flightindex_WAR_flightqueryportlet_flight_number", "");
//        params.put("_flightindex_WAR_flightqueryportlet_city_code", "");
//        params.put("_flightindex_WAR_flightqueryportlet_airline_code", "");
//        params.put("_flightindex_WAR_flightqueryportlet_flight_route", "4");
//        Date date = DateUtils.getDate("yyyy-MM-dd");
//        String dataStr = DateUtils.dateToString(date, "yyyy-MM-dd");
//        params.put("_flightindex_WAR_flightqueryportlet_begin_flight_date", dataStr + " 00:00:00");
//        params.put("_flightindex_WAR_flightqueryportlet_end_flight_date", dataStr + " 23:59:00");
//        params.put("_flightindex_WAR_flightqueryportlet_flight_status", "all");
//        params.put("_flightindex_WAR_flightqueryportlet_isPassengerPlane", "true");
//        params.put("_flightindex_WAR_flightqueryportlet_isDomestic", "true");
//        params.put("_flightindex_WAR_flightqueryportlet_isArrival", "true");
//        params.put("_flightindex_WAR_flightqueryportlet_languageId", "");
//        params.put("_flightindex_WAR_flightqueryportlet_search_type", "");
//        params.put("_flightindex_WAR_flightqueryportlet_flight_number_or_city", "");
//        params.put("_flightindex_WAR_flightqueryportlet_flight_date", "0");
//        params.put("_flightindex_WAR_flightqueryportlet_flight_time", "all");
//        params.put("_flightindex_WAR_flightqueryportlet_cur", String.valueOf(page));
//        URI uri = HttpClientUtils.getGetURI(url, HttpClientUtils.getNameValuePairs(params));
//
//        CloseableHttpClient httpClient = new DefaultHttpClient();
//        HttpGet get = new HttpGet(uri);
//        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        get.setHeader("Accept-Encoding", "gzip, deflate");
//        get.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//        get.setHeader("Connection", "keep-alive");
//        get.setHeader("Cookie", "GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; JSESSIONID=F14F7D04B682A834D007A39DAC6F4A29.node1; Hm_lvt_e76379e9296ddf1578b19c77def4a876=1516949147,1516949188,1516949211,1516954041; Hm_lpvt_e76379e9296ddf1578b19c77def4a876=1516955258");
//        get.setHeader("Host", "www.gbiac.net");
//        get.setHeader("Upgrade-Insecure-Requests", "1");
//        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
//
//        CloseableHttpResponse response = httpClient.execute(get);
//        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
//        System.out.println(s);
//        Document doc = Jsoup.parse(s);
//        Elements tr = doc.getElementsByTag("tr");
//        for (Element element : tr) {
//            Flight flight = new Flight();
//            Elements td = element.getElementsByTag("td");
//            if (td.size() <= 0) {
//                continue;
//            }
//            flight.setDepTime(td.get(0).text());
//            flight.setAirlineName(td.get(1).text());
//            flight.setFlightNo(td.get(2).text());
//            flight.setArrCity(td.get(3).text());
//            flight.setCheckInCounter(td.get(4).text());
//            flight.setBoardGate(td.get(5).text());
//            flight.setFlightState(td.get(6).text());
//            result.add(flight);
//        }
//        return result;
//    }
//
//    public static void main(String[] args) throws Exception {
//        new CommonController().parseFromCAN(3, 200);
//    }
//
//    static class Flight {
//        private String airlineName;
//        private String flightNo;
//        private String arrCity;
//        private String depTime;
//        private String checkInCounter;
//        private String boardGate;
//        private String flightState;
//
//        public String getAirlineName() {
//            return airlineName;
//        }
//
//        public void setAirlineName(String airlineName) {
//            this.airlineName = airlineName;
//        }
//
//        public String getFlightNo() {
//            return flightNo;
//        }
//
//        public void setFlightNo(String flightNo) {
//            this.flightNo = flightNo;
//        }
//
//        public String getArrCity() {
//            return arrCity;
//        }
//
//        public void setArrCity(String arrCity) {
//            this.arrCity = arrCity;
//        }
//
//        public String getDepTime() {
//            return depTime;
//        }
//
//        public void setDepTime(String depTime) {
//            this.depTime = depTime;
//        }
//
//        public String getCheckInCounter() {
//            return checkInCounter;
//        }
//
//        public void setCheckInCounter(String checkInCounter) {
//            this.checkInCounter = checkInCounter;
//        }
//
//        public String getBoardGate() {
//            return boardGate;
//        }
//
//        public void setBoardGate(String boardGate) {
//            this.boardGate = boardGate;
//        }
//
//        public String getFlightState() {
//            return flightState;
//        }
//
//        public void setFlightState(String flightState) {
//            this.flightState = flightState;
//        }
//
//        @Override
//        public String toString() {
//            return "Flight{" +
//                    "airlineName='" + airlineName + '\'' +
//                    ", flightNo='" + flightNo + '\'' +
//                    ", arrCity='" + arrCity + '\'' +
//                    ", depTime='" + depTime + '\'' +
//                    ", checkInCounter='" + checkInCounter + '\'' +
//                    ", boardGate='" + boardGate + '\'' +
//                    ", flightState='" + flightState + '\'' +
//                    '}';
//        }
//    }

}
