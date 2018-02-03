package com.adatafun.base.data.center.vo;

/**
 * 创建客户端后的返回对象
 *
 * @date: 2017/12/27 下午4:25
 * @author: ironc
 * @version: 1.0
 */
public class CustomerCreateVO {

    private String customerName;

    private String customerRemark;

    private String customerUrl;

    private String customerCode;

    private int appId;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public String getCustomerUrl() {
        return customerUrl;
    }

    public void setCustomerUrl(String customerUrl) {
        this.customerUrl = customerUrl;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

}
