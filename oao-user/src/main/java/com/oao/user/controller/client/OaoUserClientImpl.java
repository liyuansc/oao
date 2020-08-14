package com.oao.user.controller.client;

import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.user.feign.UserClient;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoApi;
import com.oao.user.service.IOaoApiService;
import com.oao.user.service.IOaoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Route.I_USER)
public class OaoUserClientImpl implements UserClient {
    @Autowired
    private IOaoUserService oaoUserService;
    @Autowired
    private IOaoApiService oaoApiService;

    @Override
    @GetMapping("/user/username/{username}")
    public Result<OaoLoginUser> findLoginUserByUsername(@PathVariable String username) {
        return Result.success(oaoUserService.findLoginUserByUsername(username));
    }

    @Override
    @GetMapping("/login_user/id/{userId}")
    public Result<OaoLoginUser> findLoginUser(@PathVariable String userId) {
        return Result.success(oaoUserService.findLoginUser(userId));
    }

    @Override
    @GetMapping("/api/all")
    public Result<List<OaoApi>> findAllApi() {
        return Result.success(oaoApiService.findAll());
    }
}
