package com.example.webflux.controller.request;

import com.example.webflux.model.User;
import lombok.Data;

@Data
public class UserRequest {
    private String name;

    public User toEntity() {
        return new User(this.name);
    }
}
