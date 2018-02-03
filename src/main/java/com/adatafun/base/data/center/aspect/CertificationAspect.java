package com.adatafun.base.data.center.aspect;

import com.adatafun.base.data.center.common.Dictionary;
import com.adatafun.base.data.center.common.Result;
import com.adatafun.base.data.center.common.ResultUtils;
import com.adatafun.base.data.center.dto.FlightQueryDTO;
import com.adatafun.base.data.center.mapper.BlackListPOMapper;
import com.adatafun.base.data.center.mapper.CustomerPOMapper;
import com.adatafun.base.data.center.mapper.DataCenterApiPOMapper;
import com.adatafun.base.data.center.mapper.WhiteListPOMapper;
import com.adatafun.base.data.center.po.BlackListPO;
import com.adatafun.base.data.center.po.CustomerPO;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import com.adatafun.base.data.center.po.WhiteListPO;
import com.adatafun.base.data.center.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.text.ParseException;
import java.util.*;

/**
 * Created by tiecheng on 2017/12/30.
 */
@Aspect
@Order(5)
@Component
public class CertificationAspect {

    private static Logger logger = LoggerFactory.getLogger(CertificationAspect.class);

    private static final String UPDATE_METHOD_NAME = "updateFlight";

    private static final String WHITE_LIST = "white_list";

    private static final String BLACK_LIST = "black_list";

    @Autowired
    private DataCenterApiPOMapper dataCenterApiPOMapper;

    @Autowired
    private CustomerPOMapper customerPOMapper;

    @Autowired
    private BlackListPOMapper blackListPOMapper;

