package com.adatafun.base.data.center.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 航班信息展示对象
 *
 * @date: 2017/12/26 上午10:34
 * @author: ironc
 * @version: 1.0
 */
public class FlightVO {

    /**
     * 更新标识
     * 默认0
     */
    private String updateFlag;

    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 航班属性
     * （1：国内-国内;2：国内-国际;3：国内-地区;4：地区-国际;5：国际-国际;6：未知）
     * 默认是未知
     */
    private Short flightCategory;

    /**
     * 航班状态
     * （计划、延误、取消、备降、返航、起飞、到达、备降起飞、备降取消、备降到达、返航起飞、返航取消、返航到达、提前取消）
     * 默认是计划
     */
    private String flightState;

    /**
     * 是否经停（0：经停；1：不经停；默认1）
     */
    private Short isStop;

    /**
     * 是否共享（0：共享；1：不共享；默认是1）
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
     * 补班航班号
     * 航班号为补班的时候，航班号最尾一位数字，从9到0的九个数字9876543210分别对应QRSTUVWXYZ
     */
    private String fillFlightNo;

    /**
     * 航班机位（1：远机位；2：近机位；3：未知）
     * 默认是未知
     */
    private Short isNearOrFar;

    /**
     * 航段标识
     * （1：计划 航段；2：备降而产生的航段；3：返航而产生的航段）
     * 默认计划
     */
    private Short legFlag;

    /**
     * 飞行距离
     */
    private String distance;

    /**
     * 准点率 （真实数据*100的结果）
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
     * 登机状态
     * （1：开始值机，2：值机结束，3：开始登机，4：催促登机，5：登机结束，6：未知）
     * 默认未知
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
     * 不能为空
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
     * 是否定制
     */
    @JSONField(serialize = false)
    private Short isCustom;

    // -=-=-=-=-=-=-=-=- 航班出发信息 -=-=-=-=-=-=-=-=-

    /**
     * 出发机场三字码
     */
    private String depAirportCode;

    /**
     * 出发机场简称
     */
    private String depAirport;

    /**
     * 出发机场名称（全）
     */
    private String depAirportName;

    /**
     * 出发日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date depDate;

    /**
     * 计划起飞时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depScheduledDate;

    /**
     * 预计起飞时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depEstimatedDate;

    /**
     * 实际起飞时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depActualDate;

    /**
     * 出发地时区
     */
    private String depTimeZone;

    /**
     * 出发城市
     */
    private String depCity;

    /**
     * 出发城市英文名
     */
    private String depCityEnglishName;

    /**
     * 候机楼
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

    // -=-=-=-=-=-=-=-=- 航班降落信息 -=-=-=-=-=-=-=-=-

    /**
     * 出发机场三字码
     */
    private String arrAirportCode;

    /**
     * 出发机场简称
     */
    private String arrAirport;

    /**
     * 出发机场名称（全）
     */
    private String arrAirportName;

    /**
     * 到达日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date arrDate;

    /**
     * 计划起飞时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date arrScheduledDate;

    /**
     * 预计起飞时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date arrEstimatedDate;

    /**
     * 实际起飞时间
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
     * 到达城市英文名
     */
    private String arrCityEnglishName;

    /**
     * 接机楼
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

    // -=-=-=-=-=-=-=-=- 航空公司信息 -=-=-=-=-=-=-=-=-

    /**
     * 航班公司中文名
     */
    private String airlineChineseName;

    /**
     * 航空公司英文名
     */
    private String airlineEnglishName;

    /**
     * 航空公司编码
     */
    private String airlineCode;

    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Short getFlightCategory() {
        return flightCategory;
    }

    public void setFlightCategory(Short flightCategory) {
        this.flightCategory = flightCategory;
    }

    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    public Short getIsStop() {
        return isStop;
    }

    public void setIsStop(Short isStop) {
        this.isStop = isStop;
    }

    public Short getIsShare() {
        return isShare;
    }

