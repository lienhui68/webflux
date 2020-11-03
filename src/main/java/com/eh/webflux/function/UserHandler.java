package com.eh.webflux.function;

import com.eh.webflux.entity.User;
import com.eh.webflux.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    // 根据id查询
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        // 获取id值
        int userId = Integer.parseInt(request.pathVariable("id"));
        // 调用service方法得到数据
        Mono<User> userMono = userService.getUserById(userId);
        // 将userMono转换成Mono<ServerResponse>
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        // 练习reactor操作符，简洁写法参考getAllUsers()
        return userMono.flatMap(user -> ServerResponse.ok() // user -> Mono<ServerResponse>,所以使用flatMap
                .contentType(MediaType.APPLICATION_JSON) // json格式
                .body(BodyInserters.fromValue(user))) // 写入body
                .switchIfEmpty(notFound); // 如果没找到使用空值返回
    }

    // 查询所有
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> users = userService.getUsers();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users, User.class);
    }

    // 添加
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        // 得到user对象
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userService.saveUser(userMono));
    }
}
