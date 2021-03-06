package com.oao.id.controller.client;

import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.id.feign.IdClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.downgoon.snowflake.Snowflake;

@RestController
@RequestMapping
public class IdClientImpl implements IdClient {
    private final static Snowflake snowflake = new Snowflake(1, 1);

    @GetMapping(Route.I_ID + "/next")
    public Result<String> nextId() {
        return Result.success(String.valueOf(snowflake.nextId()));
    }
}
