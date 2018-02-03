package com.adatafun.base.data.center.crawler;

import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by tiecheng on 2018/1/26.
 */
@Component
public class CANCrawlerHandler {

    private static final Logger logger = LoggerFactory.getLogger(CANCrawlerHandler.class);

    private final static String URL = "http://www.gbiac.net/hbxx/flightquery";

    public String parseAllFromCANDep() throws Exception {
        int page = 1;
        int rows = 200;
        List<Flight> flights = new ArrayList<>();
        List<Flight> flightRow = null;

        while (flightRow == null || flightRow.size() == 200) {
            flightRow = parseToList(getUri0(URL, page, rows, "4"), head0());
            if (flightRow != null && flightRow.size() > 0) {
                flights.addAll(flightRow);
                page++;
            }
        }

        return JSON.toJSONString(flights);
    }

    public String parseAllFromCANArr() throws Exception {
        int page = 1;
        int rows = 200;
        List<Flight> flights = new ArrayList<>();
        List<Flight> flightRow = null;

        while (flightRow == null || flightRow.size() == 200) {
            flightRow = parseToList(getUri0(URL, page, rows, "5"), head0());
            if (flightRow != null && flightRow.size() > 0) {
                flights.addAll(flightRow);
                page++;
            }
        }

        return JSON.toJSONString(flights);
    }

    public String parseFromCANDep(int page, int rows) throws Exception {
        page = page == 0 ? 1 : page;
        rows = rows == 0 ? 200 : rows;
        return parse(getUri0(URL, page, rows, "4"), head0());
    }

    public String parseFromCANArr(int page, int rows) throws Exception {
        page = page == 0 ? 1 : page;
        rows = rows == 0 ? 200 : rows;
        return parse(getUri0(URL, page, rows, "5"), head0());
    }

    private String parse(URI uri, Map<String, String> header) throws Exception {
        List<Flight> result = new ArrayList();
        CloseableHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(uri);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            get.setHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(get);
        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
        if (logger.isDebugEnabled()) {
            logger.debug("====》从白云官网爬取的响应内容{}：", s);
        }
        Document doc = Jsoup.parse(s);
        Elements tr = doc.getElementsByTag("tr");
        for (Element element : tr) {
            Flight flight = new Flight();
            Elements td = element.getElementsByTag("td");
            if (td.size() <= 0) {
                continue;
            }
            flight.setDepTime(td.get(0).text());
            flight.setAirlineName(td.get(1).text());
            flight.setFlightNo(td.get(2).text());
            flight.setArrCity(td.get(3).text());
            flight.setCheckInCounter(td.get(4).text());
            flight.setBoardGate(td.get(5).text());
            flight.setFlightState(td.get(6).text());
            result.add(flight);
        }
        return JSON.toJSONString(result);
    }

    private List<Flight> parseToList(URI uri, Map<String, String> header) throws Exception {
        List<Flight> result = new ArrayList();
        CloseableHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(uri);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            get.setHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(get);
        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
        if (logger.isDebugEnabled()) {
            logger.debug("====》从白云官网爬取的响应内容{}：", s);
        }
        if (response.getStatusLine().getStatusCode() == 200){
            Document doc = Jsoup.parse(s);
            Elements tr = doc.getElementsByTag("tr");
            for (Element element : tr) {
                Flight flight = new Flight();
                Elements td = element.getElementsByTag("td");
                if (td.size() <= 0) {
                    continue;
                }
                flight.setDepTime(td.get(0).text());
                flight.setAirlineName(td.get(1).text());
                flight.setFlightNo(td.get(2).text());
                flight.setArrCity(td.get(3).text());
                flight.setCheckInCounter(td.get(4).text());
                flight.setBoardGate(td.get(5).text());
                flight.setFlightState(td.get(6).text());
                result.add(flight);
            }
        }
        return result;
    }

    private Map<String, String> head0() {
        Map<String, String> result = new HashMap<>();
        result.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        result.put("Accept-Encoding", "gzip, deflate");
        result.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        result.put("Connection", "keep-alive");
        result.put("Cookie", "GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; JSESSIONID=F14F7D04B682A834D007A39DAC6F4A29.node1; Hm_lvt_e76379e9296ddf1578b19c77def4a876=1516949147,1516949188,1516949211,1516954041; Hm_lpvt_e76379e9296ddf1578b19c77def4a876=1516955258");
        result.put("Host", "www.gbiac.net");
        result.put("Upgrade-Insecure-Requests", "1");
        result.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        return result;
    }

