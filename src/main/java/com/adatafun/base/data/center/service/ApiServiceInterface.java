package com.adatafun.base.data.center.service;

import com.adatafun.base.data.center.common.PageEntity;
import com.adatafun.base.data.center.po.DataCenterApiPO;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/9.
 */
public interface ApiServiceInterface {

    List<DataCenterApiPO> findAll(DataCenterApiPO dataCenterApiPO, int page, int rows);

}
