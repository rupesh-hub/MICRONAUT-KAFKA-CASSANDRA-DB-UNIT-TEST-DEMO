package com.rupesh.service;

import com.rupesh.entity.UserEntity;
import com.rupesh.exception.CustomValidationException;
import com.rupesh.exception.GlobalException;
import com.rupesh.mdel.UserRequest;
import com.rupesh.mdel.UserResponse;
import com.rupesh.repository.UserRepository;
import com.rupesh.shared.HelperUtil;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Bean
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> findUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public UserResponse save(final UserRequest userRequest) {

        Map<String, String> validationMessages = new HashMap<>();

        if (userRequest == null) {
            validationMessages.put("user", "User cannot be null.");
        }


        //check if user already in database
//        UserResponse userByUsername = userRepository.findByUsername(userRequest.getUsername());
//        UserResponse userByEmail = userRepository.findByUsername(userRequest.getEmail());
//
//        if (userByEmail != null) {
//            throw new GlobalException(MessageFormat.format("user already exists with email [{0}].", userRequest.getEmail()));
//        }
//        if (userByUsername != null) {
//            throw new GlobalException(MessageFormat.format("user already exists with username [{0}].", userRequest.getUsername()));
//        }

        //validate username
        String isValidUsername = HelperUtil.isValidUsername(userRequest.getUsername());
        if (!"valid password".equalsIgnoreCase(isValidUsername)) {
            validationMessages.put("username", isValidUsername);
            throw new CustomValidationException(validationMessages);
        }

        //validate password
        String isValidPassword = HelperUtil.isValidPassword(userRequest.getPassword(), userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail());
        if (!"valid password".equalsIgnoreCase(isValidPassword)) {
            validationMessages.put("password", isValidPassword);
            throw new CustomValidationException(validationMessages);
        }

        //validate email
        boolean isValidEmail = HelperUtil.isValidEmail(userRequest.getEmail());
        if (!isValidEmail) {
            throw new GlobalException(MessageFormat.format("invalid email [{0}].", userRequest.getEmail()));
        }

        userRepository.save(map(userRequest));
        return mapToResponse(userRequest);
    }

    private UserResponse map(final UserEntity user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername());
    }

    private UserEntity map(final UserRequest request) {
        return new UserEntity(UUID.randomUUID().toString(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getUsername(), request.getPassword());
    }

    private UserResponse mapToResponse(final UserRequest request) {
        return new UserResponse(request.getFirstName(), request.getLastName(), request.getEmail(), request.getUsername());
    }
}
