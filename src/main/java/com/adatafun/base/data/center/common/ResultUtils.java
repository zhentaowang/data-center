package com.adatafun.base.data.center.common;

/**
 * 
 *
 * @date: 2017/11/9 上午9:52
 * @author: ironc
 * @version: 1.0
 */
public class ResultUtils {

    public static Result result(int status) {
        return result(status, null, null);
    }

    public static Result result(Object data) {
        return new Result(data);
    }

    public static Result result(int status, String msg) {
        return result(status, msg,  null);
    }

    public static Result result(int status, Object data) {
        return result(status, null, data);
    }

    public static Result result(int status, String msg, Object data) {
        Result result = new Result();
        result.setStatus(status);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result successResult(Object data) {
        Result result = new Result();
        result.setStatus(Result.STATUS.SUCCESS.getStatus());
        result.setMsg(Result.STATUS.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static Result successResult() {
        return result(Result.STATUS.SUCCESS.getStatus(), Result.STATUS.SUCCESS.getMsg(), null);
    }

    public static Result errorResult(Object data) {
        Result result = new Result();
        result.setStatus(Result.STATUS.ERROR.getStatus());
        result.setMsg(Result.STATUS.ERROR.getMsg());
        result.setData(data);
        return result;
    }

    public static Result errorResult() {
        return result(Result.STATUS.ERROR.getStatus(), Result.STATUS.ERROR.getMsg(), null);
    }

}
