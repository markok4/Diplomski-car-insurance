package com.synechron.usermanagement.config;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.synechron.usermanagement.dto.kafka.PolicyUserIdRequestMessage;
import io.jsonwebtoken.io.Deserializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic userIdRequest() {
        return TopicBuilder.name("user.request")
                .build();
    }
    @Bean
    public NewTopic userIdResponse() {
        return TopicBuilder.name("user.response")
                .build();
    }

}
