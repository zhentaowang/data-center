package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.DataCenterApplication;
import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.service.FlightServiceInterface;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.vo.FlightVO;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tiecheng on 2018/2/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class FlightServiceImplTest {

    @Autowired
    private FlightServiceInterface flightService;

    /**
     * 线程数量
     */
    public static final int THREAD_NUM = 100;

    /**
     * 开始时间
     */
    private static long startTime = 0L;

//    @PostConstruct
    public void init() {
        try {
            startTime = System.currentTimeMillis();
            System.out.println("******************** WaitThreadTest started at:" + System.currentTimeMillis() + " ********************");

            // 初始化计数器为100
            CountDownLatch countDownLatch = new CountDownLatch(100);

            for (int i = 0; i < THREAD_NUM; i ++) {
                new Thread(new RequestTest(countDownLatch)).start();
            }

            // 启动多个线程
            countDownLatch.await();

            System.out.println("******************** WaitThreadTest ended at:" + System.currentTimeMillis() + " ********************");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    class RequestTest implements Runnable {

        private final CountDownLatch startLatch;

        public RequestTest(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        @Override
        public void run() {
            try {
                Date date = DateUtils.stringToDate("2018-02-23", Dictionary.DATE_FORMAT);
                System.out.println(Thread.currentThread().getName()  + "哈哈");
                // 执行操作
                List<FlightVO> flightVOList = flightService.flightInfo(date, "CAN", "SYX");
                System.out.println(JSON.toJSONString(flightVOList));


                // 线程等待
                startLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void flightInfo() throws Exception {
        System.out.println("rest");
    }

    @Test
    public void flightInfo1() throws Exception {
    }

    @Test
    public void flightInfoNotRealTime() throws Exception {
    }

    @Test
    public void flightInfo2() throws Exception {
    }

    @Test
    public void updateFlight() throws Exception {
    }

    @Test
    public void isCustom() throws Exception {
    }

    @Test
    public void custom() throws Exception {
    }

    @Test
    public void custom1() throws Exception {
    }

    @Test
    public void cancelCustom() throws Exception {
    }

}