package org.medium.queueprocessor.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.medium.queueprocessor.model.QueueMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    private final String bootstrapAddress;
    private final String groupId;

    public KafkaConsumerConfiguration(@Value(value = "${spring.kafka.bootstrap-servers}") String bootstrapAddress,
                                      @Value(value = "${org.medium.queue-processor.group-id}") String groupId) {
        this.bootstrapAddress = bootstrapAddress;
        this.groupId = groupId;
    }

    @Bean
    public ConsumerFactory<String, QueueMessage> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(QueueMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, QueueMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, QueueMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
