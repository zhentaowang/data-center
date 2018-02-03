package com.adatafun.base.data.center.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by tiecheng on 2017/12/29.
 */
public class AlternateInfoDTO {

    @JSONField(name = "FlightDepAirport")
    private String flightDepAirport;

    @JSONField(name = "FlightArrAirport")
    private String flightArrAirport;

    @JSONField(name = "fid")
    private String fid;

    public String getFlightDepAirport() {
        return flightDepAirport;
    }

    public void setFlightDepAirport(String flightDepAirport) {
        this.flightDepAirport = flightDepAirport;
    }

    public String getFlightArrAirport() {
        return flightArrAirport;
    }

    public void setFlightArrAirport(String flightArrAirport) {
        this.flightArrAirport = flightArrAirport;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

}
