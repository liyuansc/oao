package com.liyu.oao.user.controller.client;

import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.user.feign.IUserClient;
import com.liyu.oao.user.model.po.User;
import com.liyu.oao.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.I_USER + "/user")
public class UserClientImpl implements IUserClient {
    @Autowired
    private IUserService userService;

    @Override
    @GetMapping("/username/{username}")
    public Result<User> findUserByUsername(@PathVariable String username) {
        return Result.success(userService.findByUsername(username));
    }
}