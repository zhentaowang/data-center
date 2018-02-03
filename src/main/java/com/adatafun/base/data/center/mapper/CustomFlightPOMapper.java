package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.dto.FlightFeeyoCustomDTO;
import com.adatafun.base.data.center.po.CustomFlightPO;
import com.adatafun.base.data.center.po.CustomerPO;

import java.util.List;

public interface CustomFlightPOMapper {
    int deleteByPrimaryKey(Long customFlightId);

    int insert(CustomFlightPO record);

    int insertSelective(CustomFlightPO record);

    CustomFlightPO selectByPrimaryKey(Long customFlightId);

    int updateByPrimaryKeySelective(CustomFlightPO record);

    int updateByPrimaryKey(CustomFlightPO record);

    /**
     * 根据四个标识查询
     * @param flightFeeyoCustomDTO
     * @return
     */
    List<CustomFlightPO> selectByFourParams(FlightFeeyoCustomDTO flightFeeyoCustomDTO);

    /**
     * 插入单条数据
     * @param customFlightPO
     * @return
     */
    int insertOne(CustomFlightPO customFlightPO);

    /**
     * 根据四个参数和客户ID查询
     * @param customFlightPO
     * @return
     */
    CustomFlightPO selectByFourParamsAndCustomerId(CustomFlightPO customFlightPO);
}