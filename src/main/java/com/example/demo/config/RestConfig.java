package com.example.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate init(){
        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofMillis(300000))
                .setConnectTimeout(Duration.ofMillis(300000))
                .build();
    }
}
