package com.homework.travel.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.homework.travel.domain.entity.User;
import com.homework.travel.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@RestController
public class UserApiController {
    
    @Autowired
    UserService userService;

    /**
     * 회원 등록 API
     */
    @PostMapping("/api/user")
    public CreateUserResponse saveUser(@RequestBody @Valid CreateUserRequest requset){

        User user = new User();
        user.setUsername(requset.getUsername());

        Long id = userService.register(user);
        return new CreateUserResponse(id);

    }

    @Data
    static class CreateUserRequest {

        @NotBlank(message = "username is a required value.")
        private String username;
    }

    @Data
    static class CreateUserResponse {
        private Long id;

        public CreateUserResponse(Long id) {
            this.id = id;
        }
    }
}
