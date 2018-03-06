package com.adatafun.base.data.center.controller;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.common.Result;
import com.adatafun.base.data.center.common.ResultUtils;
import com.adatafun.base.data.center.crawler.UmetripCrawlerHandler;
import com.adatafun.base.data.center.dto.FlightFeeyoCustomDTO;
import com.adatafun.base.data.center.dto.FlightQueryDTO;
import com.adatafun.base.data.center.po.CustomFlightPO;
import com.adatafun.base.data.center.service.CustomerFlightServiceInterface;
import com.adatafun.base.data.center.service.FlightServiceInterface;
import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @date: 2017/12/20 上午11:56
 * @author: ironc
 * @version: 1.0
 */
@RestController
@RequestMapping("/flight")
public class FlightController {

    private static Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private CustomerFlightServiceInterface customerFlightService;

    @Autowired
    private FlightServiceInterface flightService;

    /**
     * 航班号查询
     * 1. 航班号 + 日期
     * 2. 航段（出发地三字码，目的地三字码） + 日期
     *
     * @param flightQueryDTO
     * @return
     */
    @GetMapping(path = "/flightInfo", produces = {"application/json"})
    public String flightInfo(FlightQueryDTO flightQueryDTO) {
        try {
            if (logger.isDebugEnabled()) {
                StackTraceElement ste = new Throwable().getStackTrace()[1];
                logger.debug(ste.getFileName() + ":Line" + ste.getLineNumber());
            }
            /**
             * 参数校验
             */
            Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);

            // 可以查询往前一天的航班 -- 跨天的航班处理
            Date queryDate = DateUtils.addDay(date, -30);

            if (flightQueryDTO.getDepDate().before(queryDate)) {
                return JSON.toJSONString(ResultUtils.errorResult("只能查询当天或未来航班"), SerializerFeature.WriteMapNullValue);
            }

            if (flightQueryDTO.getArrCode() == null && flightQueryDTO.getDepCode() == null &&
                    StringUtils.isNoneBlank(flightQueryDTO.getFlightNo()) && flightQueryDTO.getDepDate() != null) {
                return JSON.toJSONString(
                        ResultUtils.successResult(
                                flightService.flightInfo(
                                        flightQueryDTO.getDepDate(),
                                        flightQueryDTO.getFlightNo())),
                        SerializerFeature.WriteMapNullValue);
            }
            if (flightQueryDTO.getArrCode() != null && flightQueryDTO.getDepCode() != null &&
                    flightQueryDTO.getArrCode().length() == Dictionary.THREE_CODE_SIZE && flightQueryDTO.getArrCode().length() == Dictionary.THREE_CODE_SIZE &&
                    StringUtils.isBlank(flightQueryDTO.getFlightNo()) && flightQueryDTO.getDepDate() != null) {
                return JSON.toJSONString(
                        ResultUtils.successResult(
                                flightService.flightInfo(
                                        flightQueryDTO.getDepDate(), flightQueryDTO.getDepCode(),
                                        flightQueryDTO.getArrCode())),
                        SerializerFeature.WriteMapNullValue);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(ResultUtils.errorResult());
    }

    /**
     * 航班号查询 -- 时刻信息
     * 用于实时性要求不高的地方，只支持航班号 + 日期
     *
     * @param flightQueryDTO
     * @return
     */
    @GetMapping(path = "/flightInfoNotRealTime", produces = {"application/json"})
    public String flightInfoNotRealTime(FlightQueryDTO flightQueryDTO) {
        try {
            /**
             * 参数校验
             */
            Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);

            // 可以查询往前一天的航班 -- 跨天的航班处理
            Date queryDate = DateUtils.addDay(date, -1);

            if (flightQueryDTO.getDepDate().before(queryDate)) {
                return JSON.toJSONString(ResultUtils.errorResult("只能查询当天或未来航班"), SerializerFeature.WriteMapNullValue);
            }

            if (flightQueryDTO.getArrCode() == null && flightQueryDTO.getDepCode() == null &&
                    StringUtils.isNoneBlank(flightQueryDTO.getFlightNo()) && flightQueryDTO.getDepDate() != null) {
                return JSON.toJSONString(
                        ResultUtils.successResult(
                                flightService.flightInfoNotRealTime(
                                        flightQueryDTO.getDepDate(),
                                        flightQueryDTO.getFlightNo())),
                        SerializerFeature.WriteMapNullValue);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(ResultUtils.errorResult());
    }

    /**
     * 定制接口
     *
     * @param flightQueryDTO
     * @return
     */
    @GetMapping(path = "/custom", produces = {"application/json"})
    public String customFlight(FlightQueryDTO flightQueryDTO) {
        Result result = new Result();

        if (flightQueryDTO.getAppId() == null && flightQueryDTO.getAppId().toString().length() == Dictionary.APPID_SIZE) {
            result.setMsg(Result.STATUS.UNKNOWN_CLIENT.getMsg());
            result.setStatus(Result.STATUS.UNKNOWN_CLIENT.getStatus());
            return JSON.toJSONString(result);
        }

        boolean custom = flightService.custom(flightQueryDTO.getFlightNo(), flightQueryDTO.getDepDate(), flightQueryDTO.getDepCode(), flightQueryDTO.getArrCode());
        if (!custom) {
            result.setMsg(Result.STATUS.CUSTOM_ERROR.getMsg());
            result.setStatus(Result.STATUS.CUSTOM_ERROR.getStatus());
            return JSON.toJSONString(result);
        }

        CustomFlightPO customFlightPO = new CustomFlightPO();
        customFlightPO.setFlightNo(flightQueryDTO.getFlightNo());
        customFlightPO.setDepDate(flightQueryDTO.getDepDate());
        customFlightPO.setDepAirportCode(flightQueryDTO.getDepCode());
        customFlightPO.setArrAirportCode(flightQueryDTO.getArrCode());
        boolean re = customerFlightService.createCustomFlight(flightQueryDTO.getAppId(), customFlightPO);

        if (re) {
            result.setMsg(Result.STATUS.CUSTOM_SUCCESS.getMsg());
            result.setStatus(Result.STATUS.CUSTOM_SUCCESS.getStatus());
        } else {
            result.setMsg(Result.STATUS.REPEAT_CUSTOM.getMsg());
            result.setStatus(Result.STATUS.REPEAT_CUSTOM.getStatus());
        }

        return JSON.toJSONString(result);
    }

    /**
     * 更新航班信息 -- 提供给飞常准
     *
     * @param request
     */
    @PostMapping(path = "/update", produces = {"application/json"})
    public void updateFlight(String request) {
// 如果request已经在AOP被去读了，此处的参数通过AOP给出，和AOP保持一致
//        try (DataInputStream in = new DataInputStream(request.getInputStream())) {
//            int totalbytes = request.getContentLength();
//            byte[] dataOrigin = new byte[totalbytes];
//            in.readFully(dataOrigin);
//            String reqcontent = new String(dataOrigin);
//            reqcontent = StringUtils.unicodeToString(reqcontent);
//            System.out.println(reqcontent);
//            List<FlightFeeyoCustomDTO> flightFeeyoCustomDTOList = JSONArray.parseArray(reqcontent, FlightFeeyoCustomDTO.class);
//            System.out.println(JSON.toJSONString(flightFeeyoCustomDTOList));
//            flightService.updateFlight(flightFeeyoCustomDTOList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (!("error").equals(request)) {
            if (logger.isInfoEnabled()) {
                logger.info("======》推送信息 : {}", request);
            }
            List<FlightFeeyoCustomDTO> flightFeeyoCustomDTOList = JSONArray.parseArray(request, FlightFeeyoCustomDTO.class);
            flightService.updateFlight(flightFeeyoCustomDTOList);
            customerFlightService.feeyoTrigger(flightFeeyoCustomDTOList);
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("======》无法推送 : {}", request);
            }
        }
    }

    /**
     * 测试方法 测试切面等功能
     *
     * @return
     */
    @GetMapping(path = "/testGet", produces = {"application/json"})
    public String doTestGet(FlightQueryDTO flightQueryDTO) {
        int i = RandomUtils.nextInt(1, 3);
        return JSON.toJSONString(ResultUtils.successResult(String.valueOf(i)));
    }

    @PostMapping(path = "/testPost", produces = {"application/json"})
    public String doTestPost(@RequestBody FlightQueryDTO flightQueryDTO) {
        int i = RandomUtils.nextInt(1, 3);
        return JSON.toJSONString(ResultUtils.successResult(String.valueOf(i)));
    }

    public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
        Date date = DateUtils.getDate(Dictionary.DATE_FORMAT);
        Date fileterDate = DateUtils.addDay(date, -1);
        String s = "2018-01-10";
        Date date1 = DateUtils.stringToDate(s, "yyyy-MM-dd");

        System.out.println(date1.before(fileterDate));
    }

}
