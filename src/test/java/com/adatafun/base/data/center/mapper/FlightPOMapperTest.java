package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.DataCenterApplication;
import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.po.FlightPO;
import com.adatafun.base.data.center.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.DataInput;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class FlightPOMapperTest {

    @Autowired
    private FlightPOMapper flightPOMapper;

    @Test
    public void selectByPrimaryKey() throws Exception {
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {
    }

    @Test
    public void updateByPrimaryKey() throws Exception {
    }

    @Test
    public void selectByDepDateAndFlightNo() throws Exception {
    }

    @Test
    public void updateByFourParams() throws Exception {
    }

    @Test
    public void existByFourParams() throws Exception {
    }

    @Test
    public void selectByDepDateAndDepCodeAndArrCode() throws Exception {
    }

    @Test
    public void insertOne() throws Exception {
    }

    @Test
    public void selectByFourParams() throws Exception {
    }

    @Test
    public void updateIsCustomByFourParams() throws Exception {
    }

    @Test
    public void selectByFlightNoAndDepDate() throws Exception {
    }

    @Test
    public void insertOrUpdateByFourParams() throws Exception {
        FlightPO flightPO = new FlightPO();
        flightPO.setFlightNo("TC0001");
        flightPO.setDepDate(DateUtils.stringToDate("2018-03-01", Dictionary.DATE_FORMAT));
        flightPO.setDepAirportCode("SSW");
        flightPO.setArrAirportCode("TTC");
        for (int i = 0; i < 10; i++) {
            flightPOMapper.insertOrUpdateByFourParams(flightPO);
        }
    }

}