package com.synechron.carservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.topic}")
    public String topic;

    @Bean
    public NewTopic carDetailsResponse() {
        return TopicBuilder.name(topic)
                .build();
    }
}
