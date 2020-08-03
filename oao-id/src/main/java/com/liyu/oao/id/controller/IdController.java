package com.liyu.oao.id.controller;

import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.downgoon.snowflake.Snowflake;

@RestController
@RequestMapping(Route.ID)
public class IdController {
    private final static Snowflake snowflake = new Snowflake(1, 1);

    @GetMapping("/next")
    public Result<String> nextId() {
        return Result.success(String.valueOf(snowflake.nextId()));
    }
}
