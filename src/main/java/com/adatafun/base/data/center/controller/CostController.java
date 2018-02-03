package com.adatafun.base.data.center.controller;

import com.adatafun.base.data.center.common.ResultUtils;
import com.adatafun.base.data.center.dto.FeeyoCostDTO;
import com.adatafun.base.data.center.po.CustomerPO;
import com.adatafun.base.data.center.service.ClientServiceInterface;
import com.adatafun.base.data.center.service.CostServiceInterface;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tiecheng on 2018/1/5.
 */
@RestController
@RequestMapping("/common")
public class CostController {

    @Autowired
    private ClientServiceInterface clientServiceInterface;

    @Autowired
    private CostServiceInterface commonServiceInterface;

    /**
     * 查询飞常准费用
     *
     * @return
     */
    @GetMapping(path = "/cost", produces = {"application/json"})
    public String feeyoCost(FeeyoCostDTO feeyoCostDTO) {
        return JSON.toJSONString(
                ResultUtils.successResult(
                        String.valueOf(commonServiceInterface.queryPriceByCustomerForTime(feeyoCostDTO))));
    }

    /**
     * 查询客户的消费
     *
     * @param appId
     * @return
     */
    @GetMapping(path = "/cost/{appId}", produces = {"application/json"})
    public String feeyoCost(@PathVariable("appId") Integer appId) {
        CustomerPO customerPO = clientServiceInterface.queryByAppId(appId);
        return JSON.toJSONString(
                ResultUtils.successResult(
                        String.valueOf(commonServiceInterface.queryPriceByCustomer(customerPO))));
    }

}
