//package com.rupesh.usecase;
//
//import com.rupesh.entity.UserEntity;
//import com.rupesh.mdel.GlobalResponse;
//import com.rupesh.mdel.UserResponse;
//import com.rupesh.repository.UserRepository;
//import com.rupesh.shared.UseCase;
//import com.rupesh.shared.UseCaseRequest;
//import io.micronaut.context.annotation.Bean;
//import jakarta.inject.Inject;
//
//import java.time.Instant;
//import java.util.List;
////
//@Bean
//public class FindAllUserUseCase implements UseCase<UseCaseRequest, GlobalResponse<List<UserResponse>>> {
//
//    private final UserRepository userRepository;
//
//    @Inject
//    public FindAllUserUseCase(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public GlobalResponse<List<UserResponse>> execute(final UseCaseRequest request) {
//        GlobalResponse<List<UserResponse>> globalResponse = new GlobalResponse<>();
//
//        return GlobalResponse
//                .<List<UserResponse>>builder()
//                .message("users found successfully!!")
//                .status("success")
//                .timestamp(Instant.now())
//                .data(
//                        userRepository
//                                .findAll()
//                                .stream()
//                                .map(this::map)
//                                .toList()
//                )
//                .build();
//    }
//
//    private UserResponse map(final UserEntity user) {
//        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername());
//    }
//
//}
