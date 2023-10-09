package com.rupesh.mdel;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("cassandra")
public record CassandraConfigProperties(
        String host,
        int port,
        String username,
        String password,
        String datacenter,
        String keyspace
) {
}