    public void setIsShare(Short isShare) {
        this.isShare = isShare;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getShareFlightNo() {
        return shareFlightNo;
    }

    public void setShareFlightNo(String shareFlightNo) {
        this.shareFlightNo = shareFlightNo;
    }

    public String getFillFlightNo() {
        return fillFlightNo;
    }

    public void setFillFlightNo(String fillFlightNo) {
        this.fillFlightNo = fillFlightNo;
    }

    public Short getIsNearOrFar() {
        return isNearOrFar;
    }

    public void setIsNearOrFar(Short isNearOrFar) {
        this.isNearOrFar = isNearOrFar;
    }

    public Short getLegFlag() {
        return legFlag;
    }

    public void setLegFlag(Short legFlag) {
        this.legFlag = legFlag;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Integer getOntimeRate() {
        return ontimeRate;
    }

    public void setOntimeRate(Integer ontimeRate) {
        this.ontimeRate = ontimeRate;
    }

    public String getBoardGate() {
        return boardGate;
    }

    public void setBoardGate(String boardGate) {
        this.boardGate = boardGate;
    }

    public Date getBoardTime() {
        return boardTime;
    }

    public void setBoardTime(Date boardTime) {
        this.boardTime = boardTime;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public String getTransferAirport() {
        return transferAirport;
    }

    public void setTransferAirport(String transferAirport) {
        this.transferAirport = transferAirport;
    }

    public String getTransferAirportCodeEnName() {
        return transferAirportCodeEnName;
    }

    public void setTransferAirportCodeEnName(String transferAirportCodeEnName) {
        this.transferAirportCodeEnName = transferAirportCodeEnName;
    }

    public String getTransferAirportCode() {
        return transferAirportCode;
    }

    public void setTransferAirportCode(String transferAirportCode) {
        this.transferAirportCode = transferAirportCode;
    }

    public String getAlternateInfo() {
        return alternateInfo;
    }

    public void setAlternateInfo(String alternateInfo) {
        this.alternateInfo = alternateInfo;
    }

    public String getGatePosition() {
        return gatePosition;
    }

    public void setGatePosition(String gatePosition) {
        this.gatePosition = gatePosition;
    }

    public Short getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Short isCustom) {
        this.isCustom = isCustom;
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    public String getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport;
    }

    public String getDepAirportName() {
        return depAirportName;
    }

    public void setDepAirportName(String depAirportName) {
        this.depAirportName = depAirportName;
    }

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    public Date getDepScheduledDate() {
        return depScheduledDate;
    }

    public void setDepScheduledDate(Date depScheduledDate) {
        this.depScheduledDate = depScheduledDate;
    }

    public Date getDepEstimatedDate() {
        return depEstimatedDate;
    }

    public void setDepEstimatedDate(Date depEstimatedDate) {
        this.depEstimatedDate = depEstimatedDate;
    }

    public Date getDepActualDate() {
        return depActualDate;
    }

    public void setDepActualDate(Date depActualDate) {
        this.depActualDate = depActualDate;
    }

    public String getDepTimeZone() {
        return depTimeZone;
    }

    public void setDepTimeZone(String depTimeZone) {
        this.depTimeZone = depTimeZone;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getDepCityEnglishName() {
        return depCityEnglishName;
    }

    public void setDepCityEnglishName(String depCityEnglishName) {
        this.depCityEnglishName = depCityEnglishName;
    }

    public String getDepTerminal() {
        return depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    public String getDepGate() {
        return depGate;
    }

    public void setDepGate(String depGate) {
        this.depGate = depGate;
    }

    public String getCheckInCounter() {
        return checkInCounter;
    }

    public void setCheckInCounter(String checkInCounter) {
        this.checkInCounter = checkInCounter;
    }

    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    public String getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport;
    }

    public String getArrAirportName() {
        return arrAirportName;
    }

    public void setArrAirportName(String arrAirportName) {
        this.arrAirportName = arrAirportName;
    }

    public Date getArrDate() {
        return arrDate;
    }

    public void setArrDate(Date arrDate) {
        this.arrDate = arrDate;
    }

    public Date getArrScheduledDate() {
        return arrScheduledDate;
    }

    public void setArrScheduledDate(Date arrScheduledDate) {
        this.arrScheduledDate = arrScheduledDate;
    }

    public Date getArrEstimatedDate() {
        return arrEstimatedDate;
    }

    public void setArrEstimatedDate(Date arrEstimatedDate) {
        this.arrEstimatedDate = arrEstimatedDate;
    }

    public Date getArrActualDate() {
        return arrActualDate;
    }

    public void setArrActualDate(Date arrActualDate) {
        this.arrActualDate = arrActualDate;
    }

    public String getArrTimeZone() {
        return arrTimeZone;
    }

    public void setArrTimeZone(String arrTimeZone) {
        this.arrTimeZone = arrTimeZone;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getArrCityEnglishName() {
        return arrCityEnglishName;
    }

    public void setArrCityEnglishName(String arrCityEnglishName) {
        this.arrCityEnglishName = arrCityEnglishName;
    }

    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    public String getArrGate() {
        return arrGate;
    }

    public void setArrGate(String arrGate) {
        this.arrGate = arrGate;
    }

    public String getBaggageCarousel() {
        return baggageCarousel;
    }

    public void setBaggageCarousel(String baggageCarousel) {
        this.baggageCarousel = baggageCarousel;
    }

    public String getAirlineChineseName() {
        return airlineChineseName;
    }

    public void setAirlineChineseName(String airlineChineseName) {
        this.airlineChineseName = airlineChineseName;
    }

    public String getAirlineEnglishName() {
        return airlineEnglishName;
    }

    public void setAirlineEnglishName(String airlineEnglishName) {
        this.airlineEnglishName = airlineEnglishName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

}
