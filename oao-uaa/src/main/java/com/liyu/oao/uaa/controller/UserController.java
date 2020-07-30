package com.liyu.oao.uaa.controller;

import com.alibaba.fastjson.JSON;
import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.uaa.client.id.IdClient;
import com.liyu.oao.web.security.Login;
import com.liyu.oao.web.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.UAA + "/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IdClient idClient;

    @RequestMapping
    public Result addUser(@Login LoginUser loginUser) {
        return Result.success(loginUser);
    }
}
