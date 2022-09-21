package com.example.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Document
public class User {
    @Id
    private String id;
    private String name;

    public User(String name) {
        this.name = name;
    }

    public User update(User right) {
        this.name = right.getName();
        return this;
    }
}
