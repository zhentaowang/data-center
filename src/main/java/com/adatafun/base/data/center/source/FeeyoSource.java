package com.adatafun.base.data.center.source;

import com.adatafun.base.data.center.util.DateUtils;
import com.adatafun.base.data.center.util.ParamUtils;
import com.adatafun.base.data.center.util.HttpClientUtils;
import com.adatafun.base.data.center.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 飞常准数据源
 *
 * @date: 2017/12/28 下午3:59
 * @author: ironc
 * @version: 1.0
 */
@Component
public class FeeyoSource {

    private static Logger logger = LoggerFactory.getLogger(FeeyoSource.class);

    private static final String CODE = "57e1e7bfef683";

    private static final String APPID = "10260";

    private static final String NAME = "飞常准";

    private static final String URL_FLIGHT = "http://open-al.variflight.com/api/flight";

    private static final String URL_ADDFLIGHTPUSH = "http://open-al.variflight.com/api/addflightpush";

    public static final int CUSTOM_SUCCESS_CODE = 8;

    public static final int REPEAT_CUSTOM_CODE = 6;

    public String flightInfo(Date depDate, String flightNo) {
        String result = null;
        if (logger.isInfoEnabled()) {
            logger.info("======》{} -- 查询航班信息 航班号：{}，航班日期：{}", NAME, flightNo, depDate);
        }

        try {
            String flightDate = DateUtils.dateToString(depDate, "yyyy-MM-dd");

            Map<String, Object> tokenParams = new HashMap<>();
            tokenParams.put("appid", APPID);
            tokenParams.put("date", flightDate);
            tokenParams.put("fnum", flightNo);

            String secretStr = ParamUtils.getSignContent(tokenParams);
            secretStr = secretStr + CODE;

            String md5Str = ParamUtils.MD5(secretStr);
            if (logger.isDebugEnabled()) {
                logger.debug("======》{} -- 第一次MD5加密：{}", NAME, md5Str);
            }
            md5Str = ParamUtils.MD5(md5Str);
            if (logger.isDebugEnabled()) {
                logger.debug("======》{} -- 第二次MD5加密：{}", NAME, md5Str);
            }

            Map<String, String> requestParams = new HashMap<>(4);
            requestParams.put("appid", APPID);
            requestParams.put("date", flightDate);
            requestParams.put("fnum", flightNo);
            requestParams.put("token", md5Str);

            result = HttpClientUtils.httpGetForWebService(URL_FLIGHT, requestParams);
            result = StringUtils.unicodeToString(result);

            if (logger.isInfoEnabled()) {
                logger.info("======》{} -- 请求飞常准的结果：{}", NAME, result);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("======》{} -- 方法异常：{}", NAME, e.getMessage());
            }
        }

        return result;
    }

    public String flightInfo(String depCode, String arrCode, Date depDate) {
        String result = null;
        if (logger.isDebugEnabled()) {
            logger.debug("======》{} -- 查询航班信息 出发地三字码：{}，目的地三字码：{}，航班日期：{}", NAME, depCode, arrCode, depDate);
        }

        try {
            String flightDate = DateUtils.dateToString(depDate, "yyyy-MM-dd");

            Map<String, Object> tokenParams = new HashMap<>();
            tokenParams.put("appid", APPID);
            tokenParams.put("date", flightDate);
            tokenParams.put("dep", depCode);
            tokenParams.put("arr", arrCode);

            String secretStr = ParamUtils.getSignContent(tokenParams);
            secretStr = secretStr + CODE;

            String md5Str = ParamUtils.MD5(secretStr);
            if (logger.isDebugEnabled()) {
                logger.debug("======》{} -- 第一次MD5加密：{}", NAME, md5Str);
            }
            md5Str = ParamUtils.MD5(md5Str);
            if (logger.isDebugEnabled()) {
                logger.debug("======》{} -- 第二次MD5加密：{}", NAME, md5Str);
            }

            Map<String, String> requestParams = new HashMap<>(4);
            requestParams.put("appid", APPID);
            requestParams.put("date", flightDate);
            requestParams.put("dep", depCode);
            requestParams.put("arr", arrCode);
            requestParams.put("token", md5Str);

            result = HttpClientUtils.httpGetForWebService(URL_FLIGHT, requestParams);
            result = StringUtils.unicodeToString(result);

            if (logger.isDebugEnabled()) {
                logger.debug("======》{} -- 请求飞常准的结果：{}", NAME, result);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("======》{} -- 方法异常：{}", NAME, e.getMessage());
            }
        }

        return result;
    }

    public String custom(String depCode, String arrCode, Date depDate, String flightNo) {
        String result = null;
        if (logger.isInfoEnabled()) {
            logger.info("======》{} -- 定制航班 航班号：{}，航班日期：{}，出发三字码：{}，到达三字码：{}", NAME, flightNo, depDate, depCode, arrCode);
        }

        try {
            String flightDate = DateUtils.dateToString(depDate, "yyyy-MM-dd");

            Map<String, Object> tokenParams = new HashMap<>();
            tokenParams.put("appid", APPID);
            tokenParams.put("date", flightDate);
            tokenParams.put("fnum", flightNo);
            tokenParams.put("dep", depCode);
            tokenParams.put("arr", arrCode);
            tokenParams.put("fid", "0");

            String secretStr = ParamUtils.getSignContent(tokenParams);
            secretStr = secretStr + CODE;

            String md5Str = ParamUtils.MD5(secretStr);
            if (logger.isDebugEnabled()) {
                logger.debug("======》{} -- 第一次MD5加密：{}", NAME, md5Str);
            }
            md5Str = ParamUtils.MD5(md5Str);
            if (logger.isDebugEnabled()) {
                logger.debug("======》{} -- 第二次MD5加密：{}", NAME, md5Str);
            }

            Map<String, String> requestParams = new HashMap<>();
            requestParams.put("appid", APPID);
            requestParams.put("date", flightDate);
            requestParams.put("fnum", flightNo);
            requestParams.put("dep", depCode);
            requestParams.put("arr", arrCode);
            requestParams.put("fid", "0");
            requestParams.put("token", md5Str);

            result = HttpClientUtils.httpGetForWebService(URL_ADDFLIGHTPUSH, requestParams);
            result = StringUtils.unicodeToString(result);

            if (logger.isInfoEnabled()) {
                logger.info("======》{} -- 请求飞常准的结果：{}", NAME, result);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("======》{} -- 方法异常：{}", NAME, e.getMessage());
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Date date = new Date();
        new FeeyoSource().flightInfo(date, "MU24741");
    }

}
