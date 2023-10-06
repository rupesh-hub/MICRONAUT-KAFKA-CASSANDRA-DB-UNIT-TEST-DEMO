package com.rupesh.service;

import com.rupesh.mdel.GlobalResponse;
import com.rupesh.mdel.UserRequest;
import jakarta.inject.Inject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Instant;

public class DemoProducer {

    private final KafkaProducer<String, String> kafkaProducer;

    @Inject
    public DemoProducer(KafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }


    private static final String TOPIC_NAME = "rupesh";

    public GlobalResponse<String> produce(final UserRequest message) {
        kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME, "key", message.toString()));
        return GlobalResponse
                .<String>builder()
                .timestamp(Instant.now())
                .message("message sent successfully!!")
                .status("OK")
                .build();
    }
}
