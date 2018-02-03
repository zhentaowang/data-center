package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.po.SourceApiPO;

public interface SourceApiPOMapper {
    int deleteByPrimaryKey(Long sourceApiId);

    int insert(SourceApiPO record);

    int insertSelective(SourceApiPO record);

    SourceApiPO selectByPrimaryKey(Long sourceApiId);

    int updateByPrimaryKeySelective(SourceApiPO record);

    int updateByPrimaryKey(SourceApiPO record);

    /**
     * 插入一条记录
     * @param sourceApiPO
     * @return
     */
    int insertOne(SourceApiPO sourceApiPO);
}