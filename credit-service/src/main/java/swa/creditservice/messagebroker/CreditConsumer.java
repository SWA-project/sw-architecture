package swa.creditservice.messagebroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreditConsumer {

    private final Logger logger = LoggerFactory.getLogger(CreditProducer.class);

    @KafkaListener(topics = "CreditEvents", groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("Received message -> %s", message));
    }
}