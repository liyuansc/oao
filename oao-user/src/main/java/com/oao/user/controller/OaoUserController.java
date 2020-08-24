package com.oao.user.controller;

import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.user.annotation.OaoLogin;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoUser;
import com.oao.user.service.IOaoUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.A_USER + "/user")
@Api("用户接口")
@Slf4j
public class OaoUserController {
    @Autowired
    private IOaoUserService userService;

    @ApiOperation("获取当前用户信息")
    @GetMapping("/login_user")
    public Result<OaoLoginUser> getLoginUser(@OaoLogin(isFull = true) OaoLoginUser loginUser) {
        return Result.success(loginUser);
    }

    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result<OaoUser> saveUser(@Validated @RequestBody OaoUser user) {
        return Result.success(userService.saveUser(user));
    }
}
