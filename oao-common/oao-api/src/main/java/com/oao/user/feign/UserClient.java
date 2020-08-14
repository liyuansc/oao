package com.oao.user.feign;

import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoApi;
import com.oao.user.model.po.OaoUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by liyu on 2020/2/20
 */
//@FeignClient(name = App.ID.USER, fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @GetMapping(Route.I_USER + "/user/username/{username}")
    Result<OaoLoginUser> findLoginUserByUsername(@PathVariable("username") String username);

    @GetMapping(Route.I_USER + "/login_user/id/{userId}")
    Result<OaoLoginUser> findLoginUser(@PathVariable("userId") String userId);

    @GetMapping(Route.I_USER + "/api/all")
    Result<List<OaoApi>> findAllApi();
}
