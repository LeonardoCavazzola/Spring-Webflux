package com.example.webflux.controller.response;

import com.example.webflux.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getName());
    }
}
