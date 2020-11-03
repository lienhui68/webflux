package com.eh.webflux.service;

import com.eh.webflux.entity.User;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class UserService {

    private final Map<Integer, User> users = Maps.newConcurrentMap();

    {
        users.put(1, new User("徐达", 38));
        users.put(2, new User("常遇春", 27));
        users.put(3, new User("蓝玉", 25));
    }

    // 根据id查询用户
    public Mono<User> getUserById(Integer id) {
        return Mono.justOrEmpty(users.get(id));
    }

    // 查询所有用户
    public Flux<User> getUsers() {
        return Flux.fromIterable(users.values());
    }

    // 添加用户
    public Mono<Void> saveUser(Mono<User> userMono) {
        return userMono.doOnNext(
                user -> users.put(users.size() + 1, user)
        ).thenEmpty(Mono.empty());
    }
}