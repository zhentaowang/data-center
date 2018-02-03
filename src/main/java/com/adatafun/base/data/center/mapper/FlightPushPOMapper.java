package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.po.FlightPushPO;

public interface FlightPushPOMapper {
    int deleteByPrimaryKey(Long flightPushId);

    int insert(FlightPushPO record);

    int insertSelective(FlightPushPO record);

    FlightPushPO selectByPrimaryKey(Long flightPushId);

    int updateByPrimaryKeySelective(FlightPushPO record);

    int updateByPrimaryKey(FlightPushPO record);

    /**
     * 插入单条数据
     * @param flightPushPO
     * @return
     */
    int insertOne(FlightPushPO flightPushPO);
}