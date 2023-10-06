package com.rupesh.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("kafka")
public record KafkaConfigurationProperties(String bootstrapServers,
                                           String groupId,
                                           boolean enableAutoCommit,
                                           String autoOffsetResetConfig,
                                           String keySerializer,
                                           String valueSerializer,
                                           String keyDeserializer,
                                           String valueDeserializer) {
}