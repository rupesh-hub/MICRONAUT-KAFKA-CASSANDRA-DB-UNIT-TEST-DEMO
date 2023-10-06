package com.rupesh.configuration;

import com.rupesh.logging.CustomLogger;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Factory
public class KafkaProducerConfiguration {

    private final KafkaConfigurationProperties kafkaConfigurationProperties;
    private static final CustomLogger LOGGER = CustomLogger.getInstance(KafkaProducerConfiguration.class);

    @Inject
    public KafkaProducerConfiguration(KafkaConfigurationProperties kafkaConfigurationProperties) {
        this.kafkaConfigurationProperties = kafkaConfigurationProperties;
    }

    public Properties producerConfiguration() {
        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, kafkaConfigurationProperties.bootstrapServers());
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, kafkaConfigurationProperties.keySerializer());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfigurationProperties.valueSerializer());
        return properties;
    }

    @Singleton
    @Bean
    public KafkaProducer<String, String> kafkaConsumer() {
        LOGGER.log("<<<< KAFKA PRODUCER LOADED >>>>>");
        Properties kafkaProperties = producerConfiguration();
        return new KafkaProducer<>(kafkaProperties);
    }

}
