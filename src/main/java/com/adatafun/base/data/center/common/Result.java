package com.adatafun.base.data.center.common;

/**
 * 结果对象
 *
 * @date: 2017/11/6 下午4:20
 * @author: ironc
 * @version: 1.0
 */
public class Result<T> {

    public enum STATUS {

        BAD_REQUEST(10400, "提交参数不匹配"),
        NOT_FOUND(10404, "请求结果未找到"),
        METHOD_NOT_ALLOWED(105405, "请求方法类型不匹配"),
        UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),
        SUCCESS(10200, "操作成功"),
        ERROR(10500, "服务器未知报错"),
        UNKNOWN_CLIENT(10301, "非法用户"),
        TOKEN_ERROR(10302, "TOKEN校验失败"),
        CUSTOM_SUCCESS(10211, "定制成功"),
        REPEAT_CUSTOM(10212, "重复定制"),
        CUSTOM_ERROR(10511, "定制失败");

        private int status;
        private String msg;

        STATUS(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }
    }

    private int status;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
        this.msg = STATUS.SUCCESS.getMsg();
        this.status = STATUS.SUCCESS.getStatus();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
