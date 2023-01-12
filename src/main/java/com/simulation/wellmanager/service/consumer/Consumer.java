package com.simulation.wellmanager.service.consumer;

import java.util.Arrays;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulation.wellmanager.service.FrameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oil.station.domain.frame.Frame;

@RequiredArgsConstructor
@Slf4j
@Component
public class Consumer {

    private final ObjectMapper objectMapper;

    private final FrameService frameService;


    @KafkaListener(topics = "${topic.name}")
    public void consumeMessage(final String message) throws JsonProcessingException {
        log.info("Message consumed {}", message);

        final List<Frame> frames = Arrays.asList(this.objectMapper.readValue(message, Frame[].class));

        this.frameService.saveAll(frames);
    }

}
