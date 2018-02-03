package com.adatafun.base.data.center.util;

import com.adatafun.base.data.center.crawler.FeeyoCrawlerHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by tiecheng on 2018/1/26.
 */
public class JsonpUtilsTest {

    @Test
    public void parse() throws IOException {
        CloseableHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://www.gbiac.net/hbxx/flightquery?isArrival=false&isAll=true&recently_20_data=true");
        get.setHeader("Host", "www.gbiac.net");
        get.setHeader("Connection", "keep-alive");
        get.setHeader("Cache-Control", "max-age=0");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        get.setHeader("Upgrade-Insecure-Requests", "1");
        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate");
        get.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        get.setHeader("Cookie", "JSESSIONID=E6FB44B41BD4340E994ECB80449BCB65.node1; GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; Hm_lvt_e76379e9296ddf1578b19c77def4a876=1516947710; Hm_lpvt_e76379e9296ddf1578b19c77def4a876=1516947756");
        CloseableHttpResponse response = httpClient.execute(get);
        String s = EntityUtils.toString(response.getEntity(), "UTF-8");
        Document doc = Jsoup.parse(s);
        Elements tr = doc.getElementsByTag("tr");
        for (Element element : tr) {
            System.out.println(element.html());
        }
    }

    @Test
    public void test2() throws Exception {
        String s = HttpClientUtils.httpGetForWebService("http://webapp.veryzhun.com/h5/flightsearch?fnum=CZ329&date=2018-02-01&token=74e5d4cac3179fc076af4f401fd4ebe3");
        System.out.println(s);
    }

}
