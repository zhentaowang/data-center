package com.adatafun.base.data.center.service;

import com.adatafun.base.data.center.po.CustomerPO;

/**
 *
 *
 * @date: 2017/12/28 下午3:45
 * @author: ironc
 * @version: 1.0
 */
public interface ClientServiceInterface {

    /**
     * 创建客户
     *
     * @return
     */
    String createClient();

    /**
     * 销毁客户信息 软删除
     *
     * @param clientId 客户ID
     * @return
     */
    String destroyClient(Long clientId);

    /**
     * 禁止客户信息
     *
     * @param sysCode 系统码
     * @return
     */
    boolean forbidClient(String sysCode);

    /**
     * 禁止客户信息
     *
     * @param clientId 客户ID
     * @return
     */
    boolean forbidClient(Long clientId);

    /**
     * 禁止客户信息
     *
     * @param AppId APPID
     * @return
     */
    CustomerPO queryByAppId(Integer AppId);

}
