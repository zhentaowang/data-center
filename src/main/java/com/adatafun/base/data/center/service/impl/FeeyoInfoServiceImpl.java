package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.common.PageEntity;
import com.adatafun.base.data.center.mapper.DataCenterApiPOMapper;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import com.adatafun.base.data.center.service.FeeyoInfoServiceInterface;
import com.adatafun.base.data.center.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by tiecheng on 2018/1/5.
 */
@Service
public class FeeyoInfoServiceImpl implements FeeyoInfoServiceInterface{

    @Autowired
    private DataCenterApiPOMapper dataCenterApiPOMapper;

    @Override
    public PageEntity<DataCenterApiPO> queryUpdateApiToday() {
        PageEntity<DataCenterApiPO> poPageEntity = new PageEntity<>();
        try {
            Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);
            Date nextDate = DateUtils.addDay(date, 1);
            List<DataCenterApiPO> dataCenterApiPOS = dataCenterApiPOMapper.selectMethodName("update", date ,nextDate, poPageEntity);
            int count = dataCenterApiPOMapper.countMethodName("update", date, nextDate);
            poPageEntity.setList(dataCenterApiPOS);
            poPageEntity.setTotal(count);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return poPageEntity;
    }

    @Override
    public DataCenterApiPO queryUpdateApiOneToday(Integer id) {
        return null;
    }

}
