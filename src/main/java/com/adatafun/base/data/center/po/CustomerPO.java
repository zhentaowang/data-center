package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;

import java.util.Date;

/**
 * @table_name: base_customer.java
 * @date: 2017-12-28 14:10:23
 * @author: tiecheng
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class CustomerPO extends BaseEntity {
    /**
     * 主键自增id
     */
    private Long customerId;

    /**
     * 客户名字
     */
    private String customerName;

    /**
     * 客户说明
     */
    private String customerRemark;

    /**
     * 定制URL
     */
    private String customerUrl;

    /**
     * 客户类型 1：内部客户 2：外部客户
     */
    private Short customerType;

    /**
     * 客户剩余次数
     */
    private Integer customerTotal;

    /**
     * 客户标识
     */
    private String customerCode;

    /**
     * 客户ID号
     */
    private Integer appId;

    /**
     * 过期日期
     */
    private Date expiryDate;

    /**
     * 主键自增id
     * @return customer_id 主键自增id
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 主键自增id
     * @param customerId 主键自增id
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 客户名字
     * @return customer_name 客户名字
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 客户名字
     * @param customerName 客户名字
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 客户说明
     * @return customer_remark 客户说明
     */
    public String getCustomerRemark() {
        return customerRemark;
    }

    /**
     * 客户说明
     * @param customerRemark 客户说明
     */
    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    /**
     * 定制URL
     * @return customer_url 定制URL
     */
    public String getCustomerUrl() {
        return customerUrl;
    }

    /**
     * 定制URL
     * @param customerUrl 定制URL
     */
    public void setCustomerUrl(String customerUrl) {
        this.customerUrl = customerUrl;
    }

    /**
     * 客户类型 1：内部客户 2：外部客户
     * @return customer_type 客户类型 1：内部客户 2：外部客户
     */
    public Short getCustomerType() {
        return customerType;
    }

    /**
     * 客户类型 1：内部客户 2：外部客户
     * @param customerType 客户类型 1：内部客户 2：外部客户
     */
    public void setCustomerType(Short customerType) {
        this.customerType = customerType;
    }

    /**
     * 客户剩余次数
     * @return customer_total 客户剩余次数
     */
    public Integer getCustomerTotal() {
        return customerTotal;
    }

    /**
     * 客户剩余次数
     * @param customerTotal 客户剩余次数
     */
    public void setCustomerTotal(Integer customerTotal) {
        this.customerTotal = customerTotal;
    }

    /**
     * 客户标识
     * @return customer_code 客户标识
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 客户标识
     * @param customerCode 客户标识
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * 客户ID号
     * @return app_id 客户ID号
     */
    public Integer getAppId() {
        return appId;
    }

    /**
     * 客户ID号
     * @param appId 客户ID号
     */
    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    /**
     * 过期日期
     * @return expiry_date 过期日期
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * 过期日期
     * @param expiryDate 过期日期
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}