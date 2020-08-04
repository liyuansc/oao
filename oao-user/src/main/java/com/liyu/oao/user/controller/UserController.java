package com.liyu.oao.user.controller;

import com.liyu.oao.api.annotation.Login;
import com.liyu.oao.api.model.LoginUser;
import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.user.model.po.User;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.USER + "/user")
@EnableRedisRepositories
public class UserController {

    @GetMapping("/login_user")
    public Result<LoginUser> getLoginUser(@Login(isFull = true) LoginUser loginUser) {
        return Result.success(loginUser);
    }

    @PostMapping
    public Result<Void> addUser(User user) {
        return null;
    }
}
