package com.adatafun.base.data.center.util;

import com.adatafun.base.data.center.pojo.WebHook;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URL;

/**
 * 钉钉工具类
 *
 * @date: 2018/1/18 上午9:59
 * @author: ironc
 * @version: 1.0
 */
public class DingDingUtils {

    public static final String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=d3dfacef36f286b30a7ca7b46ce56ab0d869c1d3add1100ff014b8de86ec30ac";

    public static void send(WebHook webHook) {
        try {
            URL url = new URL(webHook.getToken());
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);

//            httpPost.addHeader("Content-Type", "application/json; charset=utf-8");

            String textMsg = getMsg(webHook);
            System.out.println(textMsg);

            String result = HttpClientUtils.httpPostForWebService(uri, textMsg);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getMsg(WebHook webHook) {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"msgtype\":\"")
                .append(webHook.getMsgType())
                .append("\",\"text\":{\"content\":")
                .append("\"")
                .append(webHook.getContent())
                .append("\"")
                .append("}");
        if (webHook.getAtMobiles() != null) {
            sb.append(",").append("\"at\":{\"atMobiles\":[");
            String[] atMobiles = webHook.getAtMobiles();
            for (int i = 0; i < atMobiles.length; i++) {
                sb.append("\"").append(atMobiles[i]).append("\"").append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("],\"isAtAll\":").
                    append(false).append("}");
        } else {
            if (webHook.isAtAll()) {
                sb.append(",").append("\"at\":{\"atMobiles\":[").append("],\"isAtAll\":").append(webHook.isAtAll()).append("}");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    public static WebHook createWebHook(String token, String content, String[] atMobiles, boolean isAtAll, String msgType) {
        WebHook webHook = new WebHook();
        if (StringUtils.isNoneBlank(token)) {
            webHook.setToken(token);
        } else {
            return null;
        }
        if (StringUtils.isNoneBlank(content)) {
            webHook.setContent(content);
        } else {
            webHook.setContent("内容为空");
        }
        if (atMobiles != null && atMobiles.length > 0) {
            webHook.setAtMobiles(atMobiles);
        }
        webHook.setAtAll(isAtAll);
        webHook.setMsgType(msgType);
        return webHook;
    }

    public static WebHook createWebHook(String content, String[] atMobiles, boolean isAtAll, String msgType) {
        return createWebHook(WEBHOOK_TOKEN, content, atMobiles, isAtAll, msgType);
    }

    public static WebHook createWebHook(String token, String content) {
        return createWebHook(token, content, null, false, "text");
    }

    public static WebHook createWebHook(String token) {
        return createWebHook(token, null, null, false, "text");
    }

    public static void main(String[] args) {
        String[] strings = {"18662200227"};
        System.out.println(strings.toString());
        WebHook webHook = createWebHook(
                "https://oapi.dingtalk.com/robot/send?access_token=d3dfacef36f286b30a7ca7b46ce56ab0d869c1d3add1100ff014b8de86ec30ac",
                "刘总发红包", strings, true, "text");
        send(webHook);
    }

}
