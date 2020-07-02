package com.liyu.oao.uaa.controller;

import com.liyu.oao.common.constant.Route;
import com.liyu.oao.uaa.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.USER)
public class TestController {
    @Autowired
    private TestService testService;
}
