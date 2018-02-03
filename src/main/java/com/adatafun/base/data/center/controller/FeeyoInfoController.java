package com.adatafun.base.data.center.controller;

import com.adatafun.base.data.center.common.PageEntity;
import com.adatafun.base.data.center.common.ResultUtils;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import com.adatafun.base.data.center.service.FeeyoInfoServiceInterface;
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
@RequestMapping("feeyo")
public class FeeyoInfoController {

    @Autowired
    private FeeyoInfoServiceInterface feeyoInfoServiceInterface;

    /**
     * 查询飞常准当日推送信息接口信息
     *
     * @return
     */
    @GetMapping(path = "/updateApi/{id}", produces = {"application/json"})
    public String updateApi(@PathVariable(name = "id", required = false) Integer id) {
        if (id != null && id > 0) {
            DataCenterApiPO dataCenterApiPO = feeyoInfoServiceInterface.queryUpdateApiOneToday(id);
            return JSON.toJSONString(ResultUtils.successResult(dataCenterApiPO));
        }
        PageEntity<DataCenterApiPO> poPageEntity = feeyoInfoServiceInterface.queryUpdateApiToday();
        feeyoInfoServiceInterface.queryUpdateApiToday();
        return JSON.toJSONString(ResultUtils.successResult(poPageEntity));
    }

}
