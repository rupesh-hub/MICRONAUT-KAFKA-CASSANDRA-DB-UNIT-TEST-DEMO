package com.rupesh.configuration;

import com.rupesh.logging.CustomLogger;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Factory
public class KafkaConsumerConfiguration {

    private final KafkaConfigurationProperties kafkaConfigurationProperties;
    private static final CustomLogger LOGGER = CustomLogger.getInstance(KafkaConsumerConfiguration.class);

    @Inject
    public KafkaConsumerConfiguration(KafkaConfigurationProperties kafkaConfigurationProperties) {
        this.kafkaConfigurationProperties = kafkaConfigurationProperties;
    }

    @Singleton
    @Bean
    public KafkaConsumer<String, String> kafkaConsumer() {
        LOGGER.log("<<<< KAFKA CONSUMER LOADED >>>>>");
        Properties kafkaProperties = kafkaConsumerProperties();
        return new KafkaConsumer<>(kafkaProperties);
    }

    public Properties kafkaConsumerProperties() {
        final Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaConfigurationProperties.bootstrapServers());
        props.put(GROUP_ID_CONFIG, kafkaConfigurationProperties.groupId());
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, kafkaConfigurationProperties.keyDeserializer());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConfigurationProperties.valueDeserializer());
        props.put(AUTO_OFFSET_RESET_CONFIG, kafkaConfigurationProperties.autoOffsetResetConfig());
        props.put(ENABLE_AUTO_COMMIT_CONFIG, kafkaConfigurationProperties.enableAutoCommit());
        return props;
    }

}
