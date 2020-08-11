package com.oao.id.feign;

import com.oao.common.constant.Route;
import com.oao.common.model.Result;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by liyu on 2020/2/20
 */
//@FeignClient(name = App.ID.ID, fallbackFactory = IdClientFallbackFactory.class)
public interface IdClient {

    @GetMapping(Route.I_ID + "/next")
    Result<String> nextId();
}
