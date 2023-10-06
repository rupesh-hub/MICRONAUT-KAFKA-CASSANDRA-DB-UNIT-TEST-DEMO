package com.rupesh.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupesh.entity.UserEntity;
import com.rupesh.logging.CustomLogger;
import com.rupesh.mdel.UserResponse;
import com.rupesh.repository.UserRepository;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.util.UUID;

@KafkaListener(groupId = "demo-group", offsetReset = OffsetReset.LATEST)
public class UserConsumer {

    private final UserRepository userRepository;

    private static final CustomLogger LOGGER = CustomLogger.getInstance(UserConsumer.class);
    private static final String TOPIC = "rupesh";

    @Inject
    public UserConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Topic(TOPIC)
    public UserResponse consume(final String message) {

        LOGGER.log("Received Message >> " + message);
        final UserEntity user = map(prepare(message));
        LOGGER.log("Received Message >> USER RESPONSE >> " + user);

        userRepository.save(user);

        return null;
    }

    public UserResponse prepare(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserResponse userResponse = null;

        try (JsonParser jsonParser = objectMapper.getFactory().createParser(message)) {
            JsonNode jsonNode = objectMapper.readTree(jsonParser);

            // Extract field values from the JSON
            String firstName = jsonNode.get("first_name").asText();

            String lastName = jsonNode.get("last_name").asText();
            String email = jsonNode.get("email").asText();
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();
            userResponse = new UserResponse(firstName, lastName, email, username, password);

            LOGGER.log("Deserialized UserResponse: " + userResponse);
        } catch (Exception e) {
            LOGGER.log("Failed to deserialize JSON: " + e.getMessage());
        }

        return userResponse;
    }

    private static UserEntity map(final UserResponse userResponse) {
        return new UserEntity(UUID.randomUUID().toString(), userResponse.getFirstName(), userResponse.getLastName(), userResponse.getEmail(), userResponse.getUsername(), userResponse.getPassword());
    }

}
