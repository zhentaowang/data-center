package com.adatafun.base.data.center.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 航班查询对象
 *
 * @date: 2017/12/25 下午2:09
 * @author: ironc
 * @version: 1.0
 */
public class FlightQueryDTO {

    private Long customerId;

    private String flightNo;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date depDate;

    private String depCode;

    private String arrCode;

    private String token;

    private String customerCode;

    private Integer appId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "FlightQueryDTO{" +
                "customerId=" + customerId +
                ", flightNo='" + flightNo + '\'' +
                ", depDate=" + depDate +
                ", depCode='" + depCode + '\'' +
                ", arrCode='" + arrCode + '\'' +
                ", token='" + token + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", appId=" + appId +
                '}';
    }

}
