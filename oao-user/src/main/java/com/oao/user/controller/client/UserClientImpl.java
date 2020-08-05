package com.oao.user.controller.client;

import com.oao.api.model.LoginUser;
import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.user.feign.UserClient;
import com.oao.user.model.po.User;
import com.oao.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.I_USER)
public class UserClientImpl implements UserClient {
    @Autowired
    private IUserService userService;

    @Override
    @GetMapping("/user/username/{username}")
    public Result<User> findByUsername(@PathVariable String username) {
        return Result.success(userService.findByUsername(username));
    }

    @Override
    @GetMapping("/login_user/username/{username}")
    public Result<LoginUser> findLoginUserByUsername(@PathVariable String username) {
        return Result.success(userService.findLoginUserByUsername(username));
    }
}
