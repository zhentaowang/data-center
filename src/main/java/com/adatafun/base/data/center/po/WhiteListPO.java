package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;

/**
 * Created by tiecheng on 2018/1/22.
 */
public class WhiteListPO extends BaseEntity {

    private Long whiteId;

    private String ip;

    public Long getWhiteId() {
        return whiteId;
    }

    public void setWhiteId(Long whiteId) {
        this.whiteId = whiteId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
