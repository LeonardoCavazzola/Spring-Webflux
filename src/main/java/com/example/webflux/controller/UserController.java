package com.example.webflux.controller;

import com.example.webflux.controller.request.UserRequest;
import com.example.webflux.controller.response.UserResponse;
import com.example.webflux.exception.EntityNotFoundException;
import com.example.webflux.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserResponse> findAll() {
        return userRepository.findAll().flatMap(user -> Flux.just(UserResponse.fromEntity(user)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> findById(@PathVariable String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException()))
                .map(UserResponse::fromEntity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> add(@RequestBody UserRequest userRequest) {
        return userRepository.save(userRequest.toEntity()).map(UserResponse::fromEntity);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> update(@PathVariable String id, @RequestBody UserRequest userRequest) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException()))
                .map(user -> user.update(userRequest.toEntity()))
                .flatMap(userRepository::save)
                .map(UserResponse::fromEntity);
    }
}
