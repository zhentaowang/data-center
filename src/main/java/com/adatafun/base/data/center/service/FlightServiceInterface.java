package com.adatafun.base.data.center.service;

import com.adatafun.base.data.center.dto.FlightFeeyoCustomDTO;
import com.adatafun.base.data.center.dto.FlightFeeyoDTO;
import com.adatafun.base.data.center.vo.FlightVO;

import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @date: 2017/12/28 下午3:28
 * @author: ironc
 * @version: 1.0
 */
public interface FlightServiceInterface {

    /**
     * 航班号查询
     *
     * @param depDate  出发日期（航班日期哦）
     * @param flightNo 航班号
     * @return
     */
    List<FlightVO> flightInfo(Date depDate, String flightNo);

    /**
     * 航段查询
     *
     * @param depDate 出发日期（航班日期哦）
     * @param depCode 出发机场三字码
     * @param arrCode 到达机场三字码
     * @return
     */
    List<FlightVO> flightInfo(Date depDate, String depCode, String arrCode);

    /**
     * 航班号查询
     * 非实时
     *
     * @param depDate  出发日期（航班日期哦）
     * @param flightNo 航班号
     * @return
     */
    List<FlightVO> flightInfoNotRealTime(Date depDate, String flightNo);

    /**
     * 航班信息查询
     *
     * @param depDate  出发日期（航班日期哦）
     * @param flightNo 航班号
     * @param depCode  出发机场三字码
     * @param arrCode  到达机场三字码
     * @return
     */
    List<FlightVO> flightInfo(Date depDate, String flightNo, String depCode, String arrCode);

    /**
     * 更新航班信息
     *
     * @param flightFeeyoCustomDTOList
     */
    void updateFlight(List<FlightFeeyoCustomDTO> flightFeeyoCustomDTOList);

    /**
     * 是否定制
     *
     * @param flightNo 航班号
     * @param depDate  出发日期（航班日期哦）
     * @param depCode  出发三字码
     * @param arrCode  到达三字码
     * @return
     */
    String isCustom(String flightNo, Date depDate, String depCode, String arrCode);

    /**
     * 定制航班
     *
     * @param flightNo 航班号
     * @param depDate  出发日期（航班日期哦）
     * @param depCode  出发三字码
     * @param arrCode  到达三字码
     * @return
     */
    boolean custom(String flightNo, Date depDate, String depCode, String arrCode);

    /**
     * 取消定制
     * 航班中心层面的取消，会导致所有第三方都无法收到推送信息
     *
     * @param customerId 客户端ID
     * @param flightNo 航班号
     * @param depDate  出发日期（航班日期哦）
     * @param depCode  出发三字码
     * @param arrCode  到达三字码
     * @return
     */
    String cancelCustom(Long customerId, String flightNo, Date depDate, String depCode, String arrCode);

}
