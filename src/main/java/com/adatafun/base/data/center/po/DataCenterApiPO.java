package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;

/**
 * @table_name: operator_data_center_api.java
 * @date: 2017-12-28 14:10:23
 * @author: tiecheng
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class DataCenterApiPO extends BaseEntity {
    /**
     * 主键自增id
     */
    private Long flightCenterApiId;

    /**
     * flightCenter接口名字
     */
    private String apiName;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 客户ID
     */
    private String customerCode;

    /**
     * 执行状态
     */
    private String invokeState;

    /**
     * 执行结果
     */
    private String invokeResult;

    /**
     * 航班数据条数
     */
    private Integer dataNum;

    /**
     * 主键自增id
     * @return flight_center_api_id 主键自增id
     */
    public Long getFlightCenterApiId() {
        return flightCenterApiId;
    }

    /**
     * 主键自增id
     * @param flightCenterApiId 主键自增id
     */
    public void setFlightCenterApiId(Long flightCenterApiId) {
        this.flightCenterApiId = flightCenterApiId;
    }

    /**
     * flightCenter接口名字
     * @return api_name flightCenter接口名字
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * flightCenter接口名字
     * @param apiName flightCenter接口名字
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    /**
     * 客户ID
     * @return customer_code 客户Code
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 客户ID
     * @param customerCode 客户Code
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * 执行状态
     * @return invoke_state 执行状态
     */
    public String getInvokeState() {
        return invokeState;
    }

    /**
     * 执行状态
     * @param invokeState 执行状态
     */
    public void setInvokeState(String invokeState) {
        this.invokeState = invokeState;
    }

    /**
     * 执行结果
     * @return invoke_result 执行结果
     */
    public String getInvokeResult() {
        return invokeResult;
    }

    /**
     * 执行结果
     * @param invokeResult 执行结果
     */
    public void setInvokeResult(String invokeResult) {
        this.invokeResult = invokeResult;
    }

    public Integer getDataNum() {
        return dataNum;
    }

    public void setDataNum(Integer dataNum) {
        this.dataNum = dataNum;
    }

}