package com.adatafun.base.data.center.pojo;

/**
 * 钉钉发送对象
 *
 * @date: 2018/1/18 上午10:21
 * @author: ironc
 * @version: 1.0
 */
public class WebHook {

    private String token;

    private String content;

    private String[] atMobiles;

    private boolean isAtAll;

    private String msgType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(String[] atMobiles) {
        this.atMobiles = atMobiles;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

}