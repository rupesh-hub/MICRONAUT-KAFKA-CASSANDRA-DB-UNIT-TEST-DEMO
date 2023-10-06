package com.rupesh.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("cassandra.default.basic")
public record CassandraConfigurationProperties(String host,
                                               int port,
                                               String username,
                                               String password,
                                               String datacenter,
                                               String keyspace) {
}
