package org.medium.queueprocessor.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${org.medium.queue-processor.publish-topic}")
    private String publishTopic;

    @Value(value = "${org.medium.queue-processor.feedback-topic}")
    private String feedbackTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic publishTopic() {
        return new NewTopic(publishTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic consumeTopic() {
        return new NewTopic(feedbackTopic, 1, (short) 1);
    }
}
