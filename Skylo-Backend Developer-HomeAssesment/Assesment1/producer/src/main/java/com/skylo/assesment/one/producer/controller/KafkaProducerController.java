package com.skylo.assesment.one.producer.controller;

import com.skylo.assesment.one.producer.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KafkaProducerController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @PostMapping("/create")
    public String createMessage(@RequestParam("key") String key,@RequestParam("message") String message){

        kafkaProducerService.sendMessage(key,message);
        return "kafka sent successfully ";

    }

}
