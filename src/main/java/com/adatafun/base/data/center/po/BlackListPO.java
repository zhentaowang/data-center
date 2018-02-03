package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;

/**
 * Created by tiecheng on 2018/1/22.
 */
public class BlackListPO extends BaseEntity {

    private Long blackId;

    private String ip;

    public Long getBlackId() {
        return blackId;
    }

    public void setBlackId(Long blackId) {
        this.blackId = blackId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
