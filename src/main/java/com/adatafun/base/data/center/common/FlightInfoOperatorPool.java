package com.adatafun.base.data.center.common;

import com.adatafun.base.data.center.mapper.FlightPOMapper;
import com.adatafun.base.data.center.po.FlightPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tiecheng on 2018/2/3.
 */
@Component
public class FlightInfoOperatorPool {

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    private FlightPOMapper flightPOMapper;

    public void runOperator(List<FlightPO> flightPOList) {
        executorService.submit(new OperatorThread(flightPOMapper, flightPOList));
    }

}

class OperatorThread implements Runnable {

    private FlightPOMapper flightPOMapper;

    private List<FlightPO> flightPOList;

    public OperatorThread(FlightPOMapper flightPOMapper, List<FlightPO> flightPOList) {
        this.flightPOMapper = flightPOMapper;
        this.flightPOList = flightPOList;
    }

    public List<FlightPO> getFlightPOList() {
        return flightPOList;
    }

    public void setFlightPOList(List<FlightPO> flightPOList) {
        this.flightPOList = flightPOList;
    }

    public FlightPOMapper getFlightPOMapper() {
        return flightPOMapper;
    }

    public void setFlightPOMapper(FlightPOMapper flightPOMapper) {
        this.flightPOMapper = flightPOMapper;
    }

    @Override
    public void run() {
        for (FlightPO flightPO : flightPOList) {
            flightPOMapper.insertOrUpdateByFourParams(flightPO);
        }
    }

}