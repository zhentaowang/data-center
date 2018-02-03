package com.adatafun.base.data.center.service;

import com.adatafun.base.data.center.po.BlackListPO;
import com.adatafun.base.data.center.po.WhiteListPO;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/22.
 */
public interface WhiteAndBlackListServiceInterface {

    List<WhiteListPO> queryWhiteLists();

    List<BlackListPO> queryBlackLists();

    long createWhiteList(WhiteListPO whiteListPO);

    long createBlcakList(BlackListPO blackListPO);

    long deleteWhiteList(Long whiteId);

    long deleteBlcakList(Long blackId);

    void freshWhiteAndBlackLists();

}
