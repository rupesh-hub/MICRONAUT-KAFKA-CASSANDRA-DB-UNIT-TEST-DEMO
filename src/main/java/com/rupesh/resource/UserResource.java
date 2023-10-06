package com.rupesh.resource;

import com.rupesh.mdel.CustomResponse;
import com.rupesh.mdel.UserRequest;
import com.rupesh.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import static com.rupesh.mdel.CustomResponse.success;

@Controller("/users")
public class UserResource {
    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Post("/register")
    public CustomResponse<?> register(@Body UserRequest request) {
        return success(userService.save(request),"user saved successfully!!");
    }


    @Get("/all")
    public CustomResponse<?> findAll() {
        return success(userService.findUsers(),"user fetched successfully!!");
    }

    @Get()
    public HttpResponse<String> sayHello() {
        return HttpResponse.ok("Hello World");
    }

}
