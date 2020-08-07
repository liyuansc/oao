package com.oao.user.controller;

import com.oao.api.annotation.Login;
import com.oao.user.model.LoginUser;
import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.user.model.po.OaoUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.USER + "/user")
@Api("用户接口")
public class OaoUserController {

    @ApiOperation("获取当前用户信息")
    @GetMapping("/login_user")
    public Result<LoginUser> getLoginUser(@Login(isFull = true) LoginUser loginUser) {
        return Result.success(loginUser);
    }

    @ApiOperation("添加用户")
    @PostMapping
    public Result<Void> addUser(OaoUser user) {
        return null;
    }
}
