package com.liyu.oao.user.controller;

import com.liyu.oao.common.constant.Route;
import com.liyu.oao.user.client.UserClient;
import com.liyu.oao.user.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Route.USER)
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private UserClient userClient;


    @GetMapping("test1")
    public Mono<String> test() {
        return testService.test("111");
    }

    @GetMapping("test2")
    public Mono<String> test2() {
        return userClient.test();
    }
}
