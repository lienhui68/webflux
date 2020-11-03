package com.eh.webflux.function;

import com.eh.webflux.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Client {
    public static void main(String[] args) {
        // 创建客户端，传入baseUrl
        WebClient webClient = WebClient.create("http://localhost:53238");
        // 根据id查询
        Integer id = 1;
        User user = webClient.get().uri("/users/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve() // 发起请求并接受响应
                .bodyToMono(User.class)
                .block();
        System.out.println(user);
        // 查询所有员工
        Flux<User> users = webClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);
        users.buffer().doOnNext(System.out::println).blockFirst();
    }
}
