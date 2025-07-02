package org.medium.queueprocessor.service;

import org.medium.queueprocessor.model.QueueMessage;
import org.medium.queueprocessor.producer.FeedbackPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class QueueMessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessageProcessor.class);
    private final FeedbackPublisher feedbackPublisher;

    public QueueMessageProcessor(FeedbackPublisher feedbackPublisher) {
        this.feedbackPublisher = feedbackPublisher;
    }

    @KafkaListener(topics = "publish-queue")
    public void queueMessageListener(QueueMessage message,
                                     @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        LOGGER.info("Message with id: '{}' received: '{}'", key, message.message());
        QueueMessage processedMessage = message.copy(true);
        feedbackPublisher.sendMessage(key, processedMessage);
    }
}
