package com.adatafun.base.data.center.service;

import com.adatafun.base.data.center.common.PageEntity;
import com.adatafun.base.data.center.po.DataCenterApiPO;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/5.
 */
public interface FeeyoInfoServiceInterface {

    /**
     * 查询本日推送接口信息
     * @return
     */
    PageEntity<DataCenterApiPO> queryUpdateApiToday();

    /**
     * 查询单条推送记录
     * @param id
     * @return
     */
    DataCenterApiPO queryUpdateApiOneToday(Integer id);

}
