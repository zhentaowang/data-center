package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.common.Result;
import com.adatafun.base.data.center.dto.FeeyoCostDTO;
import com.adatafun.base.data.center.mapper.DataCenterApiPOMapper;
import com.adatafun.base.data.center.po.CustomerPO;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import com.adatafun.base.data.center.po.SourceApiPO;
import com.adatafun.base.data.center.service.CostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/5.
 */
@Service
public class CostServiceImpl implements CostServiceInterface {

    private static final Integer CUSTOM_PRICE = 500;

    private static final Integer QUERY_PRICE = 100;

    @Autowired
    private DataCenterApiPOMapper dataCenterApiPOMapper;

    @Override
    public int queryPriceByCustomer(CustomerPO customerPO) {
        int price = 0;
        List<DataCenterApiPO> dataCenterApiPOS = dataCenterApiPOMapper.selectByCustomerCode(customerPO.getCustomerCode());
        for (DataCenterApiPO dataCenterApiPO : dataCenterApiPOS) {
            if (dataCenterApiPO.getApiName().indexOf("/flight/flightInfo") > 0 && dataCenterApiPO.getInvokeState().equals(Result.STATUS.SUCCESS.getStatus())) {
                price += QUERY_PRICE;
            }
            if (dataCenterApiPO.getApiName().indexOf("/flight/custom") > 0 && dataCenterApiPO.getInvokeState().equals(Result.STATUS.CUSTOM_SUCCESS.getStatus())) {
                price += CUSTOM_PRICE;
            }
        }
        return price;
    }

    @Override
    public DataCenterApiPO queryPriceByCustomerForTime(FeeyoCostDTO feeyoCostDTO) {
        // 查询当月
        if (feeyoCostDTO.getMonth() != null && feeyoCostDTO.getMonth() >= 1 && feeyoCostDTO.getMonth() <= 12) {

        }
        // 查询本月某一天
        if (feeyoCostDTO.getDay() != null && feeyoCostDTO.getMonth() >= 1 && feeyoCostDTO.getMonth() <= 12) {

        }
        if (feeyoCostDTO.getStartTime() != null && feeyoCostDTO.getEndTime() != null) {

        }
        return null;
    }

    @Override
    public SourceApiPO queryByCondition() {
        return null;
    }

}
