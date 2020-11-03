package com.eh.webflux.function;

import com.eh.webflux.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

public class Server {

    // 创建路由
    public RouterFunction<ServerResponse> routerFunction() {
        // 创建Handler
        UserHandler userHandler = new UserHandler(new UserService());
        // 设置路由
        return RouterFunctions.route(
                RequestPredicates.GET("/users/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                userHandler::getUserById
        ).andRoute(
                RequestPredicates.GET("/users")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                , userHandler::getAllUsers
        );
    }

    // 创建服务器完成适配
    public void createReactorServer() {
        // 创建HttpHandler
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction());
        // 创建适配器
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        // 绑定adapter并使用默认网络设置启动服务器，subscribe网络
        httpServer.handle(adapter).bindNow();
    }

    // 测试程序
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

}
