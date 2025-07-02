package org.medium.queueprocessor.producer;

import org.medium.queueprocessor.model.QueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class FeedbackPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackPublisher.class);

    private final KafkaTemplate<String, QueueMessage> kafkaTemplate;
    private final String produceTopic;

    public FeedbackPublisher(KafkaTemplate<String, QueueMessage> kafkaTemplate,
                             @Value(value = "${org.medium.queue-processor.feedback-topic}") String produceTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.produceTopic = produceTopic;
    }

    public void sendMessage(String key, QueueMessage message) {
        CompletableFuture<SendResult<String, QueueMessage>> send = kafkaTemplate.send(produceTopic, key, message);
        send.whenComplete((result, exception) -> {
            if (exception == null) {
                LOGGER.info("Response for '{}' sent successfully with offset: '{}'", key, result.getRecordMetadata().offset());
            } else {
                LOGGER.error("Response for '{}' send failed due to error {}", key, exception.getMessage());
            }
        });
    }
}
