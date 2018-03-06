package com.adatafun.base.data.center.util;

import com.adatafun.base.data.center.DataCenterApplication;
import com.adatafun.base.data.center.mapper.FlightPushPOMapper;
import com.adatafun.base.data.center.po.FlightPushPO;
import com.adatafun.base.data.center.vo.FlightVO;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiecheng on 2018/1/6.
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(
        classes = DataCenterApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
public class ServiceTest {

    @Autowired
    private FlightPushPOMapper flightPushPOMapper;

    @Test
    public void testInsertOne() {
        FlightPushPO flightPushPO = new FlightPushPO();
        flightPushPO.setPushContext("{\"isStop\": 0, \"arrCity\": \"沈阳\", \"arrDate\": \"2018-01-06\", \"arrGate\": \"\", \"depCity\": \"广州\", \"depDate\": \"2018-01-06\", \"isShare\": 0, \"legFlag\": 1, \"flightId\": 5755, \"flightNo\": \"CZ6344\", \"isCustom\": 1, \"boardGate\": \"B223\", \"arrAirport\": \"沈阳桃仙\", \"boardState\": \"登机结束\", \"depAirport\": \"广州白云\", \"updateFlag\": \"0\", \"arrTerminal\": \"T3\", \"arrTimeZone\": \"28800\", \"depTerminal\": \"B\", \"depTimeZone\": \"28800\", \"flightState\": \"到达\", \"isNearOrFar\": 3, \"arrActualDate\": \"2018-01-06 20:58:00\", \"depActualDate\": \"2018-01-06 17:57:00\", \"shareFlightNo\": \"\", \"arrAirportCode\": \"SHE\", \"checkInCounter\": \"E28,D18-21,E4-E7\", \"depAirportCode\": \"CAN\", \"flightCategory\": 1, \"baggageCarousel\": \"4\", \"arrEstimatedDate\": \"2018-01-06 21:11:00\", \"arrScheduledDate\": \"2018-01-06 21:15:00\", \"depScheduledDate\": \"2018-01-06 17:30:00\", \"airlineChineseName\": \"中国南方航空股份有限公司\"}");
        flightPushPO.setIsSucceeded(Short.parseShort("1"));
        flightPushPO.setInvokeResult("{\"code\":\"200\"}");
        flightPushPO.setCustomerId(1L);
        flightPushPOMapper.insertOne(flightPushPO);
    }

    @Test
    public void tt() throws InterruptedException {
        List<FlightVO> result = new ArrayList<>();
        String cacheValue = "111";
        boolean isCacheValueEmpty = StringUtils.isBlank(cacheValue);
        for (int i = 0; i < 3; i++) {
            Thread.sleep(250);
            if (isCacheValueEmpty) {
                continue;
            } else {
                if ("-1".equals(cacheValue)) {
                    break;
                }
//                result = JSONArray.parseArray(cacheValue, FlightVO.class);
                System.out.println(cacheValue);
                break;
            }
        }
    }

}
