package com.skylo.assesment.one.called.service;

import org.springframework.stereotype.Service;

@Service
public class RetryService {

    public String  createConsumedDate(String message){
        System.out.println(message);
        return "message is consumed by service ";
    }

}
