package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.common.FlightInfoOperatorPool;
import com.adatafun.base.data.center.conf.RedisConf;
import com.adatafun.base.data.center.crawler.FeeyoCrawlerHandler;
import com.adatafun.base.data.center.crawler.UmetripCrawlerHandler;
import com.adatafun.base.data.center.dto.AlternateInfoDTO;
import com.adatafun.base.data.center.dto.FlightFeeyoCustomDTO;
import com.adatafun.base.data.center.dto.FlightFeeyoDTO;
import com.adatafun.base.data.center.mapper.FlightPOMapper;
import com.adatafun.base.data.center.po.FlightPO;
import com.adatafun.base.data.center.pojo.WebHook;
import com.adatafun.base.data.center.service.FlightServiceInterface;
import com.adatafun.base.data.center.source.FeeyoSource;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.DingDingUtils;
import com.adatafun.base.data.center.util.RedisUtils;
import com.adatafun.base.data.center.util.StringUtils;
import com.adatafun.base.data.center.vo.FlightVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.adatafun.base.data.center.util.DingDingUtils.send;

/**
 * @date: 2017/12/28 下午3:28
 * @author: ironc
 * @version: 1.0
 */
@Service
@Transactional
public class FlightServiceImpl implements FlightServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    /**
     * 航班号+日期 查询定制标识
     * 每个航段都已经定制，为1
     */
    private static final String LOCAL_CONDITION_VALUE_CUSTOM = "1";

    /**
     * 航班信息查询 数据源无数据标识
     */
    private static final String LOCAL_CONDITION_VALUE_ERROR = "-1";

    /**
     * 航班状态值
     */
    private static final String FLIGHT_STATE_ARRIVAL = "到达";

    private static final String FLIGHT_STATE_CANCEL = "取消";

    private static final String FLIGHT_STATE_ADVANCE_CANCEL = "提前取消";

    /**
     * 十二个小时秒数
     */
    private static final int TWELVE_HOURS_TIME = 12 * 60 * 60;

    @Autowired
    private FlightPOMapper flightPOMapper;

    @Autowired
    private FeeyoSource feeyoSource;

    @Autowired
    private UmetripCrawlerHandler umetripCrawlerHandler;

    @Autowired
    private FeeyoCrawlerHandler feeyoCrawlerHandler;

    @Override
    public List<FlightVO> flightInfo(Date depDate, String flightNo) {
        String redisKeyValue = depDate + "_" + flightNo;
        String cacheValue = RedisUtils.get(redisKeyValue);
        boolean isCacheValueEmpty = StringUtils.isBlank(cacheValue);
        List<FlightVO> result = new ArrayList<>();
        try {
            Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);
            if (depDate.after(date)) {
                result = parseFuture(depDate, flightNo, redisKeyValue,
                        cacheValue, isCacheValueEmpty);
            } else if (depDate.before(date)) {

            } else {
                result = parseToday(depDate, flightNo, redisKeyValue,
                        cacheValue, isCacheValueEmpty);
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebHook webHook = DingDingUtils.createWebHook(
                    Dictionary.DING_DING_URL,
                    e.getMessage(),
                    null, false, "text");
            send(webHook);
        }
        return result;
    }

    /**
     * 当日航班信息解析
     *
     * @date: 2018/2/3 下午4:45
     * @author: ironc
     * @version: 1.0
     */
    private List<FlightVO> parseToday(Date depDate, String flightNo, String redisKeyValue,
                                      String cacheValue, boolean isCacheValueEmpty) throws ParseException {
        List<FlightVO> result = new ArrayList<>();
        String info;
        if (!isCacheValueEmpty) {
            if (LOCAL_CONDITION_VALUE_ERROR.equals(isCacheValueEmpty)) {
                return result;
            }
            if (LOCAL_CONDITION_VALUE_CUSTOM.equals(isCacheValueEmpty)) {
                result = JSONArray.parseArray(JSON.toJSONString(flightPOMapper.selectByDepDateAndFlightNo(depDate, flightNo)), FlightVO.class);
                return result;
            }
            if (StringUtils.isBlank(cacheValue) || Objects.equals("[]", cacheValue)) {
                result = JSONArray.parseArray(JSON.toJSONString(flightPOMapper.selectByDepDateAndFlightNo(depDate, flightNo)), FlightVO.class);
                RedisUtils.set(redisKeyValue, JSON.toJSONString(result), RedisConf.CAN_REDIS_TIME);
                return result;
            }
            result = JSONArray.parseArray(cacheValue, FlightVO.class);
        } else {
            info = feeyoSource.flightInfo(depDate, flightNo);
            result = parseFeeyoSource(info, redisKeyValue, RedisConf.CAN_REDIS_TIME);
        }
        return result;
    }

    /**
     * 未来航班信息解析
     *
     * @date: 2018/2/3 下午4:45
     * @author: ironc
     * @version: 1.0
     */
    private List<FlightVO> parseFuture(Date depDate, String flightNo, String redisKeyValue,
                                       String cacheValue, boolean isCacheValueEmpty) throws ParseException {
        List<FlightVO> result = new ArrayList<>();
        String info;
        if (!isCacheValueEmpty) {
            if (LOCAL_CONDITION_VALUE_ERROR.equals(cacheValue)) {
                return result;
            }
            if (LOCAL_CONDITION_VALUE_CUSTOM.equals(isCacheValueEmpty)) {
                result = JSONArray.parseArray(JSON.toJSONString(flightPOMapper.selectByDepDateAndFlightNo(depDate, flightNo)), FlightVO.class);
                return result;
            }
            if (StringUtils.isBlank(cacheValue) || Objects.equals("[]", cacheValue)) {
                result = JSONArray.parseArray(
                        JSON.toJSONString(
                                flightPOMapper.selectByDepDateAndFlightNo(depDate, flightNo)), FlightVO.class);
                RedisUtils.set(redisKeyValue, JSON.toJSONString(result), millisFurture(depDate));
            }
        } else {
            info = feeyoSource.flightInfo(depDate, flightNo);
            result = parseFeeyoSource(info, redisKeyValue, millisFurture(depDate));
        }
        return result;
    }

    /**
     * 解析飞常准的返回数据
     *
     * @param source
     * @param redisKeyValue
     * @param time
     * @return
     * @throws ParseException
     */
    private List<FlightVO> parseFeeyoSource(String source, String redisKeyValue, int time) throws ParseException {
        List<FlightVO> result = new ArrayList<>();
        if (source.contains("error")) {
            JSONObject object = JSONObject.parseObject(source);
            if (Objects.equals(object.get("error_code"), Short.parseShort("5")) ||
                    Objects.equals(object.get("error_code"), Short.parseShort("10"))) {
                RedisUtils.set(redisKeyValue, LOCAL_CONDITION_VALUE_ERROR, TWELVE_HOURS_TIME);
            }
        } else {
            List<FlightFeeyoDTO> flightFeeyoDTOS = JSONArray.parseArray(source, FlightFeeyoDTO.class);
            List<FlightPO> flightPOList = copyPropertiesBySelf(flightFeeyoDTOS);
            parseFromFeeyo(flightPOList);
            result = JSONArray.parseArray(JSON.toJSONString(flightPOList), FlightVO.class);
            RedisUtils.set(redisKeyValue, JSON.toJSONString(result), time);
        }
        return result;
    }

    @Override
    public List<FlightVO> flightInfo(Date depDate, String depCode, String arrCode) {
        List<FlightVO> result = new ArrayList<>();
        try {
            result = queryByCacheOrSource(depDate, depCode, arrCode);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            WebHook webHook = DingDingUtils.createWebHook(
                    Dictionary.DING_DING_URL,
                    e.getMessage(),
                    null, false, "text");
            send(webHook);
            return result;
        }
    }

    private List<FlightVO> queryByCacheOrSource(Date depDate, String depCode, String arrCode) throws Exception {
        /**
         * 获取缓存值
         */
        String redisKeyValue = depDate + "_" + depCode + "_" + arrCode;
        List<FlightVO> result = new ArrayList<>();
        String cacheValue = RedisUtils.get(redisKeyValue);
        boolean isCacheValueEmpty = StringUtils.isBlank(cacheValue);

        Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);
        /**
         * 缓存值为空
         */
        if (isCacheValueEmpty) {
            /**
             * 通过Redis获取查询资格
             */
            String redisIsNeedQueryKeyValue = "lock:" + depDate + "_" + depCode + "_" + arrCode;
            String nx = RedisUtils.setNx(redisIsNeedQueryKeyValue, "1", 60);
            if ("1".equals(nx)) {
                String info = feeyoSource.flightInfo(depCode, arrCode, depDate);
                if (logger.isInfoEnabled()) {
                    logger.info("===》飞常准查询到的数据是 {}", info);
                }
                if (info.contains("error")) {
                    JSONObject object = JSONObject.parseObject(info);
                    if (Objects.equals(object.get("error_code"), 5) ||
                            Objects.equals(object.get("error_code"), 10)) {
                        RedisUtils.set(redisKeyValue, LOCAL_CONDITION_VALUE_ERROR, TWELVE_HOURS_TIME);
                        RedisUtils.del(redisIsNeedQueryKeyValue);
                    }
                } else {
                    List<FlightFeeyoDTO> flightFeeyoDTOS = JSONArray.parseArray(info, FlightFeeyoDTO.class);
                    List<FlightPO> flightPOList = copyPropertiesBySelf(flightFeeyoDTOS);
                    result = JSONArray.parseArray(JSON.toJSONString(flightPOList), FlightVO.class);
                    parseFromFeeyo(flightPOList);
                    if (depDate.after(date)) {
                        RedisUtils.set(redisKeyValue, JSON.toJSONString(result), millisFurture(depDate));
                    } else if (depDate.before(date)) {

                    } else {
                        RedisUtils.set(redisKeyValue, JSON.toJSONString(result), millisToday(depDate));
                    }
                }
            } else {
                compareCache(depDate, depCode, arrCode);
            }
        } else {
            if (LOCAL_CONDITION_VALUE_ERROR.equals(cacheValue)) {
                return result;
            }
            // 异常补偿
            if (Objects.equals("[]", cacheValue)) {
                // 理论上是数据丢失 从本地数据库查询
                result = JSONArray.parseArray(JSON.toJSONString(flightPOMapper.selectByDepDateAndDepCodeAndArrCode(depDate, depCode, arrCode)), FlightVO.class);
                if (depDate.after(date)) {
                    RedisUtils.set(redisKeyValue, JSON.toJSONString(result), millisFurture(depDate));
                } else if (depDate.before(date)) {

                } else {
                    RedisUtils.set(redisKeyValue, JSON.toJSONString(result), RedisConf.CAN_REDIS_TIME);
                }
                return result;
            }
            result = JSONArray.parseArray(cacheValue, FlightVO.class);
        }
        return result;
    }

    private List<FlightVO> compareCache(Date depDate, String depCode, String arrCode) throws InterruptedException {
        String redisKeyValue = depDate + "_" + depCode + "_" + arrCode;
        List<FlightVO> result = new ArrayList<>();
        String cacheValue = RedisUtils.get(redisKeyValue);
        boolean isCacheValueEmpty = StringUtils.isBlank(cacheValue);
        for (int i = 0; i < 3; i++) {
            Thread.sleep(250);
            if (isCacheValueEmpty) {
                continue;
            } else {
                if (LOCAL_CONDITION_VALUE_ERROR.equals(cacheValue)) {
                    break;
                }
                result = JSONArray.parseArray(cacheValue, FlightVO.class);
                break;
            }
        }
        return result;
    }

    /**
     * 非实时调用航班信息接口
     *
     * @param depDate  出发日期（航班日期哦）
     * @param flightNo 航班号
     * @return
     */
    @Override
    public List<FlightVO> flightInfoNotRealTime(Date depDate, String flightNo) {
        List<FlightVO> result = new ArrayList<>();
        try {
            String redisKeyValue = depDate + "_" + flightNo;
            String cacheValue = RedisUtils.get(redisKeyValue);
            boolean isCacheValueEmpty = StringUtils.isBlank(cacheValue);
            if (isCacheValueEmpty) {
                String redisKeyValue2 = depDate + "_" + flightNo + "_Not_Real";
                String cacheValue2 = RedisUtils.get(redisKeyValue2);
                boolean isCacheValueEmpty2 = StringUtils.isBlank(cacheValue2);
                if (isCacheValueEmpty2) {
                    logger.info("===》flightInfoNotRealTime 不存在缓存");
                    result = flightInfoFromFeeyoByCrawler(depDate, flightNo);
                    if (result == null || result.size() <= 0) {
                        result = flightInfo(depDate, flightNo);
                    }
                    RedisUtils.set(redisKeyValue2, JSON.toJSONString(result), millisToday(depDate));
                } else {
                    logger.info("===》flightInfoNotRealTime 存在缓存");
                    result = JSONArray.parseArray(cacheValue2, FlightVO.class);
                }
            } else {
                logger.info("===》flightInfo 存在缓存");
                result = JSONArray.parseArray(cacheValue, FlightVO.class);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    private List<FlightVO> flightInfoFromFeeyoByCrawler(Date depDate, String depCode, String arrCode) throws ParseException {
        return flightInfoFromFeeyoByCrawler(depDate, null, depCode, arrCode);
    }

    private List<FlightVO> flightInfoFromFeeyoByCrawler(Date depDate, String flightNo) throws ParseException {
        return flightInfoFromFeeyoByCrawler(depDate, flightNo, null, null);
    }

    private List<FlightVO> flightInfoFromFeeyoByCrawler(Date depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        return flightInfoByCrawler("feeyoCrawlerHandler", depDate, flightNo, depCode, arrCode);

    }

    private List<FlightVO> flightInfoFromUmetripByCrawler(Date depDate, String depCode, String arrCode) throws ParseException {
        return flightInfoFromUmetripByCrawler(depDate, null, depCode, arrCode);
    }

    private List<FlightVO> flightInfoFromUmetripByCrawler(Date depDate, String flightNo) throws ParseException {
        return flightInfoFromUmetripByCrawler(depDate, flightNo, null, null);
    }

    private List<FlightVO> flightInfoFromUmetripByCrawler(Date depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        return flightInfoByCrawler("umetripCrawlerHandler", depDate, flightNo, depCode, arrCode);
    }

    private List<FlightVO> flightInfoByCrawler(String type, Date depDate, String flightNo, String depCode, String arrCode) throws ParseException {
        List<FlightPO> flightPOList;
        if (depCode == null && arrCode == null) {
            if ("feeyoCrawlerHandler".equals(type)) {
                flightPOList = JSONArray.parseArray(feeyoCrawlerHandler.flightInfo(depDate, flightNo), FlightPO.class);
            } else if ("umetripCrawlerHandler".equals(type)) {
                flightPOList = JSONArray.parseArray(umetripCrawlerHandler.flightInfo(depDate, flightNo), FlightPO.class);
            } else {
                flightPOList = new ArrayList<>();
            }
        } else {
            if ("feeyoCrawlerHandler".equals(type)) {
                flightPOList = JSONArray.parseArray(feeyoCrawlerHandler.flightInfo(depDate, depCode, arrCode), FlightPO.class);
            } else if ("umetripCrawlerHandler".equals(type)) {
                flightPOList = JSONArray.parseArray(umetripCrawlerHandler.flightInfo(depDate, depCode, arrCode), FlightPO.class);
            } else {
                flightPOList = new ArrayList<>();
            }
        }
        for (FlightPO flightPO : flightPOList) {
            if (flightPO.getDepDate() == null) {
                flightPO.setDepDate(DateUtils.formate(flightPO.getDepScheduledDate(), Dictionary.DATE_FORMAT));
                flightPO.setArrDate(DateUtils.formate(flightPO.getArrScheduledDate(), Dictionary.DATE_FORMAT));
            }
        }
        parseFromFeeyo(flightPOList);
        return JSONArray.parseArray(JSON.toJSONString(flightPOList), FlightVO.class);
    }

    /**
     * 判断是否是计划时间一个小时内的航班
     *
     * @param depScheduledDate
     * @return
     */
    private boolean isPlanTimeOutOneHour(Date depScheduledDate) {
        // time now
        Date date = new Date();

        long oneHour = 1000 * 60 * 60;

        if (depScheduledDate.getTime() - date.getTime() > oneHour) {
            return true;
        }

        return false;
    }

    @Override
    public List<FlightVO> flightInfo(Date depDate, String flightNo, String depCode, String arrCode) {
        return null;
    }

    /**
     * 更新航班信息
     *
     * @param flightFeeyoCustomDTOList 飞常准推送集合对象
     */
    @Override
    public void updateFlight(List<FlightFeeyoCustomDTO> flightFeeyoCustomDTOList) {
        try {
            List<FlightPO> flightPOList = copyPropertiesBySelf2(flightFeeyoCustomDTOList);
            for (FlightPO flightPO : flightPOList) {
                flightPO.setUpdateTime(new Date());
                flightPOMapper.updateByFourParams(flightPO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebHook webHook = DingDingUtils.createWebHook(
                    Dictionary.DING_DING_URL,
                    e.getMessage(),
                    null, false, "text");
            send(webHook);
        }
    }

    /**
     * 刷新航班&航班缓存
     *
     * @param flightPO     飞常准推送的航班对象
     * @param redisKey     缓存key
     * @param flightVOList 航班展示集合对象（缓存对象）
     */
    private void freshCache(FlightPO flightPO, String redisKey, List<FlightVO> flightVOList) {
        for (FlightVO flightVO : flightVOList) {
            if (flightPO.getDepActualDate() != null) {
                flightVO.setDepActualDate(flightPO.getDepActualDate());
            }
            if (flightPO.getDepEstimatedDate() != null) {
                flightVO.setDepEstimatedDate(flightPO.getDepEstimatedDate());
            }
            if (flightPO.getDepScheduledDate() != null) {
                flightVO.setDepScheduledDate(flightPO.getDepScheduledDate());
            }
            if (flightPO.getArrActualDate() != null) {
                flightVO.setArrActualDate(flightPO.getArrActualDate());
            }
            if (flightPO.getArrEstimatedDate() != null) {
                flightVO.setArrEstimatedDate(flightPO.getArrEstimatedDate());
            }
            if (flightPO.getArrScheduledDate() != null) {
                flightVO.setArrScheduledDate(flightPO.getArrScheduledDate());
            }
            flightVO.setFlightState(flightPO.getFlightState());
            flightVO.setBoardGate(flightPO.getBoardGate());
            flightVO.setBoardGate(flightPO.getBoardGate());
            flightVO.setArrGate(flightPO.getArrGate());
            flightVO.setCheckInCounter(flightPO.getCheckInCounter());
        }
        RedisUtils.set(redisKey, JSON.toJSONString(flightVOList));
    }

    @Override
    public String isCustom(String flightNo, Date depDate, String depCode, String arrCode) {
        return null;
    }

    @Override
    public boolean custom(String flightNo, Date depDate, String depCode, String arrCode) {
        try {
            FlightPO flightPO = flightPOMapper.selectByFourParams(flightNo, depDate, depCode, arrCode);

            // 某些状态下不去定制
            if (flightPO == null) {
                if (logger.isInfoEnabled()) {
                    logger.info("======》本地库不存在，无法定制 ** 航班号：{}，出发三字码：{}，到达三字码：{}，航班日期：{}", flightNo, depCode, arrCode, depDate);
                }
                return false;
            }

            if ("到达".equals(flightPO.getFlightState()) ||
                    "提前取消".equals(flightPO.getFlightState())) {
                if (logger.isInfoEnabled()) {
                    logger.info("======》航班已经到达，无法定制 ** 航班号：{}，出发三字码：{}，到达三字码：{}，航班日期：{}", flightNo, depCode, arrCode, depDate);
                }
                return true;
            }

            // 已定制，直接返回
            if (Objects.equals(flightPO.getIsCustom(), Dictionary.CONDITION_TRUE)) {
                if (logger.isInfoEnabled()) {
                    logger.info("======》航班已经定制 ** 航班号：{}，出发三字码：{}，到达三字码：{}，航班日期：{}", flightNo, depCode, arrCode, depDate);
                }
                return true;
            }

            String custom = feeyoSource.custom(depCode, arrCode, depDate, flightNo);

            // 定制成功（成功&重复定制）
            JSONObject object = JSON.parseObject(custom);

            if (logger.isDebugEnabled()) {
                logger.debug("======》定制飞常准结果：{}", custom);
            }

            if (object.getInteger("error_code").equals(FeeyoSource.CUSTOM_SUCCESS_CODE) ||
                    object.getInteger("error_code").equals(FeeyoSource.REPEAT_CUSTOM_CODE)) {
                if (logger.isInfoEnabled()) {
                    logger.info("======》开始更新航班定制状态，飞常准返回结果：{}", custom);
                }
                flightPOMapper.updateIsCustomByFourParams(new Date(), Dictionary.CONDITION_TRUE, flightNo, depDate, depCode, arrCode);
                return true;
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("======》向飞常准定制失败：{}", custom);
                }
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            WebHook webHook = DingDingUtils.createWebHook(
                    Dictionary.DING_DING_URL,
                    e.getMessage(),
                    null, false, "text");
            send(webHook);
            return false;
        }
    }

    @Override
    public boolean custom(String flightNo, Date depDate, String depCode, String arrCode, int type) {
        try {
            String redisKeyValue = depDate + "_" + flightNo;
            FlightPO flightPO = flightPOMapper.selectByFourParams(flightNo, depDate, depCode, arrCode);
            if ("到达".equals(flightPO.getFlightState()) ||
                    "提前取消".equals(flightPO.getFlightState())) {
                if (logger.isInfoEnabled()) {
                    logger.info("======》航班已经到达，无法定制 ** 航班号：{}，出发三字码：{}，到达三字码：{}，航班日期：{}", flightNo, depCode, arrCode, depDate);
                }
                return true;
            }

            // 已定制，直接返回
            if (Objects.equals(flightPO.getIsCustom(), Dictionary.CONDITION_TRUE)) {
                if (logger.isInfoEnabled()) {
                    logger.info("======》航班已经定制 ** 航班号：{}，出发三字码：{}，到达三字码：{}，航班日期：{}", flightNo, depCode, arrCode, depDate);
                }
                return true;
            }

            String custom = feeyoSource.custom(depCode, arrCode, depDate, flightNo);

            // 定制成功（成功&重复定制）
            JSONObject object = JSON.parseObject(custom);

            if (logger.isDebugEnabled()) {
                logger.debug("======》定制飞常准结果：{}", custom);
            }

            if (object.getInteger("error_code").equals(FeeyoSource.CUSTOM_SUCCESS_CODE) ||
                    object.getInteger("error_code").equals(FeeyoSource.REPEAT_CUSTOM_CODE)) {
                flightPOMapper.updateIsCustomByFourParams(new Date(), Dictionary.CONDITION_TRUE, flightNo, depDate, depCode, arrCode);
                redisOperator(redisKeyValue, flightPO, depDate, type);
                return true;
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("======》向飞常准定制失败：{}", custom);
                }
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            WebHook webHook = DingDingUtils.createWebHook(
                    Dictionary.DING_DING_URL,
                    e.getMessage(),
                    null, false, "text");
            send(webHook);
            return false;
        }
    }

    private void redisOperator(String redisKeyValue, FlightPO flightPO, Date depDate, int type) {
        if (type > 3) {
            return;
        }
        if (type == 1) {
            RedisUtils.set(redisKeyValue, JSON.toJSONString(flightPO), saveRedisTime(depDate, 2));
        }
        if (type == 2 || type == 3) {
            List<FlightPO> flightPOList = flightPOMapper.selectByDepDateAndFlightNo(flightPO.getDepDate(), flightPO.getFlightNo());
            boolean isAllCustom = true;
            for (FlightPO po : flightPOList) {
                if (Dictionary.CONDITION_FALSE.equals(po.getIsCustom())) {
                    isAllCustom = false;
                    break;
                }
            }
            if (isAllCustom) {
                RedisUtils.set(redisKeyValue, JSON.toJSONString(flightPOList), saveRedisTime(depDate, 2));
            }
        }
    }

    @Override
    public String cancelCustom(Long customerId, String flightNo, Date depDate, String depCode, String arrCode) {
        return null;
    }

    @Autowired
    private FlightInfoOperatorPool flightInfoOperatorPool;

    /**
     * 处理飞常准的信息
     *
     * @param flightPOList
     */
    private void parseFromFeeyo(List<FlightPO> flightPOList) throws ParseException {
//        for (FlightPO flightPO : flightPOList) {
//            Date date = new Date();
//            flightPO.setUpdateTime(date);
//            flightPOMapper.insertOrUpdateByFourParams(flightPO);
//        }
        // 异步处理
        flightInfoOperatorPool.runOperator(flightPOList);
    }

    /**
     * 复制飞常准的属性到数据中心的航班信息
     *
     * @param flightFeeyoDTOList
     * @throws ParseException
     */
    private List<FlightPO> copyPropertiesBySelf(List<FlightFeeyoDTO> flightFeeyoDTOList) throws ParseException {
        List<FlightPO> flightPOList = new ArrayList<>();
        for (FlightFeeyoDTO flightFeeyoDTO : flightFeeyoDTOList) {
            FlightPO flightPO = new FlightPO();
            flightPO.setFlightNo(flightFeeyoDTO.getFlightNo());
            flightPO.setUpdateFlag("0");
            flightPO.setFlightCategory((short) (flightFeeyoDTO.getFlightCategory().intValue() + 1));
            flightPO.setFlightState(flightFeeyoDTO.getFlightState());
            flightPO.setIsStop(parseIsStopOrIsShare(flightFeeyoDTO.getIsStop()));
            flightPO.setIsShare(parseIsStopOrIsShare(flightFeeyoDTO.getIsShare()));
            flightPO.setFlightType(flightFeeyoDTO.getFlightType());
            flightPO.setPlanNo(flightFeeyoDTO.getPlanNo());
            flightPO.setShareFlightNo(flightFeeyoDTO.getShareFlightNo());
            flightPO.setFillFlightNo(flightFeeyoDTO.getFillFlightNo());
            flightPO.setIsNearOrFar(Short.parseShort("3"));
            flightPO.setLegFlag((short) (flightFeeyoDTO.getLegFlag().intValue() + 1));
            flightPO.setDistance(flightFeeyoDTO.getDistance());
            flightPO.setOntimeRate(flightFeeyoDTO.getOntimeRate());
            flightPO.setBoardGate(flightFeeyoDTO.getBoardGate());
            flightPO.setBoardTime(flightFeeyoDTO.getBoardTime());
            flightPO.setBoardState(flightFeeyoDTO.getBoardState());
            flightPO.setTransferAirport(flightFeeyoDTO.getTransferAirport());
            flightPO.setTransferAirportCode(flightFeeyoDTO.getTransferAirportCode());
            flightPO.setTransferAirportCodeEnName(flightFeeyoDTO.getTransferAirportCodeEnName());
            flightPO.setAlternateInfo(flightFeeyoDTO.getAlternateInfo());
            flightPO.setGatePosition(flightFeeyoDTO.getGatePosition());
            flightPO.setIsCustom(Short.parseShort("1"));
            flightPO.setDepAirportCode(flightFeeyoDTO.getDepAirportCode());
            flightPO.setDepAirport(flightFeeyoDTO.getDepAirport());
            flightPO.setDepAirportName(flightFeeyoDTO.getDepAirportName());
            if (flightFeeyoDTO.getDepScheduledDate() != null) {
                flightPO.setDepDate(DateUtils.formate(flightFeeyoDTO.getDepScheduledDate(), Dictionary.DATE_FORMAT));
            }
            flightPO.setDepScheduledDate(flightFeeyoDTO.getDepScheduledDate());
            flightPO.setDepEstimatedDate(flightFeeyoDTO.getDepEstimatedDate());
            flightPO.setDepActualDate(flightFeeyoDTO.getDepActualDate());
            flightPO.setDepTimeZone(flightFeeyoDTO.getDepTimeZone());
            flightPO.setDepCity(flightFeeyoDTO.getDepCity());
            flightPO.setDepCityEnglishName(flightFeeyoDTO.getDepCityEnglishName());
            flightPO.setDepTerminal(flightFeeyoDTO.getDepTerminal());
            flightPO.setDepGate(flightFeeyoDTO.getDepGate());
            flightPO.setCheckInCounter(flightFeeyoDTO.getCheckInCounter());
            flightPO.setArrAirportCode(flightFeeyoDTO.getArrAirportCode());
            flightPO.setArrAirport(flightFeeyoDTO.getArrAirport());
            flightPO.setArrAirportName(flightFeeyoDTO.getArrAirportName());
            if (flightFeeyoDTO.getDepScheduledDate() != null) {
                flightPO.setArrDate(DateUtils.formate(flightFeeyoDTO.getArrScheduledDate(), Dictionary.DATE_FORMAT));
            }
            flightPO.setArrScheduledDate(flightFeeyoDTO.getArrScheduledDate());
            flightPO.setArrEstimatedDate(flightFeeyoDTO.getArrEstimatedDate());
            flightPO.setArrActualDate(flightFeeyoDTO.getArrActualDate());
            flightPO.setArrTimeZone(flightFeeyoDTO.getArrTimeZone());
            flightPO.setArrCity(flightFeeyoDTO.getArrCity());
            flightPO.setArrCityEnglishName(flightFeeyoDTO.getArrCityEnglishName());
            flightPO.setArrTerminal(flightFeeyoDTO.getArrTerminal());
            flightPO.setArrGate(flightFeeyoDTO.getArrGate());
            flightPO.setBaggageCarousel(flightFeeyoDTO.getBaggageCarousel());
            flightPO.setAirlineChineseName(flightFeeyoDTO.getAirlineChineseName());
            flightPO.setAirlineEnglishName(flightFeeyoDTO.getAirlineEnglishName());
            flightPO.setAirlineCode(flightFeeyoDTO.getAirlineCode());
            flightPOList.add(flightPO);
        }
        return flightPOList;
    }

    private List<FlightPO> copyPropertiesBySelf2(List<FlightFeeyoCustomDTO> flightFeeyoCustomDTOList) throws ParseException {
        List<FlightPO> flightPOList = new ArrayList<>();
        for (FlightFeeyoCustomDTO flightFeeyoCustomDTO : flightFeeyoCustomDTOList) {
            FlightPO flightPO = new FlightPO();
            flightPO.setFlightNo(flightFeeyoCustomDTO.getFlightNo());
            flightPO.setUpdateFlag("0");
            flightPO.setFlightCategory((short) (flightFeeyoCustomDTO.getFlightCategory().intValue() + 1));
            flightPO.setFlightState(flightFeeyoCustomDTO.getFlightState());
            flightPO.setIsStop(parseIsStopOrIsShare(flightFeeyoCustomDTO.getIsStop()));
            flightPO.setIsShare(parseIsStopOrIsShare(flightFeeyoCustomDTO.getIsShare()));
            flightPO.setFlightType(flightFeeyoCustomDTO.getFlightType());
            flightPO.setPlanNo(flightFeeyoCustomDTO.getPlanNo());
            flightPO.setShareFlightNo(flightFeeyoCustomDTO.getShareFlightNo());
            flightPO.setFillFlightNo(flightFeeyoCustomDTO.getFillFlightNo());
            flightPO.setIsNearOrFar(Short.parseShort("3"));
            flightPO.setLegFlag((short) (flightFeeyoCustomDTO.getLegFlag().intValue() + 1));
            flightPO.setDistance(flightFeeyoCustomDTO.getDistance());
            flightPO.setOntimeRate(flightFeeyoCustomDTO.getOntimeRate());
            flightPO.setBoardGate(flightFeeyoCustomDTO.getBoardGate());
            flightPO.setBoardTime(flightFeeyoCustomDTO.getBoardTime());
            flightPO.setBoardState(flightFeeyoCustomDTO.getBoardState());
            flightPO.setTransferAirport(flightFeeyoCustomDTO.getTransferAirport());
            flightPO.setTransferAirportCode(flightFeeyoCustomDTO.getTransferAirportCode());
            flightPO.setTransferAirportCodeEnName(flightFeeyoCustomDTO.getTransferAirportCodeEnName());
//            flightPO.setAlternateInfo(flightFeeyoCustomDTO.getAlternateInfo());
            flightPO.setGatePosition(flightFeeyoCustomDTO.getGatePosition());
            flightPO.setIsCustom(Short.parseShort("1"));
            flightPO.setDepAirportCode(flightFeeyoCustomDTO.getDepAirportCode());
            flightPO.setDepAirport(flightFeeyoCustomDTO.getDepAirport());
            flightPO.setDepAirportName(flightFeeyoCustomDTO.getDepAirportName());
            flightPO.setDepDate(DateUtils.formate(flightFeeyoCustomDTO.getDepScheduledDate(), Dictionary.DATE_FORMAT));
            flightPO.setDepScheduledDate(flightFeeyoCustomDTO.getDepScheduledDate());
            flightPO.setDepEstimatedDate(flightFeeyoCustomDTO.getDepEstimatedDate());
            flightPO.setDepActualDate(flightFeeyoCustomDTO.getDepActualDate());
            flightPO.setDepTimeZone(flightFeeyoCustomDTO.getDepTimeZone());
            flightPO.setDepCity(flightFeeyoCustomDTO.getDepCity());
            flightPO.setDepCityEnglishName(flightFeeyoCustomDTO.getDepCityEnglishName());
            flightPO.setDepTerminal(flightFeeyoCustomDTO.getDepTerminal());
            flightPO.setDepGate(flightFeeyoCustomDTO.getDepGate());
            flightPO.setCheckInCounter(flightFeeyoCustomDTO.getCheckInCounter());
            flightPO.setArrAirportCode(flightFeeyoCustomDTO.getArrAirportCode());
            flightPO.setArrAirport(flightFeeyoCustomDTO.getArrAirport());
            flightPO.setArrAirportName(flightFeeyoCustomDTO.getArrAirportName());
            flightPO.setArrDate(DateUtils.formate(flightFeeyoCustomDTO.getArrScheduledDate(), Dictionary.DATE_FORMAT));
            flightPO.setArrScheduledDate(flightFeeyoCustomDTO.getArrScheduledDate());
            flightPO.setArrEstimatedDate(flightFeeyoCustomDTO.getArrEstimatedDate());
            flightPO.setArrActualDate(flightFeeyoCustomDTO.getArrActualDate());
            flightPO.setArrTimeZone(flightFeeyoCustomDTO.getArrTimeZone());
            flightPO.setArrCity(flightFeeyoCustomDTO.getArrCity());
            flightPO.setArrCityEnglishName(flightFeeyoCustomDTO.getArrCityEnglishName());
            flightPO.setArrTerminal(flightFeeyoCustomDTO.getArrTerminal());
            flightPO.setArrGate(flightFeeyoCustomDTO.getArrGate());
            flightPO.setBaggageCarousel(flightFeeyoCustomDTO.getBaggageCarousel());
            flightPO.setAirlineChineseName(flightFeeyoCustomDTO.getAirlineChineseName());
            flightPO.setAirlineEnglishName(flightFeeyoCustomDTO.getAirlineEnglishName());
            flightPO.setAirlineCode(flightFeeyoCustomDTO.getAirlineCode());
            flightPOList.add(flightPO);
            AlternateInfoDTO alternateInfo = flightFeeyoCustomDTO.getAlternateInfo();
            if (alternateInfo != null) {
                flightPO.setUpdateFlag(alternateInfo.getFid());
                flightPO.setDepAirport(alternateInfo.getFlightDepAirport());
                flightPO.setArrAirport(alternateInfo.getFlightArrAirport());
            }
        }

        return flightPOList;
    }

    /**
     * 转换状态码
     *
     * @param isStop
     * @return
     */
    private Short parseIsStopOrIsShare(Short isStop) {
        if (Objects.equals(isStop, Short.parseShort("0"))) {
            return Short.parseShort("0");
        }
        return Short.parseShort("1");
    }

    /**
     * 缓存当天的秒数
     *
     * @param depDate
     * @return
     */
    private Integer millisToday(Date depDate) {
        Date date = new Date();
        long millis = (DateUtils.addDay(depDate, 1).getTime() - date.getTime()) / 1000;
        logger.info("===> 缓存时间 {} 小时 - {} 分 - {} 秒 ，", millis / 3600, (millis % 3600) / 60, (millis % 3600) % 60);
        return (int) millis;
    }

    /**
     * 缓存时间
     *
     * @param depDate 出发时间
     * @return
     */
    private Integer saveRedisTime(Date depDate) {
        return saveRedisTime(depDate, 0);
    }

    /**
     * 缓存时间
     *
     * @param depDate  出发时间
     * @param overTime 超过时间(小时)
     * @return
     */
    private Integer saveRedisTime(Date depDate, int overTime) {
        Date date = new Date();
        long millis = 0L;
        try {
            Date today = DateUtils.getDate(Dictionary.DATE_FORMAT);
            depDate = depDate.after(today) ? depDate : DateUtils.addDay(depDate, 1);

            if (overTime == 0) {
                millis = depDate.getTime() - date.getTime() / 1000;
            } else {
                millis = (DateUtils.addHour(depDate, overTime).getTime() - date.getTime()) / 1000;
            }
            logger.info("===> 缓存时间 {} 小时 - {} 分 - {} 秒 ，", millis / 3600, (millis % 3600) / 60, (millis % 3600) % 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) millis;
    }

    private Integer millisFurture(Date depDate) {
        Date date = new Date();
        long millis = (depDate.getTime() - date.getTime()) / 1000;
        logger.info("===> 缓存时间 {} 小时 - {} 分 - {} 秒 ，", millis / 3600, (millis % 3600) / 60, (millis % 3600) % 60);
        return (int) millis;
    }

    public static void main(String[] args) throws ParseException {
        // 6976秒
//        String time = "2018-01-18";
//        Date depDate = DateUtils.stringToDate(time, Dictionary.DATE_FORMAT);
//        FlightServiceImpl flightService = new FlightServiceImpl();
//
//        flightService.millisToday(depDate);
//        flightService.millisFurture(depDate);
        String o = "1";

        List<FlightVO> flightVOList = JSONArray.parseArray(o, FlightVO.class);
    }


}
