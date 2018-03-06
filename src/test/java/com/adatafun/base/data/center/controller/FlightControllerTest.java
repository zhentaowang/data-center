package com.adatafun.base.data.center.controller;

import com.adatafun.base.data.center.DataCenterApplication;
import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.dto.FlightQueryDTO;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.HttpClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = DataCenterApplication.class)
public class FlightControllerTest {

    @Autowired
    private FlightController flightController;

    @Test
    public void flightInfo() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("appId", "957188");
        params.put("depDate", "2018-03-01");
        params.put("token", "a92bce88bf87788cab5d1c7e83e2d970");
        params.put("flightNo", "HU7294");
        String url = "http://127.0.0.1:8066/flight/flightInfo";
        for (int i = 0; i < 10; i++) {
            String s = HttpClientUtils.httpGetForWebService(url, params);
            System.out.println(s);
        }

        Map<String, String> params2 = new HashMap<>();
        params2.put("appId", "957188");
        params2.put("depDate", "2018-03-01");
        params2.put("token", "a92bce88bf87788cab5d1c7e83e2d970");
//        params2.put("depCode", "CAN");
        params2.put("depCode", "CKG");
        params2.put("arrCode", "SYX");
        String url2 = "http://127.0.0.1:8066/flight/flightInfo";
        for (int i = 0; i < 10; i++) {
            String s = HttpClientUtils.httpGetForWebService(url2, params2);
            System.out.println(s);
        }
    }

    @Test
    public void flightInfoCurrent2() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final int current = 50;
        final CountDownLatch countDownLatchCurrent = new CountDownLatch(current);

        for (int i = 0; i < current; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "准备就绪");
                    countDownLatchCurrent.countDown();
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + "线程启动");
                    Map<String, String> params2 = new HashMap<>();
                    params2.put("appId", "957188");
                    params2.put("depDate", "2018-03-01");
                    params2.put("token", "a92bce88bf87788cab5d1c7e83e2d970");
                    params2.put("depCode", "TAO");
                    params2.put("arrCode", "SYX");
                    String url2 = "http://127.0.0.1:8066/flight/flightInfo";
                    System.out.println(params2.toString());
                    String s = HttpClientUtils.httpGetForWebService(url2, params2);
                    System.out.println(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }

        countDownLatchCurrent.await();
        countDownLatch.countDown();
    }

    @Test
    public void flightInfoCurrent() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final int current = 50;
        final CountDownLatch countDownLatchCurrent = new CountDownLatch(current);

        FlightQueryDTO flightQueryDTO = new FlightQueryDTO();
        flightQueryDTO.setAppId(957188);
        flightQueryDTO.setArrCode("SYX");
        flightQueryDTO.setDepCode("TAO");
//        flightQueryDTO.setFlightNo();
        flightQueryDTO.setDepDate(DateUtils.stringToDate("2018-03-01", Dictionary.DATE_FORMAT));
        flightQueryDTO.setToken("a92bce88bf87788cab5d1c7e83e2d970");

        for (int i = 0; i < current; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "准备就绪");
                    countDownLatchCurrent.countDown();
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + "线程启动");
                    String s = flightController.flightInfo(flightQueryDTO);
                    System.out.println(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        countDownLatchCurrent.await();
        countDownLatch.countDown();
    }

    @Test
    public void flightInfoNotRealTime() throws Exception {
        Map<String, String> params2 = new HashMap<>();
        params2.put("appId", "957188");
        params2.put("depDate", "2018-03-01");
        params2.put("token", "a92bce88bf87788cab5d1c7e83e2d970");
        params2.put("depCode", "CKG");
        params2.put("arrCode", "SYX");
        String url2 = "http://127.0.0.1:8066/flight/flightInfo";
        String s = HttpClientUtils.httpGetForWebService(url2, params2);
        System.out.println(s);
    }

    @Test
    public void customFlight() throws Exception {
    }

}