    private URI getUri0(String url, int page, int rows, String router) throws ParseException, URISyntaxException {
        Map<String, String> params = new HashMap<>();
        params.put("p_p_id", "flightindex_WAR_flightqueryportlet");
        params.put("p_p_lifecycle", "0");
        params.put("p_p_state", "normal");
        params.put("p_p_mode", "view");
        params.put("p_p_col_id", "column-4");
        params.put("p_p_col_count", "1");
        params.put("_flightindex_WAR_flightqueryportlet_delta", String.valueOf(rows));
        params.put("_flightindex_WAR_flightqueryportlet_keywords", "");
        params.put("_flightindex_WAR_flightqueryportlet_advancedSearch", "false");
        params.put("_flightindex_WAR_flightqueryportlet_andOperator", "true");
        params.put("_flightindex_WAR_flightqueryportlet_flight_number", "");
        params.put("_flightindex_WAR_flightqueryportlet_city_code", "");
        params.put("_flightindex_WAR_flightqueryportlet_airline_code", "");
        params.put("_flightindex_WAR_flightqueryportlet_flight_route", router);
        Date date = DateUtils.getDate("yyyy-MM-dd");
        String dataStr = DateUtils.dateToString(date, "yyyy-MM-dd");
        params.put("_flightindex_WAR_flightqueryportlet_begin_flight_date", dataStr + " 00:00:00");
        params.put("_flightindex_WAR_flightqueryportlet_end_flight_date", dataStr + " 23:59:00");
        params.put("_flightindex_WAR_flightqueryportlet_flight_status", "all");
        params.put("_flightindex_WAR_flightqueryportlet_isPassengerPlane", "true");
        params.put("_flightindex_WAR_flightqueryportlet_isDomestic", "true");
        params.put("_flightindex_WAR_flightqueryportlet_isArrival", "true");
        params.put("_flightindex_WAR_flightqueryportlet_languageId", "");
        params.put("_flightindex_WAR_flightqueryportlet_search_type", "");
        params.put("_flightindex_WAR_flightqueryportlet_flight_number_or_city", "");
        params.put("_flightindex_WAR_flightqueryportlet_flight_date", "0");
        params.put("_flightindex_WAR_flightqueryportlet_flight_time", "all");
        params.put("_flightindex_WAR_flightqueryportlet_cur", String.valueOf(page));
        URI uri = HttpClientUtils.getGetURI(url, HttpClientUtils.getNameValuePairs(params));
        return uri;
    }

    static class Flight {
        private String airlineName;
        private String flightNo;
        private String arrCity;
        private String depTime;
        private String checkInCounter;
        private String boardGate;
        private String flightState;

        public String getAirlineName() {
            return airlineName;
        }

        public void setAirlineName(String airlineName) {
            this.airlineName = airlineName;
        }

        public String getFlightNo() {
            return flightNo;
        }

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public String getArrCity() {
            return arrCity;
        }

        public void setArrCity(String arrCity) {
            this.arrCity = arrCity;
        }

        public String getDepTime() {
            return depTime;
        }

        public void setDepTime(String depTime) {
            this.depTime = depTime;
        }

        public String getCheckInCounter() {
            return checkInCounter;
        }

        public void setCheckInCounter(String checkInCounter) {
            this.checkInCounter = checkInCounter;
        }

        public String getBoardGate() {
            return boardGate;
        }

        public void setBoardGate(String boardGate) {
            this.boardGate = boardGate;
        }

        public String getFlightState() {
            return flightState;
        }

        public void setFlightState(String flightState) {
            this.flightState = flightState;
        }

        @Override
        public String toString() {
            return "Flight{" +
                    "airlineName='" + airlineName + '\'' +
                    ", flightNo='" + flightNo + '\'' +
                    ", arrCity='" + arrCity + '\'' +
                    ", depTime='" + depTime + '\'' +
                    ", checkInCounter='" + checkInCounter + '\'' +
                    ", boardGate='" + boardGate + '\'' +
                    ", flightState='" + flightState + '\'' +
                    '}';
        }
    }

}
