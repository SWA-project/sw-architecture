package swa.creditservice.messagebroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreditProducer {

    private static final Logger logger = LoggerFactory.getLogger(CreditProducer.class);
    private static final String TOPIC = "CreditEvents";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format(message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}