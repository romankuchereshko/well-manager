package com.simulation.wellmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
@RequiredArgsConstructor
public class TopicConfiguration {

    @Value("${spring.kafka.topic.alert-topic}")
    private String topic;

    @Bean
    public NewTopic topic() {
        return TopicBuilder
            .name(this.topic)
            .partitions(1)
            .replicas(1)
            .build();
    }

}
