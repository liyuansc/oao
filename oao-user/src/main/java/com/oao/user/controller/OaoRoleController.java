package com.oao.user.controller;

import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.user.model.AddRoleReq;
import com.oao.user.model.po.OaoRole;
import com.oao.user.service.IOaoRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Route.A_USER + "/role")
@Api(tags = "角色接口")
public class OaoRoleController {
    @Autowired
    private IOaoRoleService roleService;

    @PostMapping("/add")
    @ApiOperation("添加角色")
    public Result<OaoRole> addRole(@Valid @RequestBody AddRoleReq role) {
        roleService.save(role);
        return Result.success(role);
    }
}
