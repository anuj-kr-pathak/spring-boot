package com.skylo.assesment.one.producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "SKYLO";


    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(String key, Object value) {
        kafkaTemplate.send(TOPIC, key, value);
        System.out.println("Pushing key and message  " + value);
    }

}
