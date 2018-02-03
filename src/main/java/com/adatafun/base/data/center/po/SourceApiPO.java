package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;

/**
 * @table_name: operator_source_api.java
 * @date: 2017-12-28 14:10:23
 * @author: tiecheng
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
 */
public class SourceApiPO extends BaseEntity {
    /**
     * 主键自增id
     */
    private Long sourceApiId;

    /**
     * 源接口名字
     */
    private String sourceName;

    /**
     * source接口名字
     */
    private String apiName;

    /**
     * 执行状态
     */
    private String requestParam;

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
     *
     * @return source_api_id 主键自增id
     */
    public Long getSourceApiId() {
        return sourceApiId;
    }

    /**
     * 主键自增id
     *
     * @param sourceApiId 主键自增id
     */
    public void setSourceApiId(Long sourceApiId) {
        this.sourceApiId = sourceApiId;
    }

    /**
     * 源接口名字
     *
     * @return source_name 源接口名字
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 源接口名字
     *
     * @param sourceName 源接口名字
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * source接口名字
     *
     * @return api_name source接口名字
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * source接口名字
     *
     * @param apiName source接口名字
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * 执行状态
     *
     * @return request_param 请求参数
     */
    public String getRequestParam() {
        return requestParam;
    }

    /**
     * 执行状态
     *
     * @param requestParam 请求参数
     */
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    /**
     * 执行结果
     *
     * @return invoke_result 执行结果
     */
    public String getInvokeResult() {
        return invokeResult;
    }

    /**
     * 执行结果
     *
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