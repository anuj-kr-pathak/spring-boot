package com.skylo.assesment.one.caller.service.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageConsumer {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ApplicationContext context;

    private static final String TOPIC = "SKYLO";
    @KafkaListener(topics = TOPIC,groupId = "skylo-consumer-group",concurrency = "1")
    public void lisner(String message){
        System.out.println(message);
        context.getBean(MessageConsumer.class).callOtherMicroservice(message);

    }

    @Async
    public void callOtherMicroservice(String message ){
        String url = "http://localhost:9096/service/called/api/retry?message="+message;
        String response =  restTemplate.postForObject(url, message, String.class);
        System.out.println(response);
    }

}
