package com.rupesh;

import com.rupesh.logging.CustomLogger;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class KafkaMessagingTest {

    @Inject
    KafkaProducer<String, String> kafkaProducer;

    @Inject
    KafkaConsumer<String, String> kafkaConsumer;

    private static final CustomLogger LOGGER = CustomLogger.getInstance(KafkaMessagingTest.class);

    @BeforeEach
    void setup() {
        //step: 1. define kafka consumer properties
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "demo-group-config");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetReset.LATEST.toString().toLowerCase());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        //step: 2. create a kafka consumer
        kafkaConsumer = new KafkaConsumer<>(properties);
    }

    @Test
    @Timeout(5)
    @Order(1)
    void testMessageProducer() {
        String message = "{\"first_name\": \"David\", \"last_name\": \"Miller\", \"email\": \"davidmiller@example.com\", \"username\": \"david101\", \"password\": \"RandomPass101\"}";
        Future<RecordMetadata> metadataFuture = kafkaProducer.send(new ProducerRecord<>("rupesh", "", message));
        assertNotNull(metadataFuture);
    }

    @Test
    @Timeout(5)
    @Order(2)
    void testMessageConsumer() {

        //step: 3. set up a latch to wait for messages
        CountDownLatch latch = new CountDownLatch(1);

        //step: 4. list to capture received message
        List<String> messages = new ArrayList<>();

        //step: 5. define a kafka listener for the test
        kafkaConsumer.subscribe(Collections.singletonList("rupesh"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                messages.add(record.value());
                LOGGER.log("Received Message >> " + record.value());
            }

            //if size of messages is 1 then break
            if (messages.size() > 0) {
                latch.countDown();
                break;
            }
        }
        String expected = "{\"first_name\": \"David\", \"last_name\": \"Miller\", \"email\": \"davidmiller@example.com\", \"username\": \"david101\", \"password\": \"RandomPass101\"}";
        assertEquals(expected, messages.get(0));
    }

    @AfterEach
    void cleanup() {
        if (kafkaConsumer != null) {
            kafkaConsumer.close();
        }
    }
}
