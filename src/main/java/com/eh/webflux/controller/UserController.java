package com.eh.webflux.controller;

import com.eh.webflux.entity.User;
import com.eh.webflux.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public Mono<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/saveUser")
    public Mono<Void> saveUser(@RequestBody User user) {
        return userService.saveUser(Mono.just(user));
    }

}
