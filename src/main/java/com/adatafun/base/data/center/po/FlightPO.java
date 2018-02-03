package com.adatafun.base.data.center.po;

import com.adatafun.base.data.center.common.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @table_name: base_flight.java
 * @date: 2017-12-28 14:10:23
 * @author: tiecheng
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class FlightPO extends BaseEntity {
    /**
     * 主键自增id
     */
    private Long flightId;

    /**
     * 更新标识字段
     */
    private String updateFlag;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 航班属性(1：国内-国内;2：国内-国际;3：国内-地区;4：地区-国际;5：国际-国际;6：未知)
     */
    private Short flightCategory;

    /**
     * 航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)
     */
    private String flightState;

    /**
     * 是否 经停 （0：经停；1：不经停；默认1）
     */
    private Short isStop;

    /**
     * 是否 共享 （0：共享；1：不共享；默认是1）
     */
    private Short isShare;

    /**
     * 机型
     */
    private String flightType;

    /**
     * 机号
     */
    private String planNo;

    /**
     * 共享航班号
     */
    private String shareFlightNo;

    /**
     * 补班 航班号 （取消 的航班才有此字段）
     */
    private String fillFlightNo;

    /**
     * 航班机位（1：远机位；2：近机位；3：未知）
     */
    private Short isNearOrFar;

    /**
     * 航段标识（1：计划 航段；2：备降而产生的航段；3：返航而产生的航段）
     */
    private Short legFlag;

    /**
     * 飞行距离
     */
    private String distance;

    /**
     * 准点率
     */
    private Integer ontimeRate;

    /**
     * 登机口
     */
    private String boardGate;

    /**
     * 登机时间
     */
    private Date boardTime;

    /**
     * 乘机状态（1：开始值机，2：值机结束，3：开始登机，4：催促登机，5：登机结束，6：未知）
     */
    private String boardState;

    /**
     * 中转机场
     */
    private String transferAirport;

    /**
     * 中转机场英文名
     */
    private String transferAirportCodeEnName;

    /**
     * 中转机场三字码
     */
    private String transferAirportCode;

    /**
     * 备降信息节点
     */
    private String alternateInfo;

    /**
     * 停机位
     */
    private String gatePosition;

    /**
     * 是否定制（0：定制；1：未定制）
     */
    private Short isCustom;

    /**
     * 出发机场三字码
     */
    private String depAirportCode;

    /**
     * 出发机场
     */
    private String depAirport;

    /**
     * 出发机场名
     */
    private String depAirportName;

    /**
     * 出发日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date depDate;

    /**
     * 计划起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depScheduledDate;

    /**
     * 预计起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depEstimatedDate;

    /**
     * 实际起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depActualDate;

    /**
     * 出发地时区
     */
    private String depTimeZone;

    /**
     * 出发城市名
     */
    private String depCity;

    /**
     * 出发城市英文
     */
    private String depCityEnglishName;

    /**
     * 出发机场候机楼
     */
    private String depTerminal;

    /**
     * 出发口
     */
    private String depGate;

    /**
     * 值机柜台
     */
    private String checkInCounter;

    /**
     * 到达机场三字码
     */
    private String arrAirportCode;

    /**
     * 到达机场
     */
    private String arrAirport;

    /**
     * 到达机场名
     */
    private String arrAirportName;

    /**
     * 到达日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date arrDate;

    /**
     * 计划到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date arrScheduledDate;

    /**
     * 预计到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date arrEstimatedDate;

    /**
     * 实际到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date arrActualDate;

    /**
     * 到达地时区
     */
    private String arrTimeZone;

    /**
     * 到达城市
     */
    private String arrCity;

    /**
     * 到达城市英文
     */
    private String arrCityEnglishName;

    /**
     * 到达地机场候机楼（接机楼）
     */
    private String arrTerminal;

    /**
     * 到达口
     */
    private String arrGate;

    /**
     * 行李盘
     */
    private String baggageCarousel;

    /**
     * 航空公司中文名称
     */
    private String airlineChineseName;

    /**
     * 航空公司英语名
     */
    private String airlineEnglishName;

    /**
     * 航空公司编号
     */
    private String airlineCode;

    /**
     * 主键自增id
     * @return flight_id 主键自增id
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * 主键自增id
     * @param flightId 主键自增id
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * 更新标识字段
     * @return update_flag 更新标识字段
     */
    public String getUpdateFlag() {
        return updateFlag;
    }

    /**
     * 更新标识字段
     * @param updateFlag 更新标识字段
     */
    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    /**
     * 航班号
     * @return flight_no 航班号
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 航班号
     * @param flightNo 航班号
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 航班属性(1：国内-国内;2：国内-国际;3：国内-地区;4：地区-国际;5：国际-国际;6：未知)
     * @return flight_category 航班属性(1：国内-国内;2：国内-国际;3：国内-地区;4：地区-国际;5：国际-国际;6：未知)
     */
    public Short getFlightCategory() {
        return flightCategory;
    }

    /**
     * 航班属性(1：国内-国内;2：国内-国际;3：国内-地区;4：地区-国际;5：国际-国际;6：未知)
     * @param flightCategory 航班属性(1：国内-国内;2：国内-国际;3：国内-地区;4：地区-国际;5：国际-国际;6：未知)
     */
    public void setFlightCategory(Short flightCategory) {
        this.flightCategory = flightCategory;
    }

    /**
     * 航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)
     * @return flight_state 航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)
     */
    public String getFlightState() {
        return flightState;
    }

    /**
     * 航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)
     * @param flightState 航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)
     */
    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    /**
     * 是否 经停 （0：经停；1：不经停；默认1）
     * @return is_stop 是否 经停 （0：经停；1：不经停；默认1）
     */
    public Short getIsStop() {
        return isStop;
    }

    /**
     * 是否 经停 （0：经停；1：不经停；默认1）
     * @param isStop 是否 经停 （0：经停；1：不经停；默认1）
     */
    public void setIsStop(Short isStop) {
        this.isStop = isStop;
    }

    /**
     * 是否 共享 （0：共享；1：不共享；默认是1）
     * @return is_share 是否 共享 （0：共享；1：不共享；默认是1）
     */
    public Short getIsShare() {
        return isShare;
    }

    /**
     * 是否 共享 （0：共享；1：不共享；默认是1）
     * @param isShare 是否 共享 （0：共享；1：不共享；默认是1）
     */
    public void setIsShare(Short isShare) {
        this.isShare = isShare;
    }

    /**
     * 机型
     * @return flight_type 机型
     */
    public String getFlightType() {
        return flightType;
    }

    /**
     * 机型
     * @param flightType 机型
     */
    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    /**
     * 机号
     * @return plan_no 机号
     */
    public String getPlanNo() {
        return planNo;
    }

    /**
     * 机号
     * @param planNo 机号
     */
    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    /**
     * 共享航班号
     * @return share_flight_no 共享航班号
     */
    public String getShareFlightNo() {
        return shareFlightNo;
    }

    /**
     * 共享航班号
     * @param shareFlightNo 共享航班号
     */
    public void setShareFlightNo(String shareFlightNo) {
        this.shareFlightNo = shareFlightNo;
    }

    /**
     * 补班 航班号 （取消 的航班才有此字段）
     * @return fill_flight_no 补班 航班号 （取消 的航班才有此字段）
     */
    public String getFillFlightNo() {
        return fillFlightNo;
    }

    /**
     * 补班 航班号 （取消 的航班才有此字段）
     * @param fillFlightNo 补班 航班号 （取消 的航班才有此字段）
     */
    public void setFillFlightNo(String fillFlightNo) {
        this.fillFlightNo = fillFlightNo;
    }

    /**
     * 航班机位（1：远机位；2：近机位；3：未知）
     * @return is_near_or_far 航班机位（1：远机位；2：近机位；3：未知）
     */
    public Short getIsNearOrFar() {
        return isNearOrFar;
    }

    /**
     * 航班机位（1：远机位；2：近机位；3：未知）
     * @param isNearOrFar 航班机位（1：远机位；2：近机位；3：未知）
     */
    public void setIsNearOrFar(Short isNearOrFar) {
        this.isNearOrFar = isNearOrFar;
    }

    /**
     * 航段标识（1：计划 航段；2：备降而产生的航段；3：返航而产生的航段）
     * @return leg_flag 航段标识（1：计划 航段；2：备降而产生的航段；3：返航而产生的航段）
     */
    public Short getLegFlag() {
        return legFlag;
    }

    /**
     * 航段标识（1：计划 航段；2：备降而产生的航段；3：返航而产生的航段）
     * @param legFlag 航段标识（1：计划 航段；2：备降而产生的航段；3：返航而产生的航段）
     */
    public void setLegFlag(Short legFlag) {
        this.legFlag = legFlag;
    }

    /**
     * 飞行距离
     * @return distance 飞行距离
     */
    public String getDistance() {
        return distance;
    }

    /**
     * 飞行距离
     * @param distance 飞行距离
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * 准点率
     * @return ontime_rate 准点率
     */
    public Integer getOntimeRate() {
        return ontimeRate;
    }

    /**
     * 准点率
     * @param ontimeRate 准点率
     */
    public void setOntimeRate(Integer ontimeRate) {
        this.ontimeRate = ontimeRate;
    }

    /**
     * 登机口
     * @return board_gate 登机口
     */
    public String getBoardGate() {
        return boardGate;
    }

    /**
     * 登机口
     * @param boardGate 登机口
     */
    public void setBoardGate(String boardGate) {
        this.boardGate = boardGate;
    }

    /**
     * 登机时间
     * @return board_time 登机时间
     */
    public Date getBoardTime() {
        return boardTime;
    }

    /**
     * 登机时间
     * @param boardTime 登机时间
     */
    public void setBoardTime(Date boardTime) {
        this.boardTime = boardTime;
    }

    /**
     * 乘机状态（1：开始值机，2：值机结束，3：开始登机，4：催促登机，5：登机结束，6：未知）
     * @return board_state 乘机状态（1：开始值机，2：值机结束，3：开始登机，4：催促登机，5：登机结束，6：未知）
     */
    public String getBoardState() {
        return boardState;
    }

    /**
     * 乘机状态（1：开始值机，2：值机结束，3：开始登机，4：催促登机，5：登机结束，6：未知）
     * @param boardState 乘机状态（1：开始值机，2：值机结束，3：开始登机，4：催促登机，5：登机结束，6：未知）
     */
    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    /**
     * 中转机场
     * @return transfer_airport 中转机场
     */
    public String getTransferAirport() {
        return transferAirport;
    }

    /**
     * 中转机场
     * @param transferAirport 中转机场
     */
    public void setTransferAirport(String transferAirport) {
        this.transferAirport = transferAirport;
    }

    /**
     * 中转机场英文名
     * @return transfer_airport_code_en_name 中转机场英文名
     */
    public String getTransferAirportCodeEnName() {
        return transferAirportCodeEnName;
    }

    /**
     * 中转机场英文名
     * @param transferAirportCodeEnName 中转机场英文名
     */
    public void setTransferAirportCodeEnName(String transferAirportCodeEnName) {
        this.transferAirportCodeEnName = transferAirportCodeEnName;
    }

    /**
     * 中转机场三字码
     * @return transfer_airport_code 中转机场三字码
     */
    public String getTransferAirportCode() {
        return transferAirportCode;
    }

    /**
     * 中转机场三字码
     * @param transferAirportCode 中转机场三字码
     */
    public void setTransferAirportCode(String transferAirportCode) {
        this.transferAirportCode = transferAirportCode;
    }

    /**
     * 备降信息节点
     * @return alternate_info 备降信息节点
     */
    public String getAlternateInfo() {
        return alternateInfo;
    }

    /**
     * 备降信息节点
     * @param alternateInfo 备降信息节点
     */
    public void setAlternateInfo(String alternateInfo) {
        this.alternateInfo = alternateInfo;
    }

    /**
     * 停机位
     * @return gate_position 停机位
     */
    public String getGatePosition() {
        return gatePosition;
    }

    /**
     * 停机位
     * @param gatePosition 停机位
     */
    public void setGatePosition(String gatePosition) {
        this.gatePosition = gatePosition;
    }

    /**
     * 是否定制（0：定制；1：未定制）
     * @return is_custom 是否定制（0：定制；1：未定制）
     */
    public Short getIsCustom() {
        return isCustom;
    }

    /**
     * 是否定制（0：定制；1：未定制）
     * @param isCustom 是否定制（0：定制；1：未定制）
     */
    public void setIsCustom(Short isCustom) {
        this.isCustom = isCustom;
    }

    /**
     * 出发机场三字码
     * @return dep_airport_code 出发机场三字码
     */
    public String getDepAirportCode() {
        return depAirportCode;
    }

    /**
     * 出发机场三字码
     * @param depAirportCode 出发机场三字码
     */
    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    /**
     * 出发机场
     * @return dep_airport 出发机场
     */
    public String getDepAirport() {
        return depAirport;
    }

    /**
     * 出发机场
     * @param depAirport 出发机场
     */
    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport;
    }

    /**
     * 出发机场名
     * @return dep_airport_name 出发机场名
     */
    public String getDepAirportName() {
        return depAirportName;
    }

    /**
     * 出发机场名
     * @param depAirportName 出发机场名
     */
    public void setDepAirportName(String depAirportName) {
        this.depAirportName = depAirportName;
    }

    /**
     * 出发日期
     * @return dep_date 出发日期
     */
    public Date getDepDate() {
        return depDate;
    }

    /**
     * 出发日期
     * @param depDate 出发日期
     */
    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    /**
     * 计划起飞时间（yyyy-MM-dd HH-mm-ss格式）
     * @return dep_scheduled_date 计划起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public Date getDepScheduledDate() {
        return depScheduledDate;
    }

    /**
     * 计划起飞时间（yyyy-MM-dd HH-mm-ss格式）
     * @param depScheduledDate 计划起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public void setDepScheduledDate(Date depScheduledDate) {
        this.depScheduledDate = depScheduledDate;
    }

    /**
     * 预计起飞时间（yyyy-MM-dd HH-mm-ss格式）
     * @return dep_estimated_date 预计起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public Date getDepEstimatedDate() {
        return depEstimatedDate;
    }

    /**
     * 预计起飞时间（yyyy-MM-dd HH-mm-ss格式）
     * @param depEstimatedDate 预计起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public void setDepEstimatedDate(Date depEstimatedDate) {
        this.depEstimatedDate = depEstimatedDate;
    }

    /**
     * 实际起飞时间（yyyy-MM-dd HH-mm-ss格式）
     * @return dep_actual_date 实际起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public Date getDepActualDate() {
        return depActualDate;
    }

    /**
     * 实际起飞时间（yyyy-MM-dd HH-mm-ss格式）
     * @param depActualDate 实际起飞时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public void setDepActualDate(Date depActualDate) {
        this.depActualDate = depActualDate;
    }

    /**
     * 出发地时区
     * @return dep_time_zone 出发地时区
     */
    public String getDepTimeZone() {
        return depTimeZone;
    }

    /**
     * 出发地时区
     * @param depTimeZone 出发地时区
     */
    public void setDepTimeZone(String depTimeZone) {
        this.depTimeZone = depTimeZone;
    }

    /**
     * 出发城市名
     * @return dep_city 出发城市名
     */
    public String getDepCity() {
        return depCity;
    }

    /**
     * 出发城市名
     * @param depCity 出发城市名
     */
    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    /**
     * 出发城市英文
     * @return dep_city_english_name 出发城市英文
     */
    public String getDepCityEnglishName() {
        return depCityEnglishName;
    }

    /**
     * 出发城市英文
     * @param depCityEnglishName 出发城市英文
     */
    public void setDepCityEnglishName(String depCityEnglishName) {
        this.depCityEnglishName = depCityEnglishName;
    }

    /**
     * 出发机场候机楼
     * @return dep_terminal 出发机场候机楼
     */
    public String getDepTerminal() {
        return depTerminal;
    }

    /**
     * 出发机场候机楼
     * @param depTerminal 出发机场候机楼
     */
    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    /**
     * 出发口
     * @return dep_gate 出发口
     */
    public String getDepGate() {
        return depGate;
    }

    /**
     * 出发口
     * @param depGate 出发口
     */
    public void setDepGate(String depGate) {
        this.depGate = depGate;
    }

    /**
     * 值机柜台
     * @return check_in_counter 值机柜台
     */
    public String getCheckInCounter() {
        return checkInCounter;
    }

    /**
     * 值机柜台
     * @param checkInCounter 值机柜台
     */
    public void setCheckInCounter(String checkInCounter) {
        this.checkInCounter = checkInCounter;
    }

    /**
     * 到达机场三字码
     * @return arr_airport_code 到达机场三字码
     */
    public String getArrAirportCode() {
        return arrAirportCode;
    }

    /**
     * 到达机场三字码
     * @param arrAirportCode 到达机场三字码
     */
    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    /**
     * 到达机场
     * @return arr_airport 到达机场
     */
    public String getArrAirport() {
        return arrAirport;
    }

    /**
     * 到达机场
     * @param arrAirport 到达机场
     */
    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport;
    }

    /**
     * 到达机场名
     * @return arr_airport_name 到达机场名
     */
    public String getArrAirportName() {
        return arrAirportName;
    }

    /**
     * 到达机场名
     * @param arrAirportName 到达机场名
     */
    public void setArrAirportName(String arrAirportName) {
        this.arrAirportName = arrAirportName;
    }

    /**
     * 到达日期
     * @return arr_date 到达日期
     */
    public Date getArrDate() {
        return arrDate;
    }

    /**
     * 到达日期
     * @param arrDate 到达日期
     */
    public void setArrDate(Date arrDate) {
        this.arrDate = arrDate;
    }

    /**
     * 计划到达时间（yyyy-MM-dd HH-mm-ss格式）
     * @return arr_scheduled_date 计划到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public Date getArrScheduledDate() {
        return arrScheduledDate;
    }

    /**
     * 计划到达时间（yyyy-MM-dd HH-mm-ss格式）
     * @param arrScheduledDate 计划到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public void setArrScheduledDate(Date arrScheduledDate) {
        this.arrScheduledDate = arrScheduledDate;
    }

    /**
     * 预计到达时间（yyyy-MM-dd HH-mm-ss格式）
     * @return arr_estimated_date 预计到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public Date getArrEstimatedDate() {
        return arrEstimatedDate;
    }

    /**
     * 预计到达时间（yyyy-MM-dd HH-mm-ss格式）
     * @param arrEstimatedDate 预计到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public void setArrEstimatedDate(Date arrEstimatedDate) {
        this.arrEstimatedDate = arrEstimatedDate;
    }

    /**
     * 实际到达时间（yyyy-MM-dd HH-mm-ss格式）
     * @return arr_actual_date 实际到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public Date getArrActualDate() {
        return arrActualDate;
    }

    /**
     * 实际到达时间（yyyy-MM-dd HH-mm-ss格式）
     * @param arrActualDate 实际到达时间（yyyy-MM-dd HH-mm-ss格式）
     */
    public void setArrActualDate(Date arrActualDate) {
        this.arrActualDate = arrActualDate;
    }

    /**
     * 到达地时区
     * @return arr_time_zone 到达地时区
     */
    public String getArrTimeZone() {
        return arrTimeZone;
    }

    /**
     * 到达地时区
     * @param arrTimeZone 到达地时区
     */
    public void setArrTimeZone(String arrTimeZone) {
        this.arrTimeZone = arrTimeZone;
    }

    /**
     * 到达城市
     * @return arr_city 到达城市
     */
    public String getArrCity() {
        return arrCity;
    }

    /**
     * 到达城市
     * @param arrCity 到达城市
     */
    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    /**
     * 到达城市英文
     * @return arr_city_english_name 到达城市英文
     */
    public String getArrCityEnglishName() {
        return arrCityEnglishName;
    }

    /**
     * 到达城市英文
     * @param arrCityEnglishName 到达城市英文
     */
    public void setArrCityEnglishName(String arrCityEnglishName) {
        this.arrCityEnglishName = arrCityEnglishName;
    }

    /**
     * 到达地机场候机楼（接机楼）
     * @return arr_terminal 到达地机场候机楼（接机楼）
     */
    public String getArrTerminal() {
        return arrTerminal;
    }

    /**
     * 到达地机场候机楼（接机楼）
     * @param arrTerminal 到达地机场候机楼（接机楼）
     */
    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    /**
     * 到达口
     * @return arr_gate 到达口
     */
    public String getArrGate() {
        return arrGate;
    }

    /**
     * 到达口
     * @param arrGate 到达口
     */
    public void setArrGate(String arrGate) {
        this.arrGate = arrGate;
    }

    /**
     * 行李盘
     * @return baggage_carousel 行李盘
     */
    public String getBaggageCarousel() {
        return baggageCarousel;
    }

    /**
     * 行李盘
     * @param baggageCarousel 行李盘
     */
    public void setBaggageCarousel(String baggageCarousel) {
        this.baggageCarousel = baggageCarousel;
    }

    /**
     * 航空公司中文名称
     * @return airline_chinese_name 航空公司中文名称
     */
    public String getAirlineChineseName() {
        return airlineChineseName;
    }

    /**
     * 航空公司中文名称
     * @param airlineChineseName 航空公司中文名称
     */
    public void setAirlineChineseName(String airlineChineseName) {
        this.airlineChineseName = airlineChineseName;
    }

    /**
     * 航空公司英语名
     * @return airline_english_name 航空公司英语名
     */
    public String getAirlineEnglishName() {
        return airlineEnglishName;
    }

    /**
     * 航空公司英语名
     * @param airlineEnglishName 航空公司英语名
     */
    public void setAirlineEnglishName(String airlineEnglishName) {
        this.airlineEnglishName = airlineEnglishName;
    }

    /**
     * 航空公司编号
     * @return airline_code 航空公司编号
     */
    public String getAirlineCode() {
        return airlineCode;
    }

    /**
     * 航空公司编号
     * @param airlineCode 航空公司编号
     */
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }
}