package com.turkcell.microserviceutil.config.kafka.properties;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ToString
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfigurationProperties {
    private KafkaConsumerProperties consumer;
    private KafkaProducerProperties producer;


    @Data
    @ToString
    public class KafkaConsumerProperties {
        private boolean active;
        private String bootstrapServers;
        private String groupId;
        private String autoOffsetReset;
        private String keyDeserializer;
        private String valueDeserializer;
    }

    @Data
    @ToString
    public class KafkaProducerProperties {
        private boolean active;
        private String bootstrapServers;
        private String keySerializer;
        private String valueSerializer;

    }

}
