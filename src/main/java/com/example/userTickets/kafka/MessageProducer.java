package com.example.userTickets.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(KafkaConstants.USER_TOPIC, message);
        log.info("Sent message: {}", message);

    }
}
