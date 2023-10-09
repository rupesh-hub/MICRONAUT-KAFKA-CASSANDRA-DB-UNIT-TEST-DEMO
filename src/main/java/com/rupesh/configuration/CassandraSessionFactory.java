package com.rupesh.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.rupesh.logging.CustomLogger;
import com.rupesh.mdel.CassandraConfigProperties;
import io.micronaut.context.annotation.*;
import jakarta.inject.Inject;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.core.config.DefaultDriverOption.*;
import static java.time.Duration.ofSeconds;

@Factory
public class CassandraSessionFactory {

    private final CqlSession cqlSession;
    private static final CustomLogger LOGGER = CustomLogger.getInstance(CassandraSessionFactory.class);

    private final CassandraConfigProperties cassandraConfigProperties;

    @Inject

    public CassandraSessionFactory(CassandraConfigProperties cassandraConfigProperties) {
        this.cassandraConfigProperties = cassandraConfigProperties;
        LOGGER.log("<<<< CASSANDRA CONNECTION LOADED >>>>>");

        cqlSession = CqlSession
                .builder()
                .addContactPoint(new InetSocketAddress(cassandraConfigProperties.host(), cassandraConfigProperties.port()))
                .withLocalDatacenter(cassandraConfigProperties.datacenter())
                .withAuthCredentials(cassandraConfigProperties.username(), cassandraConfigProperties.password())
                .withKeyspace(cassandraConfigProperties.keyspace())
                .withConfigLoader(
                        DriverConfigLoader
                                .programmaticBuilder()
                                .withDuration(REQUEST_TIMEOUT, ofSeconds(50))
                                .startProfile("capture")
                                .withDuration(REQUEST_TIMEOUT, ofSeconds(50))
                                .withInt(CONNECTION_POOL_LOCAL_SIZE, 5)
                                .withBoolean(REQUEST_LOGGER_VALUES, true)
                                .endProfile()
                                .build()
                )
                .build();

        LOGGER.log(">>> cql session established >>>");
    }

    @Bean
    @Primary
    public CqlSession getCqlSession() {
        return cqlSession;
    }

}

