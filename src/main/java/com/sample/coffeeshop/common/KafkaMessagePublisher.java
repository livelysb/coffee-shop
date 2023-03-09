package com.sample.coffeeshop.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KafkaMessagePublisher {
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public <T> void publish(KafkaTopic topic, String key, T body) {
        try {
            String content = objectMapper.writeValueAsString(body);
            kafkaTemplate.send(topic.getTopic(), key, content);
            log.info("kafka message published. topic: {}, key: {}, body: {}", topic, key, content);
        } catch (JsonProcessingException e) {
            log.error("[!] KafkaMessagePublisher.publish() failed. ", e);
        }
    }
}
