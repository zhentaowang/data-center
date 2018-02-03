package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @table_name: f_custom_flight.java
 * @date: 2017-12-28 14:10:23
 * @author: tiecheng
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class CustomFlightPO  extends BaseEntity {
    /**
     * 主键自增id
     */
    private Long customFlightId;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 定制的URl
     */
    private String customUrl;

    /**
     * 航班ID
     */
    private Long flightId;

    /**
     * 更新标识
     */
    private Integer updateFlag;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 出发日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date depDate;

    /**
     * 出发机场三字码
     */
    private String depAirportCode;


    /**
     * 到达机场三字码
     */
    private String arrAirportCode;

    /**
     * 主键自增id
     * @return custom_flight_id 主键自增id
     */
    public Long getCustomFlightId() {
        return customFlightId;
    }

    /**
     * 主键自增id
     * @param customFlightId 主键自增id
     */
    public void setCustomFlightId(Long customFlightId) {
        this.customFlightId = customFlightId;
    }

    /**
     * 客户ID
     * @return customer_id 客户ID
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 客户ID
     * @param customerId 客户ID
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 定制的URl
     * @return custom_url 定制的URl
     */
    public String getCustomUrl() {
        return customUrl;
    }

    /**
     * 定制的URl
     * @param customUrl 定制的URl
     */
    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    /**
     * 航班ID
     * @return flight_id 航班ID
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * 航班ID
     * @param flightId 航班ID
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * 更新标识
     * @return update_flag 更新标识
     */
    public Integer getUpdateFlag() {
        return updateFlag;
    }

    /**
     * 更新标识
     * @param updateFlag 更新标识
     */
    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

}