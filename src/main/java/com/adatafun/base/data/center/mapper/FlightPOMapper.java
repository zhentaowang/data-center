package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.po.FlightPO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FlightPOMapper {
    int deleteByPrimaryKey(Long flightId);

    int insert(FlightPO record);

    int insertSelective(FlightPO record);

    FlightPO selectByPrimaryKey(Long flightId);

    int updateByPrimaryKeySelective(FlightPO record);

    int updateByPrimaryKey(FlightPO record);

    /**
     * 根据航班号和航班日期查询航班
     *
     * @param depDate
     * @param flightNo
     * @return
     */
    List<FlightPO> selectByDepDateAndFlightNo(@Param("depDate") Date depDate, @Param("flightNo") String flightNo);

    /**
     * 根据四个参数去更新
     *
     * @param flightPO
     */
    void updateByFourParams(FlightPO flightPO);

    /**
     * 根据四个参数去判断是否存在
     *
     * @param flightNo
     * @param depDate
     * @param depAirportCode
     * @param arrAirportCode
     * @return
     */
    int existByFourParams(@Param("flightNo") String flightNo, @Param("depDate") Date depDate,
                          @Param("depAirportCode") String depAirportCode, @Param("arrAirportCode") String arrAirportCode);

    /**
     * 根据航班日期和出发目的地三字码查询航班
     *
     * @param depDate
     * @param depCode
     * @param arrCode
     * @return
     */
    List<FlightPO> selectByDepDateAndDepCodeAndArrCode(@Param("depDate") Date depDate,
                                                       @Param("depAirportCode") String depCode, @Param("arrAirportCode") String arrCode);

    /**
     * 插入一条航班信息
     *
     * @param flightPO
     * @return
     */
    int insertOne(FlightPO flightPO);

    /**
     * 根据四个参数去查询航班信息
     *
     * @param flightNo
     * @param depDate
     * @param depCode
     * @param arrCode
     * @return
     */
    FlightPO selectByFourParams(@Param("flightNo") String flightNo, @Param("depDate") Date depDate,
                                @Param("depAirportCode") String depCode, @Param("arrAirportCode") String arrCode);

    /**
     * 根据四个参数去更新定制参数
     *
     * @param isCustom
     * @param flightNo
     * @param depDate
     * @param depCode
     * @param arrCode
     */
    void updateIsCustomByFourParams(@Param("updateTime") Date updateTime, @Param("isCustom") Short isCustom, @Param("flightNo") String flightNo, @Param("depDate") Date depDate,
                                    @Param("depAirportCode") String depCode, @Param("arrAirportCode") String arrCode);

    /**
     * @param flightNo
     * @param depDate
     * @return
     */
    List<FlightPO> selectByFlightNoAndDepDate(@Param("flightNo") String flightNo, @Param("depDate") Date depDate);

    /**
     * 插入或者更新
     *
     * @param flightPO
     */
    void insertOrUpdateByFourParams(FlightPO flightPO);

}