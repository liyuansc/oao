package com.oao.user.controller;

import com.oao.api.annotation.OaoLogin;
import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.id.feign.IdClient;
import com.oao.support.mlog.MArg;
import com.oao.support.mlog.MLog;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.A_USER + "/user")
@Api("用户接口")
@Slf4j
public class OaoUserController {
    @Autowired
    private IdClient idClient;

    @ApiOperation("获取当前用户信息")
    @GetMapping("/login_user")
//    @MLog(title = "获取当前登录用户", value = {@MArg(value = "#p0")})
    public Result<OaoLoginUser> getLoginUser(@OaoLogin(isFull = true) OaoLoginUser oaoLoginUser) {
        return Result.success(oaoLoginUser);
    }

    @ApiOperation("添加用户")
    @PostMapping
    public Result<Void> addUser(OaoUser user) {
        return null;
    }
}
