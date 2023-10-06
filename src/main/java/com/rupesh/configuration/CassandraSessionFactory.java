package com.rupesh.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.rupesh.logging.CustomLogger;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.core.config.DefaultDriverOption.*;
import static java.time.Duration.ofSeconds;

@Factory
public class CassandraSessionFactory {
    private final CqlSession cqlSession;
    private static final CustomLogger LOGGER = CustomLogger.getInstance(CassandraSessionFactory.class);

    public CassandraSessionFactory() {
        LOGGER.log("<<<< CASSANDRA CONNECTION LOADED >>>>>");

        cqlSession = CqlSession
                .builder()
                .addContactPoint(new InetSocketAddress("20.219.183.69", 9042))
                .withLocalDatacenter("datacenter1")
                .withAuthCredentials("cassandra", "CEWerGlrXd")
                .withKeyspace("test_123")
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

