package com.example.assesment.service.impl;

import com.example.assesment.service.SimDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobScheduler {

    @Autowired
    public SimDetailService simDetailService;

    //run every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void runBatchJob()throws Exception {
        simDetailService.processSimDetails();
    }

}
