package com.simulation.wellmanager.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.simulation.library.domain.Frame;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
@RequiredArgsConstructor
public class ProducerConfiguration {

    private final KafkaProperties kafkaProperties;

    @Value("${spring.kafka.topic.alert-topic}")
    private String topic;

    @Bean
    public ProducerFactory<String, Collection<Frame>> producerFactory() {
        final Map<String, Object> properties = this.kafkaProperties.buildProducerProperties();
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class);
        properties.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, Collection<Frame>> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder
            .name(this.topic)
            .partitions(1)
            .replicas(1)
            .build();
    }

}
