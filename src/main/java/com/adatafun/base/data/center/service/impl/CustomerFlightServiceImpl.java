package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.dto.FlightFeeyoCustomDTO;
import com.adatafun.base.data.center.mapper.CustomFlightPOMapper;
import com.adatafun.base.data.center.mapper.CustomerPOMapper;
import com.adatafun.base.data.center.mapper.FlightPOMapper;
import com.adatafun.base.data.center.mapper.FlightPushPOMapper;
import com.adatafun.base.data.center.po.CustomFlightPO;
import com.adatafun.base.data.center.po.CustomerPO;
import com.adatafun.base.data.center.po.FlightPO;
import com.adatafun.base.data.center.po.FlightPushPO;
import com.adatafun.base.data.center.service.CustomerFlightServiceInterface;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.HttpClientUtils;
import com.adatafun.base.data.center.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @date: 2018/1/2 下午4:35
 * @author: ironc
 * @version: 1.0
 */
@Service
public class CustomerFlightServiceImpl implements CustomerFlightServiceInterface {

    private static Logger logger = LoggerFactory.getLogger(CustomerFlightServiceImpl.class);

    @Autowired
    private CustomFlightPOMapper customFlightPOMapper;

    @Autowired
    private CustomerPOMapper customerPOMapper;

    @Autowired
    private FlightPOMapper flightPOMapper;

    @Autowired
    private FlightPushPOMapper flightPushPOMapper;

    @Override
    public void feeyoTrigger(List<FlightFeeyoCustomDTO> flightFeeyoCustomDTOList) {
        FlightPushPO flightPushPO = new FlightPushPO();
        String s;
        Long customerId = null;
        try {
            for (FlightFeeyoCustomDTO flightFeeyoCustomDTO : flightFeeyoCustomDTOList) {
                FlightPO flightPO = flightPOMapper.selectByFourParams(flightFeeyoCustomDTO.getFlightNo(), DateUtils.formate(flightFeeyoCustomDTO.getDepScheduledDate(), Dictionary.DATE_FORMAT),
                        flightFeeyoCustomDTO.getDepAirportCode(), flightFeeyoCustomDTO.getArrAirportCode());
                flightFeeyoCustomDTO.setDepDate(DateUtils.formate(flightFeeyoCustomDTO.getDepScheduledDate(), Dictionary.DATE_FORMAT));
                List<CustomFlightPO> customFlightPOS = customFlightPOMapper.selectByFourParams(flightFeeyoCustomDTO);
                for (CustomFlightPO customFlightPO : customFlightPOS) {
                    customerId = customFlightPO.getCustomerId();
                    if (StringUtils.isNoneBlank(customFlightPO.getCustomUrl())) {
                        flightPushPO.setCustomerId(customerId);
                        flightPushPO.setPushContext(JSON.toJSONString(flightPO));
                        if (logger.isInfoEnabled()) {
                            logger.info("======》需要推送的URL : " + customFlightPO.getCustomUrl());
                        }
                        s = HttpClientUtils.httpPostForWebService(customFlightPO.getCustomUrl(), flightPO);
                        if (logger.isInfoEnabled()) {
                            logger.info("======》推送响应结果 : " + s);
                        }
                        JSONObject object = JSONObject.parseObject(s);
                        if (Objects.equals(object.getString("code"), "200")) {
                            flightPushPO.setIsSucceeded(Dictionary.CONDITION_TRUE);
                        } else {
                            flightPushPO.setIsSucceeded(Dictionary.CONDITION_FALSE);
                        }
                        flightPushPO.setInvokeResult(s);
                        flightPushPOMapper.insertOne(flightPushPO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flightPushPO.setIsSucceeded(Dictionary.CONDITION_FALSE);
            flightPushPO.setCustomerId(customerId);
            flightPushPO.setInvokeResult("{}");
            flightPushPOMapper.insertOne(flightPushPO);
        }
    }

    @Override
    public boolean createCustomFlight(Integer appId, CustomFlightPO customFlightPO) {
        boolean result;
        CustomerPO customerPO = customerPOMapper.selectByAppId(appId);
        customFlightPO.setCustomerId(customerPO.getCustomerId());
        CustomFlightPO customFlightPOLocal = customFlightPOMapper.selectByFourParamsAndCustomerId(customFlightPO);
        if (customFlightPOLocal == null) {
            customFlightPO.setCustomUrl(customerPO.getCustomerUrl());
            customFlightPOMapper.insertOne(customFlightPO);
            result = true;
        } else {
            result = false;
        }
        return result;
    }

}
