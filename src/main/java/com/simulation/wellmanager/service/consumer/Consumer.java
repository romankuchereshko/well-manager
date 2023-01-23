package com.simulation.wellmanager.service.consumer;

import java.util.Arrays;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulation.wellmanager.service.FrameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.simulation.library.domain.Frame;

@Slf4j
@Component
@AllArgsConstructor
public class Consumer {

    private final ObjectMapper objectMapper;

    private final FrameService frameService;

    @KafkaListener(topics = "${spring.kafka.topic.oil-station-topic}")
    public void consumeMessage(final String message) throws JsonProcessingException {
        log.info("Message consumed {}", message);

        final List<Frame> frames = Arrays.asList(this.objectMapper.readValue(message, Frame[].class));

        this.frameService.saveAll(frames);
    }

}

