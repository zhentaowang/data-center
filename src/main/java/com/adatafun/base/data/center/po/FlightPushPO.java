package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;

/**
 * @table_name: f_flight_push.java
 * @date: 2017-12-28 14:10:23
 * @author: tiecheng
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class FlightPushPO  extends BaseEntity {
    /**
     * 主键自增id
     */
    private Long flightPushId;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 是否推送成功 默认为1 1:成功， 2:失败
     */
    private Short isSucceeded;

    /**
     * 执行结果
     */
    private String invokeResult;

    /**
     * 推送内容
     */
    private String pushContext;

    /**
     * 主键自增id
     * @return flight_push_id 主键自增id
     */
    public Long getFlightPushId() {
        return flightPushId;
    }

    /**
     * 主键自增id
     * @param flightPushId 主键自增id
     */
    public void setFlightPushId(Long flightPushId) {
        this.flightPushId = flightPushId;
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
     * 是否推送成功 默认为1 1:成功， 2:失败
     * @return is_succeeded 是否推送成功 默认为1 1:成功， 2:失败
     */
    public Short getIsSucceeded() {
        return isSucceeded;
    }

    /**
     * 是否推送成功 默认为1 1:成功， 2:失败
     * @param isSucceeded 是否推送成功 默认为1 1:成功， 2:失败
     */
    public void setIsSucceeded(Short isSucceeded) {
        this.isSucceeded = isSucceeded;
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

    public String getPushContext() {
        return pushContext;
    }

    public void setPushContext(String pushContext) {
        this.pushContext = pushContext;
    }

}