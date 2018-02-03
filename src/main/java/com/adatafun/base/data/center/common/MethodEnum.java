package com.adatafun.base.data.center.common;

/**
 * Created by tiecheng on 2018/1/17.
 */
public enum MethodEnum {

    FLIGHT_INFO(1, "flightInfo"),
    FLIGHT_INFO_NOT_Real_Time(2, "flightInfoNotRealTime"),
    CUSTOM_FLIGHT(3, "customFlight");

    private int key;
    private String methodName;

    MethodEnum(int key, String methodName) {
        this.key = key;
        this.methodName = methodName;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public static String getValue(int key) {
        MethodEnum[] values = MethodEnum.values();
        for (MethodEnum value : values) {
            if (value.getKey() == key) {
                return value.getMethodName();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getValue(1));
        System.out.println(getValue(2));
        System.out.println(getValue(3));
        System.out.println(getValue(4));
    }

}
