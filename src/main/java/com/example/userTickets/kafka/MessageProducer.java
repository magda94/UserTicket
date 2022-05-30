package com.example.userTickets.kafka;

import com.example.userTickets.kafka.message.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageProducer {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(UserMessage message) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaTemplate.send(KafkaConstants.USER_TOPIC, objectMapper.writeValueAsString(message));
            log.info("Sent message: {}", message);
        } catch (JsonProcessingException e) {
            log.warn("Cannot send message, {}", e.getMessage());
        }

    }
}
