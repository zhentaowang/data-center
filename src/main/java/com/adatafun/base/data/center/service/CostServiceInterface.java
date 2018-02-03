package com.adatafun.base.data.center.service;

import com.adatafun.base.data.center.dto.FeeyoCostDTO;
import com.adatafun.base.data.center.po.CustomerPO;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import com.adatafun.base.data.center.po.SourceApiPO;

/**
 * Created by tiecheng on 2018/1/5.
 */
public interface CostServiceInterface {

    int queryPriceByCustomer(CustomerPO customerPO);

    DataCenterApiPO queryPriceByCustomerForTime(FeeyoCostDTO feeyoCostDTO);

    SourceApiPO queryByCondition();

}
