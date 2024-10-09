package com.example.assesment.service.impl;

import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.JobParameters;

public class CustomJobIncrementer extends RunIdIncrementer {
    @Override
    public JobParameters getNext(JobParameters parameters) {
        return super.getNext(parameters);
    }
}
