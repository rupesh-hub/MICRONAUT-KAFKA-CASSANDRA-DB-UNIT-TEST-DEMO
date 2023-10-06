package com.rupesh;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.core.config.DefaultDriverOption.*;
import static java.time.Duration.ofSeconds;

class MainTest {

    @Test
    void testCassandraConnection() throws Exception {
        final CqlSession cqlSession = CqlSession
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

        Assertions.assertNotNull(cqlSession);
    }
}
