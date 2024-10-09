package com.skylo.assesment.one.caller.config;

import com.skylo.assesment.one.caller.intercepters.UnlimitedRetryInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new UnlimitedRetryInterceptor(10000)); // 10 seconds back-off period
        restTemplate.setInterceptors(interceptors);
        return  restTemplate;
    }

}
