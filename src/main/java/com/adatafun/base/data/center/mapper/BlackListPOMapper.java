package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.po.BlackListPO;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/22.
 */
public interface BlackListPOMapper {

    int insertOne(BlackListPO blackListPO);

    int insertBatch(List<BlackListPO> blackListPOS);

    BlackListPO findBlack();

    List<BlackListPO> findBlackList();

}
