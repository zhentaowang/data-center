package com.adatafun.base.data.center.service;

import com.adatafun.base.data.center.dto.FlightFeeyoCustomDTO;
import com.adatafun.base.data.center.po.CustomFlightPO;

import java.util.List;

/**
 *
 *
 * @date: 2018/1/2 下午4:29
 * @author: ironc
 * @version: 1.0
 */
public interface CustomerFlightServiceInterface {

    /**
     * 飞常准触发器
     *
     * @param flightFeeyoCustomDTOList
     */
    void feeyoTrigger(List<FlightFeeyoCustomDTO> flightFeeyoCustomDTOList);

    /**
     * 创建定制信息
     *
     * @param appId
     * @param customFlightPO  定制航班对象
     * @return
     */
    boolean createCustomFlight(Integer appId, CustomFlightPO customFlightPO);

}
