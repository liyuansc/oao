package com.liyu.oao.user.feign;

import com.liyu.oao.api.model.LoginUser;
import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.user.model.po.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by liyu on 2020/2/20
 */
//@FeignClient(name = App.ID.USER, fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @GetMapping(Route.I_USER + "/user/username/{username}")
    Result<User> findByUsername(@PathVariable("username") String username);

    @GetMapping(Route.I_USER + "/login_user/username/{username}")
    Result<LoginUser> findLoginUserByUsername(@PathVariable("username") String username);
}