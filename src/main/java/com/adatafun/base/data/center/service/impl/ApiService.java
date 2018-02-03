package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.common.PageEntity;
import com.adatafun.base.data.center.mapper.DataCenterApiPOMapper;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import com.adatafun.base.data.center.service.ApiServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/9.
 */
@Service
public class ApiService implements ApiServiceInterface {

    @Autowired
    private DataCenterApiPOMapper dataCenterApiPOMapper;

    @Override
    public List<DataCenterApiPO> findAll(DataCenterApiPO dataCenterApiPO, int page, int rows) {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPage(page);
        pageEntity.setRows(rows);
        List<DataCenterApiPO> dataCenterApiPOS = dataCenterApiPOMapper.findDataCenterApiPOSByConditionAndPage(dataCenterApiPO, pageEntity);
        pageEntity.setList(dataCenterApiPOS);
        int count = dataCenterApiPOMapper.countDataCenterApiPOSByCondition(dataCenterApiPO);
        pageEntity.setTotal(count);
        return dataCenterApiPOS;
    }

}
