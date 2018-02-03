package com.adatafun.base.data.center.aspect;

import com.adatafun.base.data.center.common.Result;
import com.adatafun.base.data.center.common.ResultUtils;
import com.adatafun.base.data.center.mapper.SourceApiPOMapper;
import com.adatafun.base.data.center.po.SourceApiPO;
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

import java.util.Arrays;

/**
 * Created by tiecheng on 2018/1/2.
 */
@Aspect
@Order(5)
@Component
public class FeeyoSourceAspect {

    private static final String SOURCE_NAME = "飞常准";

    private static Logger logger = LoggerFactory.getLogger(FeeyoSourceAspect.class);

    @Autowired
    private SourceApiPOMapper sourceApiPOMapper;

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.adatafun.base.data.center.source.FeeyoSource.flightInfo(..))")
    public void sourceFlightInfo() {
    }

    @Pointcut("execution(public * com.adatafun.base.data.center.source.FeeyoSource.custom(..))")
    public void sourceCustom() {
    }

    @Around("sourceFlightInfo()")
    public Object processFlightInfo(ProceedingJoinPoint point) throws Throwable {
        startTime.set(System.currentTimeMillis());

        String classMethod = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();

//        logger.info("======》CLASS_METHOD : " + classMethod);
//        logger.info("======》ARGS : " + Arrays.toString(point.getArgs()));

        Object[] args = point.getArgs();

        SourceApiPO sourceApiPO = new SourceApiPO();
        sourceApiPO.setRequestParam(JSON.toJSONString(args));

        Object returnValue = null;
        try {
            sourceApiPO.setApiName(classMethod);
            sourceApiPO.setSourceName(SOURCE_NAME);
            returnValue = point.proceed(args);

            String returnValue1 = (String) returnValue;
            if (returnValue1.contains("error")) {
                sourceApiPO.setInvokeResult(returnValue1);
            } else {
                JSONArray jsonArray = JSON.parseArray(returnValue1);
                if (jsonArray != null && jsonArray.size() > 0) {
                    sourceApiPO.setDataNum(jsonArray.size());
                }
                sourceApiPO.setInvokeResult(JSON.toJSONString(ResultUtils.successResult()));
            }
            sourceApiPOMapper.insertOne(sourceApiPO);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            sourceApiPO.setInvokeResult(JSON.toJSONString(ResultUtils.errorResult()));
            sourceApiPOMapper.insertOne(sourceApiPO);
        }

        return returnValue;
    }

    @Around("sourceCustom()")
    public Object processCustom(ProceedingJoinPoint point) throws Throwable {
        startTime.set(System.currentTimeMillis());

        String classMethod = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();

//        logger.info("======》CLASS_METHOD : " + classMethod);
//        logger.info("======》ARGS : " + Arrays.toString(point.getArgs()));

        Object[] args = point.getArgs();

        SourceApiPO sourceApiPO = new SourceApiPO();
        sourceApiPO.setRequestParam(JSON.toJSONString(args));

        Object returnValue = null;
        try {
            sourceApiPO.setApiName(classMethod);
            sourceApiPO.setSourceName(SOURCE_NAME);
            returnValue = point.proceed(args);
            logger.info("======》飞常准 : " + JSON.toJSONString(returnValue));
            sourceApiPO.setInvokeResult(JSON.toJSONString(returnValue));
            sourceApiPOMapper.insertOne(sourceApiPO);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            sourceApiPO.setInvokeResult(JSON.toJSONString(ResultUtils.errorResult()));
            sourceApiPOMapper.insertOne(sourceApiPO);
        }

        return returnValue;
    }

}
