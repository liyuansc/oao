package com.liyu.oao.user.controller;

import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.user.model.po.Role;
import com.liyu.oao.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Route.USER + "/role")
@EnableRedisRepositories
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @PostMapping("/add")
    public Result<Role> addRole(@Valid @RequestBody Role role) {
        roleService.save(role);
        return Result.success(role);
    }
}
