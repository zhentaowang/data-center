package com.adatafun.base.data.center.controller;

import com.adatafun.base.data.center.common.ResultUtils;
import com.adatafun.base.data.center.service.ClientServiceInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端
 *
 * @date: 2017/12/26 下午7:42
 * @author: ironc
 * @version: 1.0
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientServiceInterface clientService;

    @GetMapping(path = "/create", produces = {"application/json"})
    public String createClient(){
        String result = clientService.createClient();
        if (result == null){
            result = "创建失败";
            return JSON.toJSONString(ResultUtils.errorResult(result));
        }
        return JSON.toJSONString(ResultUtils.successResult(result), SerializerFeature.WriteMapNullValue);
    }

    @GetMapping(path = "/forbidden", produces = {"application/json"})
    public String forbiddenClient(@RequestParam("code") String code){
        boolean result = clientService.forbidClient(code);
        if (result){
            return JSON.toJSONString(ResultUtils.successResult("禁用成功"));
        }
        return JSON.toJSONString(ResultUtils.errorResult("禁用失败"));
    }

}
