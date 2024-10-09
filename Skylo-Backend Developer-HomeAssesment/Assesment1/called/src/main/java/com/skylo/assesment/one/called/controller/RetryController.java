package com.skylo.assesment.one.called.controller;

import com.skylo.assesment.one.called.service.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/called/api")
public class RetryController {

    @Autowired
    RetryService retryService;

    @PostMapping("/retry")
    String createRetry(@RequestParam("message") String message){
        return retryService.createConsumedDate(message);
    }
}
