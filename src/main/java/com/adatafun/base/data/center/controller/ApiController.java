package com.adatafun.base.data.center.controller;

import com.adatafun.base.data.center.dto.DataCenterApiQueryDTO;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import com.adatafun.base.data.center.service.ApiServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/9.
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiServiceInterface apiServiceInterface;

    @GetMapping
    public String dataCenterApi(DataCenterApiQueryDTO dataCenterApiQueryDTO, Model model) {
        DataCenterApiPO dataCenterApiPO = new DataCenterApiPO();
        dataCenterApiPO.setRequestParam(dataCenterApiQueryDTO.getRequestParam());
        dataCenterApiPO.setCustomerCode(dataCenterApiQueryDTO.getCustomerCode());
        dataCenterApiPO.setInvokeResult(dataCenterApiQueryDTO.getInvokeResult());
        dataCenterApiPO.setInvokeState(dataCenterApiQueryDTO.getInvokeState());
        dataCenterApiPO.setApiName(dataCenterApiQueryDTO.getApiName());
        List<DataCenterApiPO> dataCenterApiPOList = apiServiceInterface.findAll(dataCenterApiPO,
                dataCenterApiQueryDTO.getPage() == null ? 1 : dataCenterApiQueryDTO.getPage(),
                dataCenterApiQueryDTO.getRows() == null ? 10 : dataCenterApiQueryDTO.getRows());
        model.addAttribute("data", dataCenterApiPOList);
        return "api/data_center_api";
    }

}
