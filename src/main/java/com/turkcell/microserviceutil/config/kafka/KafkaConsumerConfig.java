package com.turkcell.microserviceutil.config.kafka;

import com.turkcell.microserviceutil.config.kafka.properties.KafkaConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@Slf4j
@ConditionalOnProperty(value = "kafka.consumer.active", havingValue = "true")
public class KafkaConsumerConfig {

    private final KafkaConfigurationProperties  kafkaConfigurationProperties;

    public KafkaConsumerConfig(KafkaConfigurationProperties kafkaConfigurationProperties) {
        this.kafkaConfigurationProperties = kafkaConfigurationProperties;
    }

    private <T> ConsumerFactory<String, Object> customConsumerFactory(Class<T> type) {
        JsonDeserializer<Object> deserializer = new JsonDeserializer(type);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConfigurationProperties.getConsumer().getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG,kafkaConfigurationProperties.getConsumer().getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConfigurationProperties.getConsumer().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),deserializer);
    }

    public <T> ConcurrentKafkaListenerContainerFactory<String, T> createCustomConsumerFactory(Class<T> type, String... groupId) {
        log.info("Kafka consumer factory initialized for bootstrap server {}",kafkaConfigurationProperties.getConsumer().getBootstrapServers());
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(customConsumerFactory(type));
        return factory;
    }



}
