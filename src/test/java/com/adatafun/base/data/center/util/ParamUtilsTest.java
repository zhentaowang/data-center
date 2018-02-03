package com.adatafun.base.data.center.util;

import com.adatafun.base.data.center.dto.FlightFeeyoDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tiecheng on 2018/1/2.
 */
public class ParamUtilsTest {

    @Test
    public void getToken() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("flightNo", "CZ3100");
        map.put("depDate", "2018-01-06");
        map.put("arrCode", "CAN");
        map.put("depCode", "PEK");
        map.put("appId", String.valueOf(743074));
        String signContent = ParamUtils.getSignContent(map);

        signContent += "djxK5XGFLTeH9ajKLT";
        String md5Str = ParamUtils.MD5(ParamUtils.MD5(signContent));
        System.out.println(md5Str);
    }

    @Test
    public void test1(){
        Map<String, Object> map = new HashMap<>();
        map.put("flightNo", "CA1257");
        map.put("depDate", "2018-01-15");
//        map.put("arrCode", "CTU");
//        map.put("depCode", "CAN");
        map.put("appId", String.valueOf(843679));
        String signContent = ParamUtils.getSignContent(map);

        signContent += "ZMnxuZUWmWTnLf1l8b";
        String md5Str = ParamUtils.MD5(ParamUtils.MD5(signContent));

        System.out.println(md5Str);
    }

    @Test
    public void MD5() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("flightNo", "");
        map.put("depDate", "2018-01-15");
//        map.put("arrCode", "CTU");
//        map.put("depCode", "CAN");
        map.put("appId", String.valueOf(843679));
        String signContent = ParamUtils.getSignContent(map);

        signContent += "ZMnxuZUWmWTnLf1l8b";
        String md5Str = ParamUtils.MD5(ParamUtils.MD5(signContent));

        String url = "http://127.0.0.1:8066/flight/flightInfo";

        Map<String, String> param = new HashMap<>();
        param.put("flightNo", "CZ3100");
        param.put("depDate", "2018-01-06");
        param.put("appId", String.valueOf(255880));
        param.put("token", md5Str);

        String s = HttpClientUtils.httpGetForWebService(url, param);
        System.out.println(s);

    }

    @Test
    public void JSON() throws Exception {
        String json = "[\n" +
                "    {\n" +
                "        \"fcategory\": \"0\",\n" +
                "        \"FlightNo\": \"TV6350\",\n" +
                "        \"FlightCompany\": \"西藏航空\",\n" +
                "        \"FlightDepcode\": \"CAN\",\n" +
                "        \"FlightArrcode\": \"CTU\",\n" +
                "        \"FlightDeptimePlanDate\": \"2018-01-04 15:40:00\",\n" +
                "        \"FlightArrtimePlanDate\": \"2018-01-04 18:15:00\",\n" +
                "        \"FlightDeptimeReadyDate\": \"2018-01-04 15:40:00\",\n" +
                "        \"FlightArrtimeReadyDate\": \"2018-01-04 17:35:12\",\n" +
                "        \"FlightDeptimeDate\": \"\",\n" +
                "        \"FlightArrtimeDate\": \"\",\n" +
                "        \"CheckinTable\": \"L,M\",\n" +
                "        \"BoardGate\": \"A131\",\n" +
                "        \"ReachExit\": \"\",\n" +
                "        \"BaggageID\": \"24\",\n" +
                "        \"BoardState\": \"\",\n" +
                "        \"FlightState\": \"计划\",\n" +
                "        \"FlightHTerminal\": \"A\",\n" +
                "        \"FlightTerminal\": \"T2\",\n" +
                "        \"org_timezone\": \"28800\",\n" +
                "        \"dst_timezone\": \"28800\",\n" +
                "        \"ShareFlightNo\": \"CA4318\",\n" +
                "        \"StopFlag\": \"0\",\n" +
                "        \"ShareFlag\": \"1\",\n" +
                "        \"VirtualFlag\": \"0\",\n" +
                "        \"LegFlag\": \"0\",\n" +
                "        \"FlightDep\": \"广州\",\n" +
                "        \"FlightArr\": \"成都\",\n" +
                "        \"FlightDepAirport\": \"广州白云\",\n" +
                "        \"FlightArrAirport\": \"成都双流\"\n" +
                "    }\n" +
                "]";
        List<FlightFeeyoDTO> flightFeeyoDTOS = JSONArray.parseArray(json, FlightFeeyoDTO.class);
        flightFeeyoDTOS.parallelStream().forEach(x -> System.out.println(JSON.toJSONString(x, SerializerFeature.WriteMapNullValue)));
    }

}