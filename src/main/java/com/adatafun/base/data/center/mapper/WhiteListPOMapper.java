package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.po.BlackListPO;
import com.adatafun.base.data.center.po.WhiteListPO;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/22.
 */
public interface WhiteListPOMapper {

    int insertOne(WhiteListPO whiteListPO);

    int insertBatch(List<WhiteListPO> whiteListPOS);

    WhiteListPO findBlack();

    List<WhiteListPO> findWhiteList();

}
