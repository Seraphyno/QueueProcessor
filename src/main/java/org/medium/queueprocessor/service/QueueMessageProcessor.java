package org.medium.queueprocessor.service;

import org.medium.queueprocessor.model.QueueMessage;
import org.medium.queueprocessor.producer.FeedbackPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class QueueMessageProcessor {

    private final Logger logger = LoggerFactory.getLogger(QueueMessageProcessor.class);
    private final FeedbackPublisher feedbackPublisher;

    public QueueMessageProcessor(FeedbackPublisher feedbackPublisher) {
        this.feedbackPublisher = feedbackPublisher;
    }

    @KafkaListener(topics = "publish-queue")
    public void queueMessageListener(QueueMessage message) {
        logger.info("Message with id: '{}' received: '{}'", message.id(), message.message());

        QueueMessage processedMessage = message.copy(true);

        feedbackPublisher.sendMessage(processedMessage);
    }
}
