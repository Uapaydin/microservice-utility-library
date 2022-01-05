package com.turkcell.microserviceutil.config.kafka;

import com.turkcell.microserviceutil.config.kafka.properties.KafkaConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@ConditionalOnProperty(value = "kafka.producer.active", havingValue = "true")
public class KafkaProducerConfig {

    @Autowired
    private final KafkaConfigurationProperties kafkaConfigurationProperties;

    public KafkaProducerConfig(KafkaConfigurationProperties kafkaConfigurationProperties) {
        this.kafkaConfigurationProperties = kafkaConfigurationProperties;
    }

    @Bean
    @ConditionalOnProperty(value = "kafka.producer.active",havingValue = "true")
    public ProducerFactory<String, Object> producerFactory() {
        log.info("Kafka producer factory properties created");
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConfigurationProperties.getProducer().getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaConfigurationProperties.getProducer().getKeySerializer());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfigurationProperties.getProducer().getValueSerializer());
        return new DefaultKafkaProducerFactory<String, Object>(configProps);
    }

    @Bean
    @ConditionalOnProperty(value = "kafka.producer.active",havingValue = "true")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        log.info("Kafka producer factory initialized for bootstrap server {}",kafkaConfigurationProperties.getConsumer().getBootstrapServers());
        return new KafkaTemplate<String, Object>(producerFactory());
    }


}