    @Autowired
    private WhiteListPOMapper whiteListPOMapper;

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.adatafun.base.data.center.controller.FlightController.*(..))")
    public void auth() {
    }

    @Around("auth()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        startTime.set(System.currentTimeMillis());

        Object returnValue;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String method = request.getMethod();

        if (logger.isDebugEnabled()) {
            logger.debug("======》REAL IP : " + ip);
            // 记录下请求内容
            logger.debug("======》URL : " + request.getRequestURL().toString());
            logger.debug("======》HTTP_METHOD : " + method);
            logger.debug("======》IP : " + request.getRemoteAddr());
            logger.debug("======》CLASS_METHOD : " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
            logger.debug("======》ARGS : " + Arrays.toString(point.getArgs()));
        }

        System.out.println("======》REAL IP : " + ip);

        Object[] args = point.getArgs();

        DataCenterApiPO dataCenterApiPO = new DataCenterApiPO();
        dataCenterApiPO.setApiName(request.getRequestURL().toString());

//         校验黑白名单
        List<String> blacklist;
        String blacklistStr = RedisUtils.get(BLACK_LIST);
        if (!StringUtils.isBlank(blacklistStr)) {
            blacklist = JSONArray.parseArray(blacklistStr, String.class);
        } else {
            blacklist = new ArrayList<>();
            List<BlackListPO> blackList = blackListPOMapper.findBlackList();
            if (blackList != null && blackList.size() > 0) {
                for (BlackListPO blackListPO : blackList) {
                    blacklist.add(blackListPO.getIp());
                }
            }
            RedisUtils.set(BLACK_LIST, JSON.toJSONString(blacklist));
        }

        if (blacklist.contains(ip)) {
            System.out.println("======》属于黑名单");
            dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.UNKNOWN_CLIENT.getStatus()));
            dataCenterApiPO.setInvokeResult(Result.STATUS.UNKNOWN_CLIENT.getMsg());
            dataCenterApiPOMapper.insertOne(dataCenterApiPO);
            return JSON.toJSONString(ResultUtils.result(Result.STATUS.UNKNOWN_CLIENT.getStatus(), Result.STATUS.UNKNOWN_CLIENT.getMsg()));
        }

        List<String> whitelist;
        String whitelistStr = RedisUtils.get(WHITE_LIST);
        if (!StringUtils.isBlank(whitelistStr)) {
            whitelist = JSONArray.parseArray(whitelistStr, String.class);
        } else {
            whitelist = new ArrayList<>();
            List<WhiteListPO> whiteList = whiteListPOMapper.findWhiteList();
            if (whiteList != null && whiteList.size() > 0) {
                for (WhiteListPO whiteListPO : whiteList) {
                    whitelist.add(whiteListPO.getIp());
                }
            }
            RedisUtils.set(WHITE_LIST, JSON.toJSONString(whitelist));
        }

        if (!whitelist.contains(ip)) {
            System.out.println("======》未在白名单");
            dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.UNKNOWN_CLIENT.getStatus()));
            dataCenterApiPO.setInvokeResult(Result.STATUS.UNKNOWN_CLIENT.getMsg());
            dataCenterApiPOMapper.insertOne(dataCenterApiPO);
            return JSON.toJSONString(ResultUtils.result(Result.STATUS.UNKNOWN_CLIENT.getStatus(), Result.STATUS.UNKNOWN_CLIENT.getMsg()));
        }

        String customerCode = null;
        String bodyStr = "error";

        try (DataInputStream in = new DataInputStream(request.getInputStream())) {
            // 接收到请求，记录请求内容
            if (HttpClientUtils.GET_METHOD.equals(method)) {
                if (logger.isInfoEnabled()) {
                    logger.info("======》query string : {}", request.getQueryString());
                }
            }
            if (HttpClientUtils.POST_METHOD.equals(method)) {
                int totalbytes = request.getContentLength();
                byte[] dataOrigin = new byte[totalbytes];
                in.readFully(dataOrigin);
                bodyStr = new String(dataOrigin, "UTF-8");
                if (logger.isInfoEnabled()) {
                    logger.info("======》飞常准推送的数据 : {}", bodyStr);
                }
                bodyStr = StringUtils.unicodeToString(bodyStr);
                if (logger.isInfoEnabled()) {
                    logger.info("======》飞常准推送的数据 Unicode转汉字 : {}", bodyStr);
                }
            }

            // 不校验的方法
            if (UPDATE_METHOD_NAME.equals(point.getSignature().getName())) {
                // 因为此处流已经读取，因此在controller中不会再读取，这边参数直接处理的参数
                args = new Object[1];
                args[0] = bodyStr;
                returnValue = point.proceed(args);
                dataCenterApiPO.setRequestParam(bodyStr);
                dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.SUCCESS.getStatus()));
                dataCenterApiPO.setInvokeResult(Result.STATUS.SUCCESS.getMsg());
                dataCenterApiPOMapper.insertOne(dataCenterApiPO);
                return returnValue;
            }

            dataCenterApiPO.setRequestParam(JSON.toJSONString(point.getArgs()));

            FlightQueryDTO flightQueryDTO = (FlightQueryDTO) args[0];

            Date depDate = flightQueryDTO.getDepDate();
            Integer appId = flightQueryDTO.getAppId();
            String token = flightQueryDTO.getToken();

            if (depDate == null || depDate.before(DateUtils.getDate(Dictionary.DATE_FORMAT)) ||
                    appId == null || appId.toString().length() != Dictionary.APPID_SIZE || StringUtils.isBlank(token)) {
                dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.BAD_REQUEST.getStatus()));
                dataCenterApiPO.setInvokeResult(Result.STATUS.BAD_REQUEST.getMsg());
                dataCenterApiPOMapper.insertOne(dataCenterApiPO);
                return JSON.toJSONString(ResultUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), Result.STATUS.BAD_REQUEST.getMsg()));
            }
            // 处理参数对象
            Map<String, Object> tokenParams = parseFlightQueryDTO(flightQueryDTO);
            String signContent = ParamUtils.getSignContent(tokenParams);

            CustomerPO customerPO = customerPOMapper.selectByAppId(flightQueryDTO.getAppId());
            if (customerPO == null) {
                dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.UNKNOWN_CLIENT.getStatus()));
                dataCenterApiPO.setInvokeResult(Result.STATUS.UNKNOWN_CLIENT.getMsg());
                dataCenterApiPOMapper.insertOne(dataCenterApiPO);
                return JSON.toJSONString(ResultUtils.result(Result.STATUS.UNKNOWN_CLIENT.getStatus(), Result.STATUS.UNKNOWN_CLIENT.getMsg()));
            }
            // 程序后门，方便POSTMAN自测
            if (Objects.equals(customerPO.getCustomerType(), Short.parseShort("3"))) {
                dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.SUCCESS.getStatus()));
                dataCenterApiPO.setInvokeResult(Result.STATUS.SUCCESS.getMsg());
                returnValue = point.proceed(args);
                JSONObject o = JSON.parseObject((String) returnValue);
                JSONArray data = o.getJSONArray("data");
                if (data != null) {
                    dataCenterApiPO.setDataNum(data.size());
                }
                dataCenterApiPOMapper.insertOne(dataCenterApiPO);
                return returnValue;
            }
            customerCode = customerPO.getCustomerCode();
            signContent += customerCode;
            String md5Str = ParamUtils.MD5(ParamUtils.MD5(signContent));

            if (logger.isDebugEnabled()) {
                logger.debug("======》加密后的字符串 :{}", md5Str);
            }

            if (flightQueryDTO.getToken().equals(md5Str)) {
                //用改变后的参数执行目标方法
                returnValue = point.proceed(args);
                JSONObject o = JSON.parseObject((String) returnValue);
                dataCenterApiPO.setInvokeState(o.getString("status"));
                dataCenterApiPO.setInvokeResult(o.getString("msg"));
                JSONArray data = o.getJSONArray("data");
                if (data != null && data.size() > 0) {
                    dataCenterApiPO.setDataNum(data.size());
                }
                dataCenterApiPO.setCustomerCode(customerCode);
                dataCenterApiPOMapper.insertOne(dataCenterApiPO);
            } else {
                returnValue = JSON.toJSONString(ResultUtils.result(Result.STATUS.TOKEN_ERROR.getStatus(), Result.STATUS.TOKEN_ERROR.getMsg()));
                dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.TOKEN_ERROR.getStatus()));
                dataCenterApiPO.setInvokeResult(Result.STATUS.TOKEN_ERROR.getMsg());
                dataCenterApiPO.setCustomerCode(customerCode);
                dataCenterApiPOMapper.insertOne(dataCenterApiPO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnValue = JSON.toJSONString(ResultUtils.result(Result.STATUS.ERROR.getStatus(), Result.STATUS.ERROR.getMsg()));
            dataCenterApiPO.setInvokeState(String.valueOf(Result.STATUS.ERROR.getStatus()));
            dataCenterApiPO.setInvokeResult(Result.STATUS.ERROR.getMsg());
            dataCenterApiPO.setCustomerCode(customerCode);
            dataCenterApiPOMapper.insertOne(dataCenterApiPO);
        }
        return returnValue;
    }

    private Map<String, Object> parseFlightQueryDTO(FlightQueryDTO flightQueryDTO) throws ParseException {
        Map<String, Object> result = new HashMap<>();
        result.put("appId", String.valueOf(flightQueryDTO.getAppId()));
        result.put("depDate", DateUtils.dateToString(flightQueryDTO.getDepDate(), Dictionary.DATE_FORMAT));
        if (flightQueryDTO.getFlightNo() != null) {
            result.put("flightNo", flightQueryDTO.getFlightNo());
        }
        if (flightQueryDTO.getArrCode() != null) {
            result.put("arrCode", flightQueryDTO.getArrCode());
        }
        if (flightQueryDTO.getDepCode() != null) {
            result.put("depCode", flightQueryDTO.getDepCode());
        }
        return result;
    }

    public static void main(String[] args) {
        //  处理黑白名单
        String ip1 = "192.168.1.178";
        String ip2 = "127.0.0.1";
        String ip3 = "122.224.248.26";
        String[] whitelistIps = new String[]{ip1, ip2, ip3};
        List<String> whitelist = Arrays.asList(whitelistIps);
//        RedisUtils.set("whitelist", JSON.toJSONString(whitelist));
//        System.out.println(whitelist.contains(ip1));
//        System.out.println(whitelist.contains("127.0.0.02"));

//        List<String> whitelist2 = JSONArray.parseArray(RedisUtils.get("whitelist"), String.class);
//        System.out.println(whitelist.contains(ip1));
//        System.out.println(whitelist.contains("127.0.0.02"));

        RedisUtils.set("whitelist", JSON.toJSONString(whitelist));
    }

}
