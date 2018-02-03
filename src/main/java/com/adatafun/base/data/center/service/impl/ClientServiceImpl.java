package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.mapper.CustomerPOMapper;
import com.adatafun.base.data.center.po.CustomerPO;
import com.adatafun.base.data.center.pojo.WebHook;
import com.adatafun.base.data.center.service.ClientServiceInterface;
import com.adatafun.base.data.center.util.DingDingUtils;
import com.adatafun.base.data.center.util.StringUtils;
import com.adatafun.base.data.center.vo.CustomerCreateVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.adatafun.base.data.center.util.DingDingUtils.send;

/**
 * @date: 2017/12/28 下午3:46
 * @author: ironc
 * @version: 1.0
 */
@Transactional
@Service
public class ClientServiceImpl implements ClientServiceInterface {

    @Autowired
    private CustomerPOMapper customerPOMapper;

    @Override
    public String createClient() {
        try {
            CustomerPO customerPO = new CustomerPO();
            customerPO.setCustomerCode(StringUtils.getNumAndLetterCode(18));
            String numCode = StringUtils.getNumCode(6);
            customerPO.setAppId(Integer.parseInt(numCode));
            customerPO.setCustomerName("test-" + numCode);
            customerPOMapper.insertOne(customerPO);
            if (customerPO.getCustomerId() != null) {
                CustomerCreateVO customerCreateVO = new CustomerCreateVO();
                customerCreateVO.setAppId(customerPO.getAppId());
                customerCreateVO.setCustomerCode(customerPO.getCustomerCode());
                customerCreateVO.setCustomerUrl(customerPO.getCustomerUrl());
                customerCreateVO.setCustomerName(customerPO.getCustomerName());
                return JSON.toJSONString(customerCreateVO, SerializerFeature.WriteMapNullValue);
            }
        } catch (Exception e) {
            WebHook webHook = DingDingUtils.createWebHook(
                    Dictionary.DING_DING_URL,
                    e.getMessage(),
                    null, false, "text");
            send(webHook);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String destroyClient(Long clientId) {
        return null;
    }

    @Override
    public boolean forbidClient(String sysCode) {
        return false;
    }

    @Override
    public boolean forbidClient(Long clientId) {
        return false;
    }

    @Override
    public CustomerPO queryByAppId(Integer appId) {
        return customerPOMapper.selectByAppId(appId);
    }

}